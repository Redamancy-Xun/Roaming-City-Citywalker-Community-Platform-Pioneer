package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.annotation.Checked;
import com.forum.common.Result;
import com.forum.controller.request.CreateCommentParams;
import com.forum.exception.MyException;
import com.forum.service.CommentService;
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
@Api("CommentController")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Checked
    @PostMapping(value = "/createComment", produces = "application/json")
    @ApiOperation(value = "创建一个评论")
    public Result createComment(@NotNull @RequestBody CreateCommentParams createCommentParams) {
        try {
            return Result.success(commentService.createComment(createCommentParams));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/getCommentList", produces = "application/json")
    @ApiOperation(value = "获取评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "postId", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "orderByPopularity", value = "orderByPopularity",  paramType = "query", dataType = "Boolean")
    })
    public Result getCommentList(@NotNull @RequestParam("postId") Long postId,
                                 @NotNull @RequestParam("page") Integer page,
                                 @NotNull @RequestParam("pageSize") Integer pageSize,
                                 @RequestParam("orderByPopularity") Boolean orderByPopularity) {
        try {
            return Result.success(commentService.getCommentList(postId, page, pageSize, orderByPopularity));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

//    @Auth
//    @GetMapping(value = "/comments", produces = "application/json")
//    @ApiOperation(value = "获取评论列表（新，用多表联查实现的）")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "postId", value = "postId", required = true, paramType = "query", dataType = "Long"),
//            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "orderByDesc", value = "是否降序排列", paramType = "query", dataType = "Boolean")
//    })
//    public Result<List<CommentDtoInfo>> comments(@NotNull @RequestParam("postId") Long postId,
//                                                 @NotNull @RequestParam("page") Integer page,
//                                                 @NotNull @RequestParam("pageSize") Integer pageSize,
//                                                 @RequestParam("orderByDesc") Boolean orderByDesc) {
//        try {
//            return Result.success(commentService.comments(postId, page, pageSize, orderByDesc));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }

//    @Auth
//    @GetMapping(value = "/commentsOrderByPopularity", produces = "application/json")
//    @ApiOperation(value = "获取根据热度排序的评论列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "postId", value = "postId", required = true, paramType = "query", dataType = "Long"),
//            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer"),
//    })
//    public Result<List<CommentDtoInfo>> commentsOrderByPopularity(@NotNull @RequestParam("postId") Long postId,
//                                                 @NotNull @RequestParam("page") Integer page,
//                                                 @NotNull @RequestParam("pageSize") Integer pageSize) {
//        try {
//            return Result.success(commentService.commentsOrderByPopularity(postId, page, pageSize));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }

//    @Auth
//    @GetMapping(value = "/allComments", produces = "application/json")
//    @ApiOperation(value = "带删除的评论列表，order为0则正序，为1则倒序，为2则按热门排。不传则默认正序")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "postId", value = "postId", required = true, paramType = "query", dataType = "Long"),
//            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "order", value = "order", required = true, paramType = "query", dataType = "Integer")
//    })
//    public Result<List<CommentDtoInfo>> allComments(@NotNull @RequestParam("postId") Long postId,
//                                                                  @NotNull @RequestParam("page") Integer page,
//                                                                  @NotNull @RequestParam("pageSize") Integer pageSize,
//                                                  @NotNull @RequestParam("order") Integer order) {
//        try {
//            return Result.success(commentService.allComments(postId, page, pageSize,order));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }

    @Auth
    @GetMapping(value = "/getCommentByUserId", produces = "application/json")
    @ApiOperation(value = "获取用户的评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer"),
    })
    public Result getCommentByUserId(@NotNull @RequestParam("postId") String userId,
                                     @NotNull @RequestParam("page") Integer page,
                                     @NotNull @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(commentService.getCommentByUserId(userId, page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/getById", produces = "application/json")
    @ApiOperation(value = "获取评论详情，即使是被删除的评论也可以获取到")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "commentId", required = true, paramType = "query", dataType = "Long"),
    })
    public Result getCommentByUserId(@NotNull @RequestParam("commentId") Long commentId) {
        try {
            return Result.success(commentService.getCommentById(commentId));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Checked
    @PostMapping(value = "/deleteComment", produces = "application/json")
    @ApiOperation(value = "只有评论的作者和管理员可以删除评论，自动判断当前是否为作者或管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "commentId", required = true, paramType = "query", dataType = "Long"),
    })
    public Result deleteComment(@NotNull @RequestParam("commentId") Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return Result.success("删除成功");
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

//    @Auth
//    @GetMapping(value = "/search", produces = "application/json")
//    @ApiOperation(value = "搜索评论、回复")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "content", value = "postOrCommentOrReply", required = true, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
//    })
//    public Result<List<PostInfo>> searchByPostOrCommentOrReply(@NotNull @RequestParam("content") String content,
//                                                               @RequestParam("page") Integer page,
//                                                               @RequestParam("pageSize") Integer pageSize) {
//        try {
//            return Result.success(commentService.search(content,page,pageSize));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }

}
