package com.forum.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.forum.common.EnumExceptionType;
import com.forum.exception.MyException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.forum.common.Page;
import com.forum.controller.request.CreateCommentParams;
import com.forum.controller.response.CommentInfo;
import com.forum.entity.Comment;
import com.forum.entity.Post;
import com.forum.entity.Reply;
import com.forum.entity.User;
import com.forum.mapper.CommentMapper;
import com.forum.mapper.PostMapper;
import com.forum.mapper.ReplyMapper;
import com.forum.service.CommentService;
import com.forum.service.LikeService;
import com.forum.service.NoticeService;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private LikeService likeService;

    /**
     * 创建评论
     * @param createCommentParams 评论参数
     * @return 评论信息
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public CommentInfo createComment(CreateCommentParams createCommentParams) throws MyException{
        User user = userService.getUserById(sessionUtils.getUserId());

        String images = JSON.toJSONString(createCommentParams.getImages());

        Comment comment = Comment.builder()
                .userId(sessionUtils.getUserId())
                .postId(createCommentParams.getPostId())
                .content(createCommentParams.getContent())
                .createAt(new Date())
                .images(images)
                .likes(0L)
                .replies(0L)
                .version(1)
                .build();

        Post post = postMapper.selectById(createCommentParams.getPostId());

        if (post.getComments() == 0) {
            comment.setFloor(1L);
        } else {
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("post_id", createCommentParams.getPostId());
            queryWrapper.orderByDesc("create_at");
            queryWrapper.last("limit 1");
            Comment lastComment = commentMapper.selectOne(queryWrapper);
            comment.setFloor(lastComment.getFloor() + 1);
        }
        commentMapper.insert(comment);

        post.setComments(post.getComments() + 1);
//        post.setUpdateAt(new Date());
        if (postMapper.updateById(post) == 0){
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        String content = comment.getContent();
        if (content.length() >= 30) {
            content = content.substring(0,30);
            content += "...";
        }
        if (!createCommentParams.getImages().isEmpty()) {
            content += "[图片]";
        }

        noticeService.SysSend(post.getId(), post.getUserId(), 2, user.getId(), content);

        return new CommentInfo(comment, user, false);
    }

    /**
     * 获取评论列表
     * @param postId 帖子id
     * @param page 页码
     * @param pageSize 每页数量
     * @return 评论列表
     * @throws MyException 通用异常
     */
    @Override
    public List<CommentInfo> getCommentList(Long postId, Integer page, Integer pageSize, Boolean orderByPopularity) throws MyException {
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
            PageHelper.startPage(page, pageSize, "likes+replies desc , create_at desc");
        }

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("post_id", postId);
        commentQueryWrapper.isNull("delete_at");

        List<Comment> comments = new Page<>(new PageInfo<>(commentMapper.selectList(commentQueryWrapper))).getItems();
        List<CommentInfo> commentInfos = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userService.getUserById(comment.getUserId());
            Boolean isLike = likeService.check(comment.getId(), 1);
            commentInfos.add(new CommentInfo(comment, user, isLike));
        }

        return commentInfos;
    }

//    /**
//     * 用联查实现获取评论列表
//     * @param postId 帖子id
//     * @param page  页码
//     * @param pageSize 每页数量
//     * @param orderByDesc 是否倒序
//     * @return 评论列表
//     */
//    @Override
//    public List<CommentDtoInfo> comments(Long postId, Integer page, Integer pageSize, Boolean orderByDesc) {
//        if(page == null || page < 1){
//            page = 1;
//        }
//        if(pageSize == null || pageSize < 1){
//            pageSize = 10;
//        }
//        if(orderByDesc == null){
//            orderByDesc = false;
//        }
//
//        if(orderByDesc){
//            PageHelper.startPage(page, pageSize, "create_at desc");
//        }else{
//            PageHelper.startPage(page, pageSize, "create_at asc");
//        }
//
//        List<CommentDto> comments = new Page<>(new PageInfo<>(commentMapper.selectCommentsByPostId(postId))).getList();
//        List<CommentDtoInfo> commentInfos = new ArrayList<>();
//        for(var comment : comments){
//            CommentDtoInfo dto = new CommentDtoInfo(comment);
//            dto.setIsLike(likeService.check(comment.getId(),1));
//            commentInfos.add(dto);
//        }
//
//        return commentInfos;
//    }

//    /**
//     * 获取评论列表（包括已删除）
//     * @param postId 帖子id
//     * @param page 页码
//     * @param pageSize 每页数量
//     * @param order 排序方式
//     * @return 评论列表
//     */
//    @Override
//    public List<CommentDtoInfo> allComments(Long postId, Integer page, Integer pageSize,Integer order) {
//        if(page == null || page < 1){
//            page = 1;
//        }
//        if(pageSize == null || pageSize < 1){
//            pageSize = 10;
//        }
//        if(order == null){
//            order = 0;
//        }
//
//        switch (order) {
//            case 1 -> PageHelper.startPage(page, pageSize, "create_at desc");
//            case 2 -> PageHelper.startPage(page, pageSize, "likes+replies desc,create_at asc");
//            default -> PageHelper.startPage(page, pageSize, "create_at asc");
//        }
//
//        List<CommentDto> comments = new Page<>(new PageInfo<>(commentMapper.selectCommentsByPostIdWithoutDeleteAt(postId))).getList();
//        List<CommentDtoInfo> commentInfos = new ArrayList<>();
//        for(var comment : comments){
//            CommentDtoInfo dto = new CommentDtoInfo(comment);
//            dto.setIsLike(likeService.check(comment.getId(),1));
//            commentInfos.add(dto);
//        }
//
//        return commentInfos;
//    }

    /**
     * 获取评论详情
     * @param commentId 评论id
     * @return 评论
     * @throws MyException 通用异常
     */
    @Override
    public CommentInfo getCommentById(Long commentId) throws MyException {
        Comment comment = commentMapper.selectById(commentId);
        User user = userService.getUserById(comment.getUserId());
        Boolean isLike = likeService.check(comment.getId(), 1);

        return new CommentInfo(comment, user, isLike);

    }

//    /**
//     * 根据热度排序获取评论列表
//     * @param postId 帖子id
//     * @param page 页码
//     * @param pageSize 每页数量
//     * @return 评论列表
//     */
//    @Override
//    public List<CommentDtoInfo> commentsOrderByPopularity(Long postId, Integer page, Integer pageSize) {
//        if(page == null || page < 1){
//            page = 1;
//        }
//        if(pageSize == null || pageSize < 1){
//            pageSize = 10;
//        }
//
//        PageHelper.startPage(page, pageSize, "likes+replies desc, create_at asc");
//
//        List<CommentDto> comments = new Page<>(new PageInfo<>(commentMapper.selectCommentsByPostId(postId))).getList();
//        List<CommentDtoInfo> commentInfos = new ArrayList<>();
//        for(var comment : comments){
//            commentInfos.add(new CommentDtoInfo(comment));
//        }
//
//        return commentInfos;
//    }

    /**
     * 根据用户id获取评论
     * @param userId 用户id
     * @param page 页码
     * @param pageSize 每页数量
     * @return 评论列表
     * @throws MyException 通用异常
     */
    @Override
    public List<CommentInfo> getCommentByUserId(String userId, Integer page, Integer pageSize) throws MyException {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        PageHelper.startPage(page, pageSize, "create_at desc");

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("user_id", userId);
        commentQueryWrapper.isNull("delete_at");

        List<Comment> comments = new Page<>(new PageInfo<>(commentMapper.selectList(commentQueryWrapper))).getItems();
        List<CommentInfo> commentInfos = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userService.getUserById(userId);
            Boolean isLike = likeService.check(comment.getId(), 1);
            commentInfos.add(new CommentInfo(comment, user, isLike));
        }

        return commentInfos;
    }

    /**
     * 删除评论
     * @param commentId 评论id
     * @throws MyException 通用异常
     */
    @Override
    public void deleteComment(Long commentId) throws MyException {
        User user = userService.getUserById(sessionUtils.getUserId());

        Comment comment = commentMapper.selectById(commentId);

        // 检查是否有权限删除
        if (user.getRole() >=2 && !Objects.equals(comment.getUserId(), user.getId())) {
            throw new MyException(EnumExceptionType.PERMISSION_DENIED);
        }

        User commentOwner = userService.getUserById(comment.getUserId());
        if (user.getRole() - commentOwner.getRole() >= 1) {
            throw new MyException(EnumExceptionType.PERMISSION_DENIED);
        }

        likeService.unlikeAll(commentId, 1);

        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.isNull("delete_at");
        replyQueryWrapper.eq("comment_id", commentId);
        List<Reply> replies = replyMapper.selectList(replyQueryWrapper);
        for (Reply reply : replies) {
            reply.setDeleteAt(new Date());
            if (replyMapper.updateById(reply) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }

            likeService.unlikeAll(reply.getId(), 2);
        }

        Post post = postMapper.selectById(comment.getPostId());
        post.setComments(post.getComments() - comment.getReplies() - 1);
        if (postMapper.updateById(post) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        comment.setDeleteAt(new Date());
        if (commentMapper.updateById(comment) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }
    }


}







