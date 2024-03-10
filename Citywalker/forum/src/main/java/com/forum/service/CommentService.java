package com.forum.service;

import com.forum.controller.request.CreateCommentParams;
import com.forum.controller.response.CommentInfo;

import java.util.List;

public interface CommentService {

    CommentInfo createComment(CreateCommentParams createCommentParams);

    List<CommentInfo> getCommentList(Long postId, Integer page, Integer pageSize, Boolean orderByPopularity);

    CommentInfo getCommentById(Long commentId);

//    List<CommentDtoInfo> comments(Long postId, Integer page, Integer pageSize, Boolean orderByDesc);

//    List<CommentDtoInfo> allComments(Long postId, Integer page, Integer pageSize,Integer order);

//    List<CommentDtoInfo> commentsOrderByPopularity(Long postId, Integer page, Integer pageSize);

    List<CommentInfo> getCommentByUserId(String userId, Integer page, Integer pageSize);

    void deleteComment(Long commentId);
}
