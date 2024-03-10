package com.forum.service.impl;

import com.forum.common.EnumExceptionType;
import com.forum.exception.MyException;
import com.forum.entity.Post;
import com.forum.mapper.PostMapper;
import com.forum.service.ViewService;
import com.forum.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.forum.common.CommonConstants.VIEW;

@Service
@Slf4j
public class ScheduledTasks {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ViewService viewService;

    /**
     * 浏览量入mysql库
     *
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    //从午夜开始每12小时执行一次
    @Scheduled(cron = "0 0 0/12 * * ? ")
    public void views2database() throws MyException {
        Map<Object, Object> viewCount = redisUtils.hmget(VIEW);

        if (viewCount == null || viewCount.isEmpty()) {
            return;
        }

        for (Map.Entry<Object, Object> entry : viewCount.entrySet()) {
            if (!(entry.getKey() instanceof Long) || !(entry.getValue() instanceof Long)) {
                throw new MyException(EnumExceptionType.VIEW_COUNT_ERROR);
            }

            Post post = postMapper.selectById((Long) entry.getKey());
            post.setViews((Long) entry.getValue());
            if (postMapper.updateById(post) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }
        }

        try {
            Thread.sleep(1000 * 5);
            redisUtils.del(VIEW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
