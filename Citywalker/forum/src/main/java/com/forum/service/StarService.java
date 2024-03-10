package com.forum.service;

import com.forum.controller.response.PostInfo;

import java.util.List;

public interface StarService {

    Boolean starPost(Long postId);

    Boolean unstarPost(Long postId);

    Boolean check(Long postId,String userId);

    List<PostInfo> getStarList(Integer page, Integer pageSize);

//    List<PostInfo> get(Integer page,Integer pageSize);
}
