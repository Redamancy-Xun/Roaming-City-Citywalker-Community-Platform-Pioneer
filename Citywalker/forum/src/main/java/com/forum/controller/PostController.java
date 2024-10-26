package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.annotation.Checked;
import com.forum.common.Result;
import com.forum.exception.MyException;
import com.forum.controller.request.CreatePostParams;
import com.forum.service.PostService;
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
@Api("PostController")
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private SessionUtils sessionUtils;

    @Checked
    @PostMapping(value = "/createPost", produces = "application/json")
    @ApiOperation(value = "创建一个帖子")
    public Result createPost(@NotNull @RequestBody CreatePostParams createPostParams) {
        try {
            return Result.success(postService.createPost(createPostParams));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/getPostList", produces = "application/json")
    @ApiOperation(value = "获取帖子列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page",paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize",  paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "orderByPopularity", value = "orderByPopularity",  paramType = "query", dataType = "Boolean")
    })
    public Result getPostList(@RequestParam("page") Integer page,
                              @RequestParam("pageSize") Integer pageSize,
                              @RequestParam("orderByPopularity") Boolean orderByPopularity) {
        try {
            return Result.success(postService.getPostList(page,pageSize,orderByPopularity));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

//    @Auth
//    @GetMapping(value = "/posts", produces = "application/json")
//    @ApiOperation(value = "获取帖子列表（新，用多表联查实现的）")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "page", value = "page",paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize",  paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "orderByPopularity", value = "orderByPopularity",  paramType = "query", dataType = "Boolean")
//    })
//    public Result<List<PostDtoInfo>> posts(@RequestParam("page") Integer page,
//                                           @RequestParam("pageSize") Integer pageSize,
//                                           @RequestParam("orderByPopularity") Boolean orderByPopularity) {
//        try {
//            return Result.success(postService.posts(page,pageSize,orderByPopularity));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }

    @Auth
    @GetMapping(value = "/getPostById", produces = "application/json")
    @ApiOperation(value = "获取帖子详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "postId", required = true, paramType = "query", dataType = "Long")
    })
    public Result getPostById(@RequestParam("postId") Long postId) {
        try {
            return Result.success(postService.getPostById(postId));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

//    @Auth
//    @GetMapping(value = "/get", produces = "application/json")
//    @ApiOperation(value = "获取帖子详情，即使是被删除的帖子也可以获取到，除非帖子id不存在")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "postId", value = "postId", required = true, paramType = "query", dataType = "Long")
//    })
//    public Result<PostDtoInfo> get(@RequestParam("postId") Long postId) {
//        try {
//            return Result.success(postService.getPost(postId));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }

    @Checked
    @GetMapping(value = "/getPostByUserId", produces = "application/json")
    @ApiOperation(value = "获取用户的帖子列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result getPostByUserId(@RequestParam("userId") String userId,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(postService.getPostByUserId(userId,page,pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Checked
    @PostMapping(value = "/deletePost", produces = "application/json")
    @ApiOperation(value = "删除帖子")
    public Result deletePost(@NotNull @RequestParam("postId") Long postId) {
        try {
            return Result.success(postService.deletePost(postId));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

//    @Auth
//    @GetMapping(value = "/searchByPostOrCommentOrReply", produces = "application/json")
//    @ApiOperation(value = "搜索帖子、评论、回复")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "postOrCommentOrReply", value = "postOrCommentOrReply", required = true, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "orderByPopularity", value = "orderByPopularity", required = true, paramType = "query", dataType = "Boolean"),
//            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
//    })
//    public Result searchByPostOrCommentOrReply(@NotNull @RequestParam("postOrCommentOrReply") String postOrCommentOrReply,
//                                               @RequestParam("orderByPopularity") Boolean orderByPopularity,
//                                               @RequestParam("page") Integer page,
//                                               @RequestParam("pageSize") Integer pageSize) {
//        try {
//            return Result.success(postService.searchByPostOrCommentOrReply(postOrCommentOrReply,orderByPopularity,page,pageSize));
//        } catch (MyException e) {
//            return Result.result(e.getEnumExceptionType());
//        }
//    }

//    @Auth
//    @GetMapping(value = "/search", produces = "application/json")
//    @ApiOperation(value = "搜索帖子、评论、回复 (new)")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "postOrCommentOrReply", value = "postOrCommentOrReply", paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "orderByPopularity", value = "orderByPopularity", required = true, paramType = "query", dataType = "Boolean"),
//            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
//    })
//    public Result<List<PostDtoInfo>> search(@NotNull @RequestParam("postOrCommentOrReply") String postOrCommentOrReply,
//                                                               @RequestParam("orderByPopularity") Boolean orderByPopularity,
//                                                               @RequestParam("page") Integer page,
//                                                               @RequestParam("pageSize") Integer pageSize) {
//        try {
//            return Result.success(postService.search(postOrCommentOrReply.trim(),orderByPopularity,page,pageSize));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }




}







