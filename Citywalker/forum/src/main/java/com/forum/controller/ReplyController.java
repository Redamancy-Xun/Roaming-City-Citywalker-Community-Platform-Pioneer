package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.annotation.Checked;
import com.forum.common.Result;
import com.forum.controller.request.CreateReplyParams;
import com.forum.controller.response.ReplyInfo;
import com.forum.entity.Reply;
import com.forum.exception.MyException;
import com.forum.service.ReplyService;
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
@Api("ReplyController")
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Checked
    @PostMapping(value = "/createReply", produces = "application/json")
    @ApiOperation(value = "创建回复")
    public Result createReply(@NotNull @RequestBody CreateReplyParams createReplyParams) {
        try {
            return Result.success(replyService.createReply(createReplyParams));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/getReplyList", produces = "application/json")
    @ApiOperation(value = "获取回复列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "commentId", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "page" ,paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "orderByPopularity", value = "orderByPopularity",  paramType = "query", dataType = "Boolean")
    })
    public Result getReplyList(@NotNull @RequestParam("commentId") Long commentId,
                               @RequestParam("page") Integer page,
                               @RequestParam("pageSize") Integer pageSize,
                               @RequestParam("orderByPopularity") Boolean orderByPopularity) {
        try {
            return Result.success(replyService.getReplyList(commentId, orderByPopularity, page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

//    @Auth
//    @GetMapping(value = "/replies", produces = "application/json")
//    @ApiOperation(value = "获取回复列表（新，用多表联查实现的）")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "commentId", value = "commentId", required = true, paramType = "query", dataType = "Long"),
//            @ApiImplicitParam(name = "orderByDesc", value = "orderByDesc", paramType = "query", dataType = "Boolean",defaultValue = "false"),
//            @ApiImplicitParam(name = "page", value = "page" ,paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", dataType = "Integer"),
//    })
//    public Result<List<ReplyInfo>> replies(@NotNull @RequestParam("commentId") Long commentId,
//                                           @RequestParam("orderByDesc")Boolean orderByDesc,
//                                           @RequestParam("page") Integer page,
//                                           @RequestParam("pageSize") Integer pageSize) {
//        try {
//            return Result.success(replyService.replies(commentId,orderByDesc,page,pageSize));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }

    @Auth
    @GetMapping(value = "/getReplyByUserId", produces = "application/json")
    @ApiOperation(value = "获取用户回复列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "page" ,paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", dataType = "Integer"),
    })
    public Result getReplyByUserId(@NotNull @RequestParam("userId") String userId,
                                   @RequestParam("page") Integer page,
                                   @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(replyService.getReplyByUserId(userId, page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/getById", produces = "application/json")
    @ApiOperation(value = "获取回复详情，即使是被删除的回复也可以获取到")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "replyId", value = "replyId", required = true, paramType = "query", dataType = "Long"),
    })
    public Result getReplyByUserId(@NotNull @RequestParam("replyId") Long replyId) {
        try {
            return Result.success(replyService.getReplyById(replyId));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Checked
    @PostMapping(value = "/deleteReply", produces = "application/json")
    @ApiOperation(value = "删除回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "replyId", value = "replyId", required = true, paramType = "query", dataType = "Long"),
    })
    public Result deleteReply(@NotNull @RequestParam("replyId") Long replyId){
        try {
            replyService.deleteReply(replyId);
            return Result.success("删除成功");
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

}
