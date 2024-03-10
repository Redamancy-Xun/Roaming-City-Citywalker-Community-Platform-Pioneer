package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.annotation.Checked;
import com.forum.common.Result;
import com.forum.exception.MyException;
import com.forum.service.StarService;
import com.forum.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
@Validated
@Api("StarController")
@RequestMapping("/star")
public class StarController {

    @Autowired
    private StarService starService;

    @Autowired
    private SessionUtils sessionUtils;

    @Checked
    @PostMapping(value = "/starPost", produces = "application/json")
    @ApiOperation(value = "收藏帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "postId", required = true, paramType = "query", dataType = "Long"),
    })
    public Result starPost(@NotNull @RequestParam("postId") Long postId) {
        try {
            return Result.success(starService.starPost(postId));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Checked
    @PostMapping(value = "/unstarPost", produces = "application/json")
    @ApiOperation(value = "取消收藏帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "postId", required = true, paramType = "query", dataType = "Long"),
    })
    public Result unstarPost(@NotNull @RequestParam("postId") Long postId) {
        try {
            return Result.success(starService.unstarPost(postId));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/check", produces = "application/json")
    @ApiOperation(value = "检查是否收藏帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "postId", required = true, paramType = "query", dataType = "Long"),
    })
    public Result check(@NotNull @RequestParam("postId") Long postId) {
        try {
            return Result.success(starService.check(postId, sessionUtils.getUserId()));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Checked
    @GetMapping(value = "/getStarList", produces = "application/json")
    @ApiOperation(value = "获取自己的收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer"),
    })
    public Result getStarListByUserId(@RequestParam("page") Integer page,
                                      @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(starService.getStarList(page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

//    @Checked
//    @GetMapping(value = "/get", produces = "application/json")
//    @ApiOperation(value = "获取自己的收藏列表（新）")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer"),
//    })
//    public Result<List<PostDtoInfo>> get(@RequestParam("page") Integer page,
//                                         @RequestParam("pageSize") Integer pageSize) {
//        try {
//            return Result.success(starService.get(page,pageSize));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }



}






