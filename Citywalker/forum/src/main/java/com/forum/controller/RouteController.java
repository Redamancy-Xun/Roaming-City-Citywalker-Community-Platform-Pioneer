package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.common.Page;
import com.forum.common.Result;
import com.forum.controller.response.ShowRouteResponse;
import com.forum.entity.Route;
import com.forum.exception.MyException;
import com.forum.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Validated
@Api("对路线进行操作的类")
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Auth
    @GetMapping(value = "/show", produces = "application/json")
    @ApiOperation(value = "展示所有路线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result showRoute(@RequestParam("page") Integer page,
                            @RequestParam("pageSize") Integer pageSize) {
        try {
            Page<Route> routesOriginal= routeService.showRoute(page, pageSize);
            Page<ShowRouteResponse> routes = new Page<>();
            routes.setPages(routesOriginal.getPages());
            routes.setTotal(routesOriginal.getTotal());
            routes.setPageNum(routesOriginal.getPageNum());
            routes.setPageSize(routesOriginal.getPageSize());

            List<ShowRouteResponse> showRouteResponses = new ArrayList<>();
            for (Route route : routesOriginal.getItems()) {
                showRouteResponses.add(new ShowRouteResponse(route));
            }
            routes.setItems(showRouteResponses);

            return Result.success(routes);
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/getRouteById", produces = "application/json")
    @ApiOperation(value = "获取对应RouteId的路线信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", value = "路线id", required = true, paramType = "query", dataType = "Long") })
    public Result getRouteById(@NotNull @RequestParam("routeId") Long routeId) {
        try {
            return Result.success(new ShowRouteResponse(routeService.getRouteById(routeId)));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }
}
