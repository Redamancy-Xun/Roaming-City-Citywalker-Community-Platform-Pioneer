package com.forum.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.common.EnumExceptionType;
import com.forum.common.Page;
import com.forum.common.Result;
import com.forum.controller.response.ShowShoppingCartReponse;
import com.forum.entity.Buy;
import com.forum.entity.Route;
import com.forum.entity.ShoppingCart;
import com.forum.entity.User;
import com.forum.exception.MyException;
import com.forum.mapper.BuyMapper;
import com.forum.mapper.RouteMapper;
import com.forum.mapper.ShoppingCartMapper;
import com.forum.service.NoticeService;
import com.forum.service.ShoppingCartService;
import com.forum.service.UserService;
import com.forum.util.SessionUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private BuyMapper buyMapper;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private SessionUtils sessionUtils;

    private static final Integer[] cost = {128, 128, 88, 88, 98, 48, 68, 88, 58, 88, 88, 88};

    /**
     * 展示用户购物车中的路线
     * @param page 页码
     * @param pageSize 每页数量
     * @return Page<ShowShoppingCartResponse>
     * @throws MyException 通用异常
     */
    @Override
    public Page<ShowShoppingCartReponse> showShoppingCart(Integer page, Integer pageSize) throws MyException {
        // 页面参数处理
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        // 获取用户购物车
        String userId = sessionUtils.getUserId();
        QueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new QueryWrapper<>();
        shoppingCartQueryWrapper.eq("user_id", userId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(shoppingCartQueryWrapper);

        // 购物车为空的情况
        if (shoppingCart.getRoutes() == null) {
            return new Page<ShowShoppingCartReponse>();
        }

        int count = 0;
        List<ShowShoppingCartReponse> showShoppingCartResponses = new ArrayList<>();

        // 遍历购物车内的路线
        long cartId = shoppingCart.getCartId();
        List<String> orderIdList = JSON.parseObject(shoppingCart.getOrderId(), new TypeReference<List<String>>(){});
        Map<String, Long> routeIdMap = JSON.parseObject(shoppingCart.getRoutes(), new TypeReference<Map<String, Long>>(){});
        Map<String, Integer> peopleMap = JSON.parseObject(shoppingCart.getRoutePeople(), new TypeReference<Map<String, Integer>>(){});
        Map<String, String> timeMap = JSON.parseObject(shoppingCart.getRouteTime(), new TypeReference<Map<String, String>>(){});
        for (String orderId : orderIdList) {
            Route route = routeMapper.selectById(routeIdMap.get(orderId));
            Integer routePeople = peopleMap.get(orderId);
            String routeTime = timeMap.get(orderId);
            Integer routeCost = cost[route.getRouteId().intValue() - 1] * routePeople;

            count++;
            if (count > (long) (page - 1) * pageSize && count <= (long) page * pageSize) {
                showShoppingCartResponses.add(new ShowShoppingCartReponse(cartId, orderId, routePeople, routeTime, routeCost, route));
            }
        }

        // 返回购物车信息
        Page<ShowShoppingCartReponse> showShoppingCartReponsePage = new Page<>();
        showShoppingCartReponsePage.setPageSize(pageSize);
        showShoppingCartReponsePage.setPages(((count - 1) / pageSize) + 1);
        showShoppingCartReponsePage.setPageNum(page);
        showShoppingCartReponsePage.setTotal((long) count);
        showShoppingCartReponsePage.setItems(showShoppingCartResponses);

        // 返回购物车信息
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
        // 获取用户购物车
        String userId = sessionUtils.getUserId();
        QueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new QueryWrapper<>();
        shoppingCartQueryWrapper.eq("user_id", userId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(shoppingCartQueryWrapper);

        // 数据转换为可操作
        List<String> orderIdList = new ArrayList<>();
        Map<String, Long> routeIdMap = new HashMap<>();
        Map<String, Integer> peopleMap = new HashMap<>();
        Map<String, String> timeMap = new HashMap<>();
        if (shoppingCart.getRoutes() != null) {
            orderIdList = JSON.parseObject(shoppingCart.getOrderId(), new TypeReference<List<String>>(){});
            routeIdMap = JSON.parseObject(shoppingCart.getRoutes(), new TypeReference<Map<String, Long>>(){});
            peopleMap = JSON.parseObject(shoppingCart.getRoutePeople(), new TypeReference<Map<String, Integer>>(){});
            timeMap = JSON.parseObject(shoppingCart.getRouteTime(), new TypeReference<Map<String, String>>(){});
        }

        // 生成orderId
        String orderId = UUID.randomUUID().toString().substring(0, 8);

        // 将路线加入购物车
        orderIdList.add(orderId);
        routeIdMap.put(orderId, routeId);
        peopleMap.put(orderId, routePeople);
        timeMap.put(orderId, routeTime);

        // 更新购物车
        shoppingCart.setOrderId(JSON.toJSONString(orderIdList));
        shoppingCart.setRoutes(JSON.toJSONString(routeIdMap));
        shoppingCart.setRoutePeople(JSON.toJSONString(peopleMap));
        shoppingCart.setRouteTime(JSON.toJSONString(timeMap));

        // 返回购物车信息
        ShowShoppingCartReponse showShoppingCartReponse = new ShowShoppingCartReponse();
        showShoppingCartReponse.setCartId(shoppingCart.getCartId());
        showShoppingCartReponse.setOrderId(orderId);
        showShoppingCartReponse.setRoute(routeMapper.selectById(routeId));
        showShoppingCartReponse.setRoutePeople(routePeople);
        showShoppingCartReponse.setRouteTime(routeTime);

        // 更新购物车
        if (shoppingCartMapper.updateById(shoppingCart) == 0) {
            throw new MyException(EnumExceptionType.ADD_SHOPPINGCART_FAIL);
        }

        return showShoppingCartReponse;
    }

    /**
     * 删除购物车中的路线
     * @param orderId 路线orderId
     * @throws MyException 通用异常
     */
    @Override
    public void deleteShoppingCart(String orderId) {
        // 获取用户购物车信息
        String userId = sessionUtils.getUserId();
        QueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new QueryWrapper<>();
        shoppingCartQueryWrapper.eq("user_id", userId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(shoppingCartQueryWrapper);

        // 数据转换为可操作
        List<String> orderIdList = JSON.parseObject(shoppingCart.getOrderId(), new TypeReference<List<String>>(){});
        Map<String, Long> routeIdMap =  JSON.parseObject(shoppingCart.getRoutes(), new TypeReference<Map<String, Long>>(){});
        Map<String, Integer> peopleMap = JSON.parseObject(shoppingCart.getRoutePeople(), new TypeReference<Map<String, Integer>>(){});
        Map<String, String> timeMap = JSON.parseObject(shoppingCart.getRouteTime(), new TypeReference<Map<String, String>>(){});

        // 删除路线
        orderIdList.remove(orderId);
        routeIdMap.remove(orderId);
        peopleMap.remove(orderId);
        timeMap.remove(orderId);

        // 更新购物车
        shoppingCart.setOrderId(JSON.toJSONString(orderIdList));
        shoppingCart.setRoutes(JSON.toJSONString(routeIdMap));
        shoppingCart.setRoutePeople(JSON.toJSONString(peopleMap));
        shoppingCart.setRouteTime(JSON.toJSONString(timeMap));

        // 更新购物车
        if (shoppingCartMapper.updateById(shoppingCart) == 0) {
            throw new MyException(EnumExceptionType.DELETE_SHOPPINGCART_FAIL);
        }
    }

    /**
     * 购买购物车中的路线
     * @param orderId 路线orderId
     * @throws MyException 通用异常
     */
    @Override
    public void buyShoppingCart(List<String> orderId) {
        // 获取用户购物车信息
        String userId = sessionUtils.getUserId();
        QueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new QueryWrapper<>();
        shoppingCartQueryWrapper.eq("user_id", userId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(shoppingCartQueryWrapper);

        // 数据转换为可操作
        List<String> orderIdList = JSON.parseObject(shoppingCart.getOrderId(), new TypeReference<List<String>>(){});
        Map<String, Long> routeIdMap =  JSON.parseObject(shoppingCart.getRoutes(), new TypeReference<Map<String, Long>>(){});
        Map<String, Integer> peopleMap = JSON.parseObject(shoppingCart.getRoutePeople(), new TypeReference<Map<String, Integer>>(){});
        Map<String, String> timeMap = JSON.parseObject(shoppingCart.getRouteTime(), new TypeReference<Map<String, String>>(){});

        // 记录购买的路线
        List<Long> routeIdList = new ArrayList<>();
        for (String id : orderId) {
            routeIdList.add(routeIdMap.get(id));
        }

        // 购买路线
        for (String orderIdOne : orderId) {
            orderIdList.remove(orderIdOne);
            routeIdMap.remove(orderIdOne);
            peopleMap.remove(orderIdOne);
            timeMap.remove(orderIdOne);
        }

        // 更新购物车
        shoppingCart.setOrderId(JSON.toJSONString(orderIdList));
        shoppingCart.setRoutes(JSON.toJSONString(routeIdMap));
        shoppingCart.setRoutePeople(JSON.toJSONString(peopleMap));
        shoppingCart.setRouteTime(JSON.toJSONString(timeMap));

        // 更新购物车
        if (shoppingCartMapper.updateById(shoppingCart) == 0) {
            throw new MyException(EnumExceptionType.BUY_SHOPPINGCART_FAIL);
        }

        // 给用户订单信息内加入已走路线
        QueryWrapper<Buy> buyQueryWrapper = new QueryWrapper<>();
        buyQueryWrapper.eq("user_id", userId);
        buyQueryWrapper.isNull("delete_at");
        Buy buy = buyMapper.selectOne(buyQueryWrapper);
        List<Long> oldRouteIdList = buy.getBuyRoutes() == null ? new ArrayList<>() : JSON.parseObject(buy.getBuyRoutes(), new TypeReference<List<Long>>(){});
        oldRouteIdList.addAll(routeIdList);
        buy.setBuyRoutes(JSON.toJSONString(oldRouteIdList));

        // 更新购买订单
        if (buyMapper.updateById(buy) == 0) {
            throw new MyException(EnumExceptionType.BUY_SHOPPINGCART_FAIL);
        }

        // 给用户发送购买订单成功系统信息
        User user = userService.getUserById(userId);
        for (Long id : routeIdList) {
            noticeService.sendBuySuccessNotice(user, id);
        }
    }
}
