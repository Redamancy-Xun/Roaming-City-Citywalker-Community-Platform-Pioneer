package com.forum.service;

import com.forum.controller.request.CreatePostParams;
import com.forum.controller.response.PostInfo;

import java.util.List;

public interface PostService {
    PostInfo createPost(CreatePostParams createPostParams);

    List<PostInfo> getPostList(Integer page, Integer pageSize, Boolean orderByPopularity);

//    List<PostDtoInfo> posts(Integer page,Integer pageSize,Boolean orderByPopularity);

    PostInfo getPostById(Long postId);

//    PostDtoInfo getPost(Long postId);

    List<PostInfo> getPostByUserId(String userId, Integer page, Integer pageSize);

    Boolean deletePost(Long postId);

//    List<PostInfo> searchByPostOrCommentOrReply(String postOrCommentOrReply,Boolean orderByPopularity , Integer page, Integer pageSize);

//    List<PostDtoInfo> search(String postOrCommentOrReply,Boolean orderByPopularity , Integer page, Integer pageSize);
}
