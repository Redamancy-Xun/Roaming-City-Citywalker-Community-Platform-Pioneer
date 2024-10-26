package com.forum.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.common.Page;
import com.forum.controller.response.ShowRouteResponse;
import com.forum.entity.Buy;
import com.forum.entity.ShoppingCart;
import com.forum.mapper.BuyMapper;
import com.forum.service.BuyService;
import com.forum.service.RouteService;
import com.forum.util.PageUtils;
import com.forum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyServiceImpl implements BuyService {

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private RouteService routeService;

    @Autowired
    private BuyMapper buyMapper;

    /**
     * 展示用户购买
     * @param page
     * @param pageSize
     * @return
     */
    public Page<ShowRouteResponse> showBuy(Integer page, Integer pageSize) {
        // 页面参数处理
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        // 获取用户的购买信息
        String userId = sessionUtils.getUserId();
        QueryWrapper<Buy> buyQueryWrapper = new QueryWrapper<>();
        buyQueryWrapper.eq("user_id", userId);
        Buy buy = buyMapper.selectOne(buyQueryWrapper);

        // 用户购买的路线信息为空
        if (buy.getBuyRoutes() == null) {
            return new Page<>();
        }

        // 获取用户购买的路线信息
        List<ShowRouteResponse> showRouteResponses = new ArrayList<>();
        List<Long> routeIdList = JSON.parseObject(buy.getBuyRoutes(), new TypeReference<List<Long>>(){});
        for (Long routeId : routeIdList) {
            ShowRouteResponse showRouteResponse = new ShowRouteResponse(routeService.getRouteById(routeId));
            showRouteResponses.add(showRouteResponse);
        }

        // 分页处理
        Page<ShowRouteResponse> showRouteResponsePage = new Page<>();
        showRouteResponsePage.setTotal((long) showRouteResponses.size());
        showRouteResponsePage.setPageNum(page);
        showRouteResponsePage.setPages((showRouteResponses.size() - 1) / pageSize + 1);
        showRouteResponsePage.setPageSize(pageSize);
        showRouteResponsePage.setItems(PageUtils.splitList(page, pageSize, showRouteResponses));

        // 返回用户购买的路线信息
        return showRouteResponsePage;
    }
}
