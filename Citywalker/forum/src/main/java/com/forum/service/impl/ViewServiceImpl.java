package com.forum.service.impl;

import com.forum.mapper.PostMapper;
import com.forum.service.ViewService;
import com.forum.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.forum.common.CommonConstants.VIEW;

@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PostMapper postMapper;

    /**
     * 生成redis中的key
     * @param objectId 对象id
     * @return redis中的key
     */
    private String VIEW_COUNT(Long objectId) {
        return Long.toString(objectId);
    }

    /**
     * 从redis中获取浏览量
     * @param postId 帖子id
     * @return 浏览量
     */
    private Long getViewsFromRedis(Long postId) {
        try {
            return (Long)redisUtils.hget(VIEW, VIEW_COUNT(postId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 增加浏览量
     * @param postId 帖子id
     * @return 增加后的浏览量
     */
    @Override
    public Long addView(Long postId) {
        Long views = getView(postId);
        Map<String, Object> viewCount = new HashMap<>();
        viewCount.put(VIEW_COUNT(postId), views+1);
        redisUtils.hmset(VIEW, viewCount);

        return  views + 1 ;
    }

    /**
     * 获取浏览量
     * @param postId 帖子id
     * @return 浏览量
     */
    @Override
    public Long getView(Long postId) {
        Long views = getViewsFromRedis(postId);
        if (views != null) return views;
        views = postMapper.selectById(postId).getViews();
        Map<String, Object> viewCount = new HashMap<>();
        viewCount.put(VIEW_COUNT(postId), views);
        redisUtils.hmset(VIEW, viewCount);

        return views;
    }

}








