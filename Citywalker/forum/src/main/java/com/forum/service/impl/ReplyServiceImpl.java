package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forum.common.EnumExceptionType;
import com.forum.exception.MyException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.forum.common.Page;
import com.forum.controller.request.CreateReplyParams;
import com.forum.controller.response.ReplyInfo;
import com.forum.entity.Comment;
import com.forum.entity.Post;
import com.forum.entity.Reply;
import com.forum.entity.User;
import com.forum.mapper.CommentMapper;
import com.forum.mapper.PostMapper;
import com.forum.mapper.ReplyMapper;
import com.forum.mapper.UserMapper;
import com.forum.service.LikeService;
import com.forum.service.NoticeService;
import com.forum.service.ReplyService;
import com.forum.service.UserService;
import com.forum.util.AssertUtil;
import com.forum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private LikeService likeService;

    /**
     * 创建回复
     * @param createReplyParams 回复参数
     * @return 回复
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public Reply createReply(CreateReplyParams createReplyParams) {
        User user = userService.getUserById(sessionUtils.getUserId());

        Reply reply = Reply.builder()
                .userId(sessionUtils.getUserId())
                .createAt(new Date())
                .replyId(createReplyParams.getReplyId())
                .likes(0L)
                .commentId(createReplyParams.getCommentId())
                .content(createReplyParams.getContent())
                .build();

        Comment comment = commentMapper.selectById(reply.getCommentId());
        if (comment == null) {
            throw new MyException(EnumExceptionType.COMENT_NOT_EXIST);
        }
        comment.setReplies(comment.getReplies() + 1);
        if (commentMapper.updateById(comment) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        reply.setPostId(comment.getPostId());
        replyMapper.insert(reply);

        Post post = postMapper.selectById(comment.getPostId());
        post.setComments(post.getComments() + 1);
        if (postMapper.updateById(post) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        String content = reply.getContent();
        if (content.length() >= 30) {
            content = content.substring(0, 30);
            content += "...";
        }

        if (reply.getReplyId() != null) {
            Reply reply1 = getReplyByReplyId(reply.getReplyId());
            reply.setReplyUserId(reply1.getUserId());
            if (replyMapper.updateById(reply) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }

            noticeService.SysSend(reply.getReplyId(), reply1.getUserId(), 6, user.getId(), content);

        } else {
            noticeService.SysSend(comment.getId(), comment.getUserId(), 4, user.getId(), content);
        }

        return reply;
    }

    /**
     * 根据评论id获取回复
     * @param commentId 评论id
     * @param orderByPopularity 是否根据热度排列
     * @param page 页数
     * @param pageSize 每页数量
     * @return 回复列表
     */
    @Override
    public List<ReplyInfo> getReplyList(Long commentId, Boolean orderByPopularity, Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        if (orderByPopularity == null) {
            orderByPopularity = false;
        }

        if (!orderByPopularity) {
            PageHelper.startPage(page, pageSize, "create_at desc");
        } else {
            PageHelper.startPage(page, pageSize, "likes desc , create_at desc");
        }

        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.eq("comment_id", commentId);
        replyQueryWrapper.isNull("delete_at");

        List<Reply> replies = new Page<>(new PageInfo<>(replyMapper.selectList(replyQueryWrapper))).getItems();
        List<ReplyInfo> replyInfos = new ArrayList<>();
        for (Reply reply : replies) {
            User user = userService.getUserById(reply.getUserId());
            Boolean isLike = likeService.check(reply.getId(),2);
            if (reply.getReplyUserId() != null) {
                User replyUser = userService.getUserById(reply.getReplyUserId());
                replyInfos.add(new ReplyInfo(reply, user, replyUser, isLike));
            } else {
                replyInfos.add(new ReplyInfo(reply, user, isLike));
            }
        }

        return replyInfos;
    }

//    /**
//     * 用联查方式实现根据评论id获取回复
//     * @param commentId 评论id
//     * @param orderByDesc 是否倒序
//     * @param page 页数
//     * @param pageSize 每页数量
//     * @return 回复列表
//     */
//    @Override
//    public List<ReplyInfo> replies(Long commentId, Boolean orderByDesc, Integer page, Integer pageSize) {
//        if(page == null || page < 1){
//            page = 1;
//        }
//        if(pageSize == null || pageSize < 1){
//            pageSize = 10;
//        }
//        if (orderByDesc == null){
//            orderByDesc = false;
//        }
//
//        if(orderByDesc){
//            PageHelper.startPage(page, pageSize,"create_at desc");
//        }else{
//            PageHelper.startPage(page, pageSize,"create_at asc");
//        }
//
//        List<ReplyInfo> replies = new Page<>(new PageInfo<>(replyMapper.selectReplies(commentId))).getList();
//
//        for(var reply : replies){
//            if(reply.getReplyUserId() != null){
//                UserDto replyUser = userMapper.selectNameAndAvatar(reply.getReplyUserId());
//                reply.setReplyUserName(replyUser.getName());
//                reply.setReplyUserAvatar(replyUser.getAvatar());
//                reply.setIsLike(likeService.check(reply.getId(),2));
//            }
//        }
//
//        return replies;
//    }

    /**
     * 根据回复id获取回复，包括已删除的
     * @param replyId 回复id
     * @return 回复
     */
    @Override
    public ReplyInfo getReplyById(Long replyId) {
        Reply reply = getReplyByReplyId(replyId);
        User user = userService.getUserById(reply.getUserId());
        Boolean isLike = likeService.check(reply.getId(),2);
        if (reply.getReplyUserId() != null) {
            User replyUser = userService.getUserById(reply.getReplyUserId());
            return new ReplyInfo(reply, user, replyUser, isLike);
        }
        else {
            return new ReplyInfo(reply, user, isLike);
        }
    }

    /**
     * 根据用户id获取回复
     * @param userId 用户id
     * @param page 页数
     * @param pageSize 每页数量
     * @return 回复列表
     */
    @Override
    public List<ReplyInfo> getReplyByUserId(String userId, Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        PageHelper.startPage(page, pageSize,"create_at desc");

        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.eq("user_id",userId);
        replyQueryWrapper.isNull("delete_at");

        List<Reply> replies = new Page<>(new PageInfo<>(replyMapper.selectList(replyQueryWrapper))).getItems();
        List<ReplyInfo> replyInfos = new ArrayList<>();
        for (Reply reply : replies) {
            User user = userService.getUserById(reply.getUserId());
            Boolean isLike = likeService.check(reply.getId(),2);
            if (reply.getReplyUserId() != null) {
                User replyUser = userService.getUserById(reply.getReplyUserId());
                replyInfos.add(new ReplyInfo(reply, user, replyUser, isLike));
            } else {
                replyInfos.add(new ReplyInfo(reply, user, isLike));
            }
        }

        return replyInfos;
    }

    /**
     * 根据回复id删除回复
     * @param replyId 回复id
     * @throws MyException 通用异常
     */
    @Override
    public void deleteReply(Long replyId) throws MyException {
        User user = userService.getUserById(sessionUtils.getUserId());

        Reply reply = getReplyByReplyId(replyId);

        //检查是否有权限删除
        if (user.getRole() <= 2 && !Objects.equals(reply.getUserId(), user.getId())) {
            throw new MyException(EnumExceptionType.PERMISSION_DENIED);
        }

        User replyOwner = userService.getUserById(reply.getUserId());
        if (user.getRole() - replyOwner.getRole() >= 1) {
            throw new MyException(EnumExceptionType.PERMISSION_DENIED);
        }

        likeService.unlikeAll(replyId, 2);

        Comment comment = commentMapper.selectById(reply.getCommentId());
        comment.setReplies(comment.getReplies() - 1);
        if (commentMapper.updateById(comment) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        Post post = postMapper.selectById(comment.getPostId());
        post.setComments(post.getComments() - 1);
        if (postMapper.updateById(post) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        reply.setDeleteAt(new Date());
        if (replyMapper.updateById(reply) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }
    }

    /**
     * 根据回复id获取回复
     * @param replyId 回复id
     * @return 回复
     * @throws MyException 通用异常
     */
    public Reply getReplyByReplyId(Long replyId) throws MyException {
        if (replyId == null) {
            throw new MyException(EnumExceptionType.PARAM_ERROR);
        }
        return replyMapper.selectById(replyId);
    }

}







