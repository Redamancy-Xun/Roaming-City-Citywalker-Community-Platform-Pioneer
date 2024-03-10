package com.forum.service;

public interface LikeService {

    Boolean like(Long objectId,Integer type);

    Boolean unlike(Long objectId,Integer type);

    Boolean check(Long objectId,Integer type);

    Integer count(String userId);

    void unlikeAll(Long objectId,Integer type);
}
