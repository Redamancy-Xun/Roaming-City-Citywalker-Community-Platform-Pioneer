package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forum.common.EnumExceptionType;
import com.forum.controller.response.PostInfo;
import com.forum.entity.Post;
import com.forum.entity.Star;
import com.forum.entity.User;
import com.forum.exception.MyException;
import com.forum.mapper.PostMapper;
import com.forum.mapper.StarMapper;
import com.forum.service.LikeService;
import com.forum.service.StarService;
import com.forum.service.UserService;
import com.forum.util.PageUtils;
import com.forum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StarServiceImpl implements StarService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private StarMapper starMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private LikeService likeService;

    /**
     * 获取收藏列表
     * @param postId 帖子id
     * @return 是否收藏
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public Boolean starPost(Long postId) throws MyException {
        Post post = postMapper.selectById(postId);
        String userId = sessionUtils.getUserId();
        if (post == null || post.getDeleteAt() != null) {
            throw new MyException(EnumExceptionType.POST_NOT_EXIST);
        }

        QueryWrapper<Star> starQueryWrapper = new QueryWrapper<>();
        starQueryWrapper.eq("user_id", userId);
        starQueryWrapper.eq("post_id", postId);
        starQueryWrapper.isNull("delete_at");
        if (starMapper.selectCount(starQueryWrapper) != 0) {
            throw new MyException(EnumExceptionType.STAR_EXIST);
        }

        Star star = Star.builder()
                .userId(userId)
                .starCreatedAt(new Date())
                .postId(postId)
                .build();
        if (starMapper.insert(star) == 0) {
            throw new MyException(EnumExceptionType.STAR_FAILED);
        }

        post.setStars(post.getStars() + 1);
        if (postMapper.updateById(post) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        return true;
    }

    /**
     * 取消收藏
     * @param postId    帖子id
     * @return  是否取消收藏成功
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public Boolean unstarPost(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null || post.getDeleteAt() != null) {
            throw new MyException(EnumExceptionType.POST_NOT_EXIST);
        }

        QueryWrapper<Star> starQueryWrapper = new QueryWrapper<>();
        starQueryWrapper.eq("user_id", sessionUtils.getUserId());
        starQueryWrapper.eq("post_id", postId);
        starQueryWrapper.isNull("delete_at");

        Star star = starMapper.selectOne(starQueryWrapper);
        if (star == null) {
            throw new MyException(EnumExceptionType.UNSATR_FAIL);
        }
        star.setDeleteAt(new Date());
        if (starMapper.updateById(star) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        post.setStars(post.getStars() - 1);
        if (postMapper.updateById(post) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        return true;
    }

    /**
     * 检查用户是否收藏了该帖子
     * @param postId 帖子id
     * @return 是否收藏
     */
    @Override
    public Boolean check(Long postId, String userId) {
        QueryWrapper<Star> starQueryWrapper = new QueryWrapper<>();
        starQueryWrapper.eq("user_id", userId);
        starQueryWrapper.eq("post_id", postId);
        starQueryWrapper.isNull("delete_at");
        return starMapper.selectOne(starQueryWrapper) != null;
    }

    /**
     * 获取用户收藏的帖子列表
     * @return 帖子列表
     */
    @Override
    public List<PostInfo> getStarList(Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        List<Long> postIds = starMapper.getStarPostIdListByUserId(sessionUtils.getUserId());

        List<PostInfo> postInfoList = new ArrayList<>();
        for (Long postId : postIds) {
            Post post = postMapper.selectById(postId);
            String userId = post.getUserId();
            User user = userService.getUserById(userId);
            Boolean isLike = likeService.check(post.getId(), 0);
            Boolean isStar = check(post.getId(), sessionUtils.getUserId());
            postInfoList.add(new PostInfo(post, user, isLike, isStar));
        }

        //方便！！！
        return PageUtils.splitList(page, pageSize, postInfoList);
    }

//    /**
//     * 分页获取用户收藏的帖子列表
//     * @param page 页码
//     * @param pageSize  每页大小
//     * @return  帖子列表
//     */
//    @Override
//    public List<PostDtoInfo> get(Integer page,Integer pageSize) {
//        String userId = sessionUtils.getUserId();
//        List<Long> postIds = starMapper.getStarPostIdListByUserId(userId);
//
//        List<PostDto> res = new ArrayList<>();
//        for(var postId : postIds){
//            PostDto postDto = postMapper.selectPostByIdWithoutDeleteAt(postId);
//            if(postDto != null) {
//                res.add(postDto);
//            }
//        }
//
//        List<PostDtoInfo> postInfoList = new ArrayList<>();
//        for(var post : res){
//            PostDtoInfo dto = new PostDtoInfo(post);
//            dto.setIsStar(check(post.getId(),userId));
//            dto.setIsLike(likeService.check(post.getId(),0));
//            postInfoList.add(dto);
//        }
//
//        return PageUtils.splitList(page, pageSize, postInfoList);
//    }


}
