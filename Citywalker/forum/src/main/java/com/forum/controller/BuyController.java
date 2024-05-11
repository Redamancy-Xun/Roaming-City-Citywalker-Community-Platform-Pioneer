package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.common.Result;
import com.forum.exception.MyException;
import com.forum.service.BuyService;
import com.forum.service.impl.BuyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
@Api("处理用户购买的类")
@RequestMapping("/buy")
public class BuyController {

    @Autowired
    private BuyService buyService;

    /**
     * 展示用户购买
     * @param page 页码
     * @param pageSize 每页数量
     * @return Result
     * @throws MyException
     */
    @Auth
    @PostMapping(value = "/show", produces = "application/json")
    @ApiOperation(value = "展示用户购买")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result showBuy(@RequestParam("page") Integer page,
                           @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(buyService.showBuy(page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }
}
