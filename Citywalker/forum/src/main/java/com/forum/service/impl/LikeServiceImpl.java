package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forum.common.EnumExceptionType;
import com.forum.entity.Comment;
import com.forum.entity.Likes;
import com.forum.entity.Post;
import com.forum.entity.Reply;
import com.forum.exception.MyException;
import com.forum.mapper.CommentMapper;
import com.forum.mapper.LikeMapper;
import com.forum.mapper.PostMapper;
import com.forum.mapper.ReplyMapper;
import com.forum.service.LikeService;
import com.forum.service.NoticeService;
import com.forum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private NoticeService noticeService;

    /**
     * 点赞帖子
     * @param post 帖子
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    public void likePost(Post post) throws MyException {
        if (post == null || post.getDeleteAt() != null) {
            throw new MyException(EnumExceptionType.POST_NOT_EXIST);
        }
        post.setLikes(post.getLikes() + 1);
        if (postMapper.updateById(post) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        noticeService.SysSend(post.getId(), post.getUserId(), 1, sessionUtils.getUserId(), null);
    }

    /**
     * 点赞
     * @param objectId 对象id
     * @param type 类型
     * @return 是否点赞成功
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public Boolean like(Long objectId, Integer type) throws MyException {
        String userId = sessionUtils.getUserId();

        Likes likes = Likes.builder()
                .likeTime(new Date())
                .userId(userId)
                .objectId(objectId)
                .objectType(type)
                .build();

        //判断是否已经点赞
        QueryWrapper<Likes> likesQueryWrapper = new QueryWrapper<>();
        likesQueryWrapper.eq("user_id", userId)
                .eq("object_id", objectId)
                .eq("object_type", type)
                .isNull("delete_time");
        if (likeMapper.selectCount(likesQueryWrapper) != 0) {
            return false;
        }

        //帖子为0，评论为1，回复为2
        if (type == 0) {
            Post post = postMapper.selectById(objectId);
            likePost(post);
            likes.setToUserId(post.getUserId());
        } else if (type == 1) {
            Comment comment = commentMapper.selectById(objectId);
            if (comment == null || comment.getDeleteAt() != null) {
                throw new MyException(EnumExceptionType.COMMENT_NOT_FOUND);
            }
            comment.setLikes(comment.getLikes() + 1);
            if (commentMapper.updateById(comment) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }

            Post post = postMapper.selectById(comment.getPostId());
            likePost(post);
            likes.setToUserId(comment.getUserId());

            noticeService.SysSend(objectId, comment.getUserId(), 3, userId, null);
        } else if (type == 2) {
            Reply reply = replyMapper.selectById(objectId);
            if (reply == null || reply.getDeleteAt() != null) {
                throw new MyException(EnumExceptionType.REPLY_NOT_FOUND);
            }
            reply.setLikes(reply.getLikes() + 1);
            if (replyMapper.updateById(reply) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }

            likes.setToUserId(reply.getUserId());
            noticeService.SysSend(objectId, reply.getUserId(), 5, userId, null);
        } else {
            throw new MyException(EnumExceptionType.PARAM_ERROR);
        }

        likeMapper.insert(likes);
        return true;
    }

    /**
     * 取消点赞帖子
     * @param objectId 对象id
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    public void unlikePost(Long objectId) throws MyException {
        Post post = postMapper.selectById(objectId);
        if (post == null || post.getDeleteAt() != null) {
            throw new MyException(EnumExceptionType.POST_NOT_EXIST);
        }
        post.setLikes(post.getLikes() - 1);
        if (postMapper.updateById(post) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }
    }

    /**
     * 取消点赞
     * @param objectId 对象id
     * @param type 类型
     * @return  是否取消点赞成功
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public Boolean unlike(Long objectId,Integer type) throws MyException {
        //帖子为0，评论为1，回复为2
        if (type == 0) {
            unlikePost(objectId);
        } else if (type == 1) {
            Comment comment = commentMapper.selectById(objectId);
            if (comment == null || comment.getDeleteAt() != null) {
                throw new MyException(EnumExceptionType.COMMENT_NOT_FOUND);
            }
            comment.setLikes(comment.getLikes() - 1);
            if (commentMapper.updateById(comment) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }
            unlikePost(comment.getPostId());
        } else if (type == 2) {
            Reply reply = replyMapper.selectById(objectId);
            if (reply == null || reply.getDeleteAt() != null) {
                throw new MyException(EnumExceptionType.REPLY_NOT_FOUND);
            }
            reply.setLikes(reply.getLikes() - 1);
            if (replyMapper.updateById(reply) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }
        } else {
            throw new MyException(EnumExceptionType.PARAM_ERROR);
        }

        QueryWrapper<Likes> likeQueryWrapper = new QueryWrapper<>();
        likeQueryWrapper.isNull("delete_time")
                .eq("user_id", sessionUtils.getUserId())
                .eq("object_id", objectId)
                .eq("object_type", type);

        Likes likes = likeMapper.selectOne(likeQueryWrapper);
        likes.setDeleteTime(new Date());

        if (likeMapper.updateById(likes) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }
        return true;
    }

    /**
     * 检查是否点赞
     * @param objectId 对象id
     * @param type 类型
     * @return 是否点赞
     * @throws MyException 通用异常
     */
    @Override
    public Boolean check(Long objectId, Integer type) throws MyException {
        QueryWrapper<Likes> likeQueryWrapper = new QueryWrapper<>();
        likeQueryWrapper.isNull("delete_time")
                .eq("user_id", sessionUtils.getUserId())
                .eq("object_id", objectId)
                .eq("object_type", type);

        return likeMapper.selectCount(likeQueryWrapper) > 0;
    }

    /**
     * 统计用户的点赞数
     * @param userId 用户id
     * @return 点赞数
     */
    @Override
    public Integer count(String userId) {
        QueryWrapper<Likes> likeQueryWrapper = new QueryWrapper<>();
        likeQueryWrapper.isNull("delete_time")
                .eq("user_id", userId);

        return likeMapper.selectCount(likeQueryWrapper);
    }

    @Override
    public void unlikeAll(Long objectId, Integer type) {
        QueryWrapper<Likes> likeQueryWrapper = new QueryWrapper<>();
        likeQueryWrapper.isNull("delete_time")
                .eq("object_id", objectId)
                .eq("object_type", type);

        List<Likes> likes = likeMapper.selectList(likeQueryWrapper);
        for (Likes like : likes) {
            like.setDeleteTime(new Date());
            if (likeMapper.updateById(like) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }
        }
    }
}











