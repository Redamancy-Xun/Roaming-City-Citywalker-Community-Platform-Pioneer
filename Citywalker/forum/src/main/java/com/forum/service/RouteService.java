package com.forum.service;

import com.forum.common.Page;
import com.forum.entity.Route;
import com.forum.entity.User;
import com.forum.exception.MyException;

public interface RouteService {

    Page<Route> showRoute(Integer page, Integer pageSize) throws MyException;

    Route getRouteById(Long routeId) throws MyException;
}
