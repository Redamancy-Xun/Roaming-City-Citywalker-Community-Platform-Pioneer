package com.forum.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.common.EnumExceptionType;
import com.forum.common.Page;
import com.forum.controller.response.ShowShoppingCartReponse;
import com.forum.entity.Route;
import com.forum.entity.ShoppingCart;
import com.forum.entity.User;
import com.forum.exception.MyException;
import com.forum.mapper.RouteMapper;
import com.forum.mapper.ShoppingCartMapper;
import com.forum.service.ShoppingCartService;
import com.forum.service.UserService;
import com.forum.util.SessionUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionUtils sessionUtils;

    /**
     * 展示用户购物车中的路线
     * @param page 页码
     * @param pageSize 每页数量
     * @return Page<ShowShoppingCartResponse>
     * @throws MyException 通用异常
     */
    @Override
    public Page<ShowShoppingCartReponse> showShoppingCart(Integer page, Integer pageSize) throws MyException {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        String userId = sessionUtils.getUserId();
        QueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new QueryWrapper<>();
        shoppingCartQueryWrapper.eq("userId", userId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(shoppingCartQueryWrapper);

        int count = 0;
        List<ShowShoppingCartReponse> showShoppingCartReponses = new ArrayList<>();

        long cartId = shoppingCart.getCartId();
        Map<Long, Integer> peopleMap = JSON.parseObject(shoppingCart.getRoutePeople(), new TypeReference<Map<Long, Integer>>(){});
        Map<Long, String> timeMap = JSON.parseObject(shoppingCart.getRouteTime(), new TypeReference<Map<Long, String>>(){});
        for (Long routeId : JSON.parseObject(shoppingCart.getRoutes(), new TypeReference<List<Long>>(){})) {
            Route route = routeMapper.selectById(routeId);
            Integer routePeople = peopleMap.get(routeId);
            String routeTime = timeMap.get(routeId);

            count++;
            if (count > (long) (page - 1) * pageSize && count <= (long) page * pageSize) {
                showShoppingCartReponses.add(new ShowShoppingCartReponse(cartId, route, routePeople, routeTime));
            }
        }

        Page<ShowShoppingCartReponse> showShoppingCartReponsePage = new Page<>();
        showShoppingCartReponsePage.setPageSize(pageSize);
        showShoppingCartReponsePage.setPages(((count - 1) / pageSize) + 1);
        showShoppingCartReponsePage.setPageNum(page);
        showShoppingCartReponsePage.setTotal((long) count);
        showShoppingCartReponsePage.setItems(showShoppingCartReponses);

        return showShoppingCartReponsePage;
    }

    /**
     * 将路线加入用户购物车
     * @param routeId 路线Id
     * @param routePeople 路线所选人数
     * @param routeTime 路线所选时间段
     * @return Boolean
     * @throws MyException 通用异常
     */
    @Override
    public ShowShoppingCartReponse addShoppingCart(Long routeId, Integer routePeople, String routeTime) throws MyException {
        String userId = sessionUtils.getUserId();
        QueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new QueryWrapper<>();
        shoppingCartQueryWrapper.eq("userId", userId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(shoppingCartQueryWrapper);

        List<Long> routeIdList =  JSON.parseObject(shoppingCart.getRoutes(), new TypeReference<List<Long>>(){});
        Map<Long, Integer> peopleMap = JSON.parseObject(shoppingCart.getRoutePeople(), new TypeReference<Map<Long, Integer>>(){});
        Map<Long, String> timeMap = JSON.parseObject(shoppingCart.getRouteTime(), new TypeReference<Map<Long, String>>(){});
        routeIdList.add(routeId);
        peopleMap.put(routeId, routePeople);
        timeMap.put(routeId, routeTime);

        shoppingCart.setRoutes(JSON.toJSONString(routeIdList));
        shoppingCart.setRoutePeople(JSON.toJSONString(peopleMap));
        shoppingCart.setRouteTime(JSON.toJSONString(timeMap));

        ShowShoppingCartReponse showShoppingCartReponse = new ShowShoppingCartReponse();
        showShoppingCartReponse.setCartId(shoppingCart.getCartId());
        showShoppingCartReponse.setRoute(routeMapper.selectById(routeId));
        showShoppingCartReponse.setRoutePeople(routePeople);
        showShoppingCartReponse.setRouteTime(routeTime);

        if (shoppingCartMapper.updateById(shoppingCart) == 0) {
            throw new MyException(EnumExceptionType.ADD_SHOPPINGCART_FAIL);
        }

        return showShoppingCartReponse;
    }

    /**
     * 删除购物车中的路线
     * @param routeId 路线id
     * @throws MyException 通用异常
     */
    @Override
    public void deleteShoppingCart(Long routeId) {
        String userId = sessionUtils.getUserId();
        QueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new QueryWrapper<>();
        shoppingCartQueryWrapper.eq("userId", userId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(shoppingCartQueryWrapper);

        List<Long> routeIdList =  JSON.parseObject(shoppingCart.getRoutes(), new TypeReference<List<Long>>(){});
        Map<Long, Integer> peopleMap = JSON.parseObject(shoppingCart.getRoutePeople(), new TypeReference<Map<Long, Integer>>(){});
        Map<Long, String> timeMap = JSON.parseObject(shoppingCart.getRouteTime(), new TypeReference<Map<Long, String>>(){});

        routeIdList.remove(routeId);
        peopleMap.remove(routeId);
        timeMap.remove(routeId);

        shoppingCart.setRoutes(JSON.toJSONString(routeIdList));
        shoppingCart.setRoutePeople(JSON.toJSONString(peopleMap));
        shoppingCart.setRouteTime(JSON.toJSONString(timeMap));

        if (shoppingCartMapper.updateById(shoppingCart) == 0) {
            throw new MyException(EnumExceptionType.DELETE_SHOPPINGCART_FAIL);
        }
    }
}