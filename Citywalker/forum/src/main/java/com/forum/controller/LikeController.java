package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.annotation.Checked;
import com.forum.common.Result;
import com.forum.exception.MyException;
import com.forum.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@Validated
@Api("PostController")
@RequestMapping("/like") //http://www.redamancy.fun/like/like?id={id}&type={type}
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Checked
    @PostMapping(value = "/like", produces = "application/json")
    @ApiOperation(value = "点赞, 帖子为0, 评论为1, 回复为2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "type", value = "type", required = true, paramType = "query", dataType = "Integer")

    })
    public Result likePost(@NotNull @RequestParam("id") Long id,
                           @NotNull @RequestParam("type") Integer type) {
        try {
            return Result.success(likeService.like(id, type));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Checked
    @PostMapping(value = "/unlike", produces = "application/json")
    @ApiOperation(value = "取消点赞,帖子为0，评论为1，回复为2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "type", value = "type", required = true, paramType = "query", dataType = "Integer")
    })
    public Result unlikePost(@NotNull @RequestParam("id") Long id,
                                      @NotNull @RequestParam("type") Integer type) {
        try {
            return Result.success(likeService.unlike(id, type));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/check", produces = "application/json")
    @ApiOperation(value = "返回是否被点赞过, 帖子为0, 评论为1, 回复为2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "type", value = "type", required = true, paramType = "query", dataType = "Integer")
    })
    public Result check(@NotNull @RequestParam("id") Long id,
                        @NotNull @RequestParam("type") Integer type) {
        try {
            return Result.success(likeService.check(id,type));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/count", produces = "application/json")
    @ApiOperation(value = "返回用户的点赞数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String")
    })
    public Result count(@NotNull @RequestParam("userId") String userId) {
        try {
            return Result.success(likeService.count(userId));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

}
