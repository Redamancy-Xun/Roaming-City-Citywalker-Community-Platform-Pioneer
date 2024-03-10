package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forum.common.EnumExceptionType;
import com.forum.common.Page;
import com.forum.entity.Route;
import com.forum.exception.MyException;
import com.forum.mapper.RouteMapper;
import com.forum.service.RouteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteMapper routeMapper;

    /**
     * 展示所有路线
     * @param page 页码
     * @param pageSize 每页数量
     * @return Page<Route>
     * @throws MyException 通用异常
     */
    @Override
    public Page<Route> showRoute(Integer page, Integer pageSize) throws MyException {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        PageHelper.startPage(page, pageSize);
        QueryWrapper<Route> routeQueryWrapper = new QueryWrapper<>();

        return new Page<>(new PageInfo<>(routeMapper.selectList(routeQueryWrapper)));
    }

    /**
     * 根据routeId获取路线信息
     * @param routeId 路线id
     * @return Route
     * @throws MyException 通用异常
     */
    @Override
    public Route getRouteById(Long routeId) throws MyException{
        Route route = routeMapper.selectById(routeId);
        if (route == null){
            throw new MyException(EnumExceptionType.ROUTE_NOT_EXIST);
        }
        return route;
    }

}
