package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.common.Page;
import com.forum.common.Result;
import com.forum.controller.response.ShowRouteResponse;
import com.forum.entity.Route;
import com.forum.exception.MyException;
import com.forum.service.NoticeService;
import com.forum.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Validated
@Api("处理用户购物车的类")
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping(value = "/show", produces = "application/json")
    @ApiOperation(value = "展示用户购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result showShoppingCart(@RequestParam("page") Integer page,
                                   @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(shoppingCartService.showShoppingCart(page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @PostMapping(value = "/add", produces = "application/json")
    @ApiOperation(value = "加入用户购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", value = "routeId", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "routePeople", value = "routePeople", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "routeTime", value = "routeTime", required = true, paramType = "query", dataType = "String")
    })
    public Result addShoppingCart(@RequestParam("routeId") Long routeId,
                                  @RequestParam("routePeople") Integer routePeople,
                                  @RequestParam("routeTime") String routeTime) {
        try {
            return Result.success(shoppingCartService.addShoppingCart(routeId, routePeople, routeTime));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @PostMapping(value = "/delete", produces = "application/json")
    @ApiOperation(value = "删除购物车内路线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", value = "routeId", required = true, paramType = "query", dataType = "Long"),
    })
    public Result deleteShoppingCart(@RequestParam("routeId") Long routeId) {
        try {
            shoppingCartService.deleteShoppingCart(routeId);
            return Result.success("删除成功");
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @PostMapping(value = "/buy", produces = "application/json")
    @ApiOperation(value = "购买购物车内路线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", value = "routeId", required = true, paramType = "query", dataType = "List<Long>"),
    })
    public Result buyShoppingCart(@RequestParam("routeId") List<Long> routeId) {
        try {
            shoppingCartService.buyShoppingCart(routeId);
            return Result.success("购买成功");
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

}
