package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.common.Result;
import com.forum.exception.MyException;
import com.forum.service.NoticeService;
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
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Auth
    @GetMapping(value = "/check", produces = "application/json")
    @ApiOperation(value = "查看当前用户有无通知")
    public Result check() {
        try {
            return Result.success(noticeService.hasUnread());
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

//    @Auth
//    @GetMapping(value = "/getList", produces = "application/json")
//    @ApiOperation(value = "获取对应类型的通知列表并清空未读消息，返回通知列表。type：0:系统 1:给帖子点赞 2:给帖子回复 3:给评论点赞 4:给评论回复 5:给回复点赞 6:给回复回复")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "type", value = "type", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
//    })
//    public Result getList(@NotNull @RequestParam("type") Integer type,
//                                         @NotNull @RequestParam("page") Integer page,
//                                         @NotNull @RequestParam("pageSize") Integer pageSize) {
//        try {
//            return Result.success(noticeService.getNoticeList(type,page,pageSize).getItems());
//        } catch (MyException e) {
//            return Result.result(e.getEnumExceptionType());
//        }
//    }

    @Auth
    @GetMapping(value = "/notices", produces = "application/json")
    @ApiOperation(value = "获取对应类型的通知列表并清空未读消息，返回通知列表。type：0:系统 1:点赞 2:回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "type", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result notices(@NotNull @RequestParam("type") Integer type,
                          @NotNull @RequestParam("page") Integer page,
                          @NotNull @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(noticeService.getNotices(type,page,pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @PostMapping(value = "/send", produces = "application/json")
    @ApiOperation(value = "给指定用户发送系统通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "msg", value = "msg", required = true, paramType = "query", dataType = "String")
    })
    public Result send(@NotNull @RequestParam("userId") String userId,
                       @NotNull @RequestParam("msg") String msg) {
        try {
            return Result.success(noticeService.SysSend(msg, userId));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @PostMapping(value = "/sendAll", produces = "application/json")
    @ApiOperation(value = "给所有用户发送系统通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "msg", required = true, paramType = "query", dataType = "String")
    })
    public Result sendAll(@NotNull @RequestParam("msg") String msg) {
        try {
            return Result.success(noticeService.SysSend(msg));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @PostMapping(value = "/sendMessage", produces = "application/json")
    @ApiOperation(value = "给用户发送信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "msg", value = "msg", required = true, paramType = "query", dataType = "String")
    })
    public Result sendMessage(@NotNull @RequestParam("userId") String userId,
                              @NotNull @RequestParam("msg") String msg) {
        try {
            return Result.success(noticeService.SysSend(msg, userId, 7));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/userNotices", produces = "application/json")
    @ApiOperation(value = "返回我的信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result userNotices(@NotNull @RequestParam("page") Integer page,
                              @NotNull @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(noticeService.getUserNotices(page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/likeAndReplyNotices", produces = "application/json")
    @ApiOperation(value = "返回赞与评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result likeAndReplyNotices(@NotNull @RequestParam("page") Integer page,
                                      @NotNull @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(noticeService.getLikeAndReplyNotices(page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/noticesById", produces = "application/json")
    @ApiOperation(value = "查看消息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "Long"),
    })
    public Result userNotices(@NotNull @RequestParam("id") Long id) {
        try {
            return Result.success(noticeService.getNoticeById(id));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

}















