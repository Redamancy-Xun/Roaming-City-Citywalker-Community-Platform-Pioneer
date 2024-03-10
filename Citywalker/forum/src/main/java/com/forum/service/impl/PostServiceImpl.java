package com.forum.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.forum.common.EnumExceptionType;
import com.forum.exception.MyException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.forum.common.Page;
import com.forum.controller.request.CreatePostParams;
import com.forum.controller.response.PostInfo;
import com.forum.entity.Comment;
import com.forum.entity.Post;
import com.forum.entity.Reply;
import com.forum.entity.User;
import com.forum.mapper.CommentMapper;
import com.forum.mapper.PostMapper;
import com.forum.mapper.ReplyMapper;
import com.forum.mapper.UserMapper;
import com.forum.service.*;
import com.forum.util.AssertUtil;
import com.forum.util.PageUtils;
import com.forum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    //TO-DO
    //创建了帖子、有评论和回复之后发送notice

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ViewService viewService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private StarService starService;

    /**
     * 创建帖子
     * @param createPostParams 帖子参数
     * @return 帖子信息
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public PostInfo createPost(CreatePostParams createPostParams) throws MyException {
        User user = userService.getUserById(sessionUtils.getUserId());

        String images = JSON.toJSONString(createPostParams.getImages());
        String topic = JSON.toJSONString(createPostParams.getTopic());

        Post post = Post.builder()
                .userId(sessionUtils.getUserId())
                .title(createPostParams.getTitle())
                .content(createPostParams.getContent())
                .routeId(createPostParams.getRouteId())
                .createAt(new Date())
                .updateAt(new Date())
                .comments(0L)
                .likes(0L)
                .stars(0L)
                .views(0L)
                .images(images)
                .topic(topic)
                .version(1)
                .build();
        postMapper.insert(post);

        return new PostInfo(post, user, false, false);
    }

    /**
     * 获取帖子列表
     * @param page 页码
     * @param pageSize 每页大小
     * @param orderByPopularity 是否按照热度排序
     * @return 帖子列表
     * @throws MyException 通用异常
     */
    @Override
    public List<PostInfo> getPostList(Integer page, Integer pageSize, Boolean orderByPopularity) throws MyException{
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
            PageHelper.startPage(page, pageSize, "update_at desc");
        } else {
            PageHelper.startPage(page, pageSize, "likes+comments+views+stars desc , update_at desc");
        }
        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.isNull("delete_at");

        Page<Post> posts = new Page<>(new PageInfo<>(postMapper.selectList(postQueryWrapper)));
        List<PostInfo> postInfos = new ArrayList<>();
        String userId = sessionUtils.getUserId();

        for (Post post : posts.getItems()) {
            post.setViews(viewService.getView(post.getId()));
            User user = userService.getUserById(post.getUserId());
            Boolean isLike = likeService.check(post.getId(),0);
            Boolean isStar = starService.check(post.getId(), userId);
            postInfos.add(new PostInfo(post, user, isLike, isStar));
        }
        return postInfos;
    }

//    /**
//     * 获取帖子列表
//     * @param page 页码
//     * @param pageSize 每页大小
//     * @param orderByPopularity 是否按照热度排序
//     * @return 帖子列表
//     */
//    @Override
//    public List<PostDtoInfo> posts(Integer page, Integer pageSize, Boolean orderByPopularity) {
//        if (page == null || page < 1) {
//            page = 1;
//        }
//        if (pageSize == null || pageSize < 1) {
//            pageSize = 10;
//        }
//
//        if (!orderByPopularity) {
//            PageHelper.startPage(page, pageSize, "update_at desc");
//        } else {
//            PageHelper.startPage(page, pageSize, "likes+comments+views+stars desc , update_at desc");
//        }
//
//        List<PostDto> posts = new Page<>(new PageInfo<>(postMapper.selectPosts())).getItems();
//        List<PostDtoInfo> postDtoInfos = new ArrayList<>();
//        String userId = sessionUtils.getUserId();
//
//        for (Post post : posts){
//            post.setViews(viewService.getView(post.getId()));
//            PostDtoInfo dto = new PostDtoInfo(post);
//            dto.setIsLike(likeService.check(post.getId(),0));
//            dto.setIsStar(starService.check(post.getId(),userId));
//            postDtoInfos.add(dto);
//        }
//
//        return postDtoInfos;
//    }

    /**
     * 获取帖子详情
     * @param postId 帖子id
     * @return 帖子详情
     * @throws MyException 通用异常
     */
    @Override
    public PostInfo getPostById(Long postId) throws MyException {
        Post post = postMapper.selectById(postId);
        if (post == null || post.getDeleteAt() != null) {
            throw new MyException(EnumExceptionType.POST_NOT_EXIST);
        }

        Long views = viewService.addView(postId);
        post.setViews(views);
        String userId = sessionUtils.getUserId();
        User user = userService.getUserById(post.getUserId());
        Boolean isLike = likeService.check(post.getId(),0);
        Boolean isStar = starService.check(post.getId(), userId);

        if (postMapper.updateById(post) == 0) {
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        return new PostInfo(post, user, isLike, isStar);
    }

//    /**
//     * 获取帖子详情（带用户信息）
//     * @param postId 帖子id
//     * @return 帖子详情
//     */
//    @Override
//    public PostDtoInfo getPost(Long postId) {
//        PostDto post = postMapper.selectPostByIdWithoutDeleteAt(postId);
//        AssertUtil.notNull(post, CommonErrorCode.POST_NOT_EXIST);
//
//        if(post.getDeleteAt() == null){
//            Long views = viewService.addView(postId);
//            post.setViews(views);
//        }
//
//        PostDtoInfo dto = new PostDtoInfo(post);
//        dto.setIsLike(likeService.check(post.getId(),0));
//        dto.setIsStar(starService.check(post.getId(),sessionUtils.getUserId()));
//
//        return dto;
//    }

    /**
     * 获取用户帖子列表
     * @param userId 用户id
     * @param page 页码
     * @param pageSize 每页大小
     * @return 帖子列表
     * @throws MyException 通用异常
     */
    @Override
    public List<PostInfo> getPostByUserId(String userId, Integer page, Integer pageSize)throws MyException {
        User user = userService.getUserById(userId);

        PageHelper.startPage(page, pageSize).setOrderBy("update_at desc");

        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.isNull("delete_at");
        postQueryWrapper.eq("user_id", userId);

        List<Post> posts = new Page<>(new PageInfo<>(postMapper.selectList(postQueryWrapper))).getItems();
        List<PostInfo> postInfos = new ArrayList<>();
        for (Post post : posts) {
            post.setViews(viewService.getView(post.getId()));
            Boolean isLike = likeService.check(post.getId(),0);
            Boolean isStar = starService.check(post.getId(), userId);
            postInfos.add(new PostInfo(post, user, isLike, isStar));
        }
        return postInfos;
    }

    /**
     * 删除帖子
     * @param postId 帖子id
     * @return 是否成功
     */
    @Override
    public Boolean deletePost(Long postId) {
        User user = userService.getUserById(sessionUtils.getUserId());

        Post post = postMapper.selectById(postId);
        if (post == null || post.getDeleteAt() != null) {
            throw new MyException(EnumExceptionType.POST_NOT_EXIST);
        }
        if (user.getRole() >= 2 && !Objects.equals(post.getUserId(), user.getId())) {
            throw new MyException(EnumExceptionType.PERMISSION_DENIED);
        }

        User postOwner = userService.getUserById(post.getUserId());
        if (user.getRole() - postOwner.getRole() >= 1) {
            throw new MyException(EnumExceptionType.PERMISSION_DENIED);
        }

        likeService.unlikeAll(postId, 0);

        Set<Long> commentIds = new HashSet<>();
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.isNull("delete_at");
        commentQueryWrapper.eq("post_id", postId);
        List<Comment> comments = commentMapper.selectList(commentQueryWrapper);
        for (Comment comment : comments) {
            commentIds.add(comment.getId());
            comment.setDeleteAt(new Date());
            if (commentMapper.updateById(comment) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }

            likeService.unlikeAll(comment.getId(), 1);
        }

        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.isNull("delete_at");
        replyQueryWrapper.in("comment_id", commentIds);
        List<Reply> replies = replyMapper.selectList(replyQueryWrapper);
        for (Reply reply : replies){
            reply.setDeleteAt(new Date());
            if (replyMapper.updateById(reply) == 0) {
                throw new MyException(EnumExceptionType.UPDATE_FAILED);
            }

            likeService.unlikeAll(reply.getId(), 2);
        }

        post.setDeleteAt(new Date());
        if (postMapper.updateById(post) == 0){
            throw new MyException(EnumExceptionType.UPDATE_FAILED);
        }

        return true;
    }

//    /**
//     * 搜索帖子
//     * @param postOrCommentOrReply 搜索内容
//     * @param orderByPopularity 是否按热度排序
//     * @param page 页码
//     * @param pageSize 每页大小
//     * @return 帖子列表
//     */
//    @Override
//    public List<PostInfo> searchByPostOrCommentOrReply(String postOrCommentOrReply, Boolean orderByPopularity, Integer page, Integer pageSize) {
//        if (page == null || page < 1) {
//            page = 1;
//        }
//        if (pageSize == null || pageSize < 1) {
//            pageSize = 10;
//        }
//        if (orderByPopularity == null) {
//            orderByPopularity = false;
//        }
//
//        Set<Long> commentIds = new HashSet<>();
//
//        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
//        replyQueryWrapper.isNull("delete_at");
//        replyQueryWrapper.like("content", postOrCommentOrReply);
//        List<Reply> replies = replyMapper.selectList(replyQueryWrapper);
//
//        for (Reply reply : replies) {
//            commentIds.add(reply.getCommentId());
//        }
//
//        QueryWrapper<Comment> commentQueryWrapper1 = new QueryWrapper<>();
//        commentQueryWrapper1.isNull("delete_at");
//        commentQueryWrapper1.like("content", postOrCommentOrReply);
//        List<Comment> comments1 = commentMapper.selectList(commentQueryWrapper1);
//
//        if (!commentIds.isEmpty()) {
//            QueryWrapper<Comment> commentQueryWrapper2 = new QueryWrapper<>();
//            commentQueryWrapper2.isNull("delete_at")
//                    .in("id", commentIds);
//            List<Comment> comments2 = commentMapper.selectList(commentQueryWrapper2);
//
//            comments1.addAll(comments2);
//        }
//
//        Set<Long> postIds = new HashSet<>();
//        for(var comment : comments1){
//            postIds.add(comment.getPostId());
//        }
//
//        QueryWrapper<Post> postQueryWrapper1 = new QueryWrapper<>();
//        postQueryWrapper1.isNull("delete_at")
//                .and(i -> i.like("title", postOrCommentOrReply)
//                        .or()
//                        .like("content", postOrCommentOrReply));
//        List<Post> posts = postMapper.selectList(postQueryWrapper1);
//
//        if(!postIds.isEmpty()){
//            QueryWrapper<Post> postQueryWrapper2 = new QueryWrapper<>();
//            postQueryWrapper2.isNull("delete_at")
//                    .in("id", postIds);
//            List<Post> posts2 = postMapper.selectList(postQueryWrapper2);
//
//            posts.addAll(posts2);
//        }
//
//        if(orderByPopularity){
//            posts.sort((Post o1,Post o2) -> (int) (o2.getLikes() + o2.getComments() + o2.getViews() + o2.getStars() - o1.getLikes() - o1.getComments() - o1.getViews() - o1.getStars()));
//        }else{
//            posts.sort((Post o1,Post o2) -> o2.getUpdateAt().compareTo(o1.getUpdateAt()));
//        }
//
//        List<Post> postList;
//        if(!posts.isEmpty()){
//            postList = PageUtils.splitList(page,pageSize,posts);
//        }else{
//            postList = new ArrayList<>();
//        }
//
//        List<PostInfo> postInfos = new ArrayList<>();
//        for(var post : postList){
//            postInfos.add(new PostInfo(post));
//        }
//        return postInfos;
//    }
//
//    /**
//     * 搜索帖子（带用户信息）
//     * @param postOrCommentOrReply 搜索内容
//     * @param orderByPopularity 是否按热度排序
//     * @param page 页码
//     * @param pageSize 每页大小
//     * @return 帖子列表
//     */
//    @Override
//    public List<PostDtoInfo> search(String postOrCommentOrReply, Boolean orderByPopularity, Integer page, Integer pageSize) {
//        if(page == null || page < 1){
//            page = 1;
//        }
//        if(pageSize == null || pageSize < 1){
//            pageSize = 10;
//        }
//        if(orderByPopularity == null){
//            orderByPopularity = false;
//        }
//        if(postOrCommentOrReply == null){
//            postOrCommentOrReply = "";
//        }
//
//        Set<Long> commentIds = new HashSet<>();
//
//        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
//        replyQueryWrapper.isNull("delete_at");
//        replyQueryWrapper.like("content", postOrCommentOrReply);
//        List<Reply> replies = replyMapper.selectList(replyQueryWrapper);
//
//        for(var reply : replies){
//            commentIds.add(reply.getCommentId());
//        }
//
//        QueryWrapper<Comment> commentQueryWrapper1 = new QueryWrapper<>();
//        commentQueryWrapper1.isNull("delete_at")
//                .notIn("id", commentIds)
//                .like("content", postOrCommentOrReply);
//        List<Comment> comments = commentMapper.selectList(commentQueryWrapper1);
//
//        if(!commentIds.isEmpty()){
//            QueryWrapper<Comment> commentQueryWrapper2 = new QueryWrapper<>();
//            commentQueryWrapper2.isNull("delete_at")
//                    .in("id", commentIds);
//            List<Comment> comments2 = commentMapper.selectList(commentQueryWrapper2);
//
//            comments.addAll(comments2);
//        }
//
//        Set<Long> postIds = new HashSet<>();
//        for(var comment : comments){
//            postIds.add(comment.getPostId());
//        }
//
//        QueryWrapper<Post> postQueryWrapper1 = new QueryWrapper<>();
//        final String content = postOrCommentOrReply;
//        postQueryWrapper1.isNull("delete_at").notIn("id", postIds)
//                .and(i -> i.like("title", content)
//                        .or()
//                        .like("content", content));
//        List<Post> posts = postMapper.selectList(postQueryWrapper1);
//
//        if(!postIds.isEmpty()){
//            QueryWrapper<Post> postQueryWrapper2 = new QueryWrapper<>();
//            postQueryWrapper2.isNull("delete_at")
//                    .in("id", postIds);
//            List<Post> posts2 = postMapper.selectList(postQueryWrapper2);
//
//            posts.addAll(posts2);
//        }
//
//        if(orderByPopularity){
//            posts.sort((Post o1,Post o2) -> (int) (o2.getLikes() + o2.getComments() + o2.getViews() + o2.getStars() - o1.getLikes() - o1.getComments() - o1.getViews() - o1.getStars()));
//        }else{
//            posts.sort((Post o1,Post o2) -> o2.getUpdateAt().compareTo(o1.getUpdateAt()));
//        }
//
//        List<Post> postList;
//        if(!posts.isEmpty()){
//            postList = PageUtils.splitList(page,pageSize,posts);
//        }else{
//            postList = new ArrayList<>();
//        }
//
//        List<PostDtoInfo> postWithUser = new ArrayList<>();
//        String userId = sessionUtils.getUserId();
//        for(var post : postList){
//            PostDtoInfo postDtoInfo = new PostDtoInfo(post);
//            UserDto userDto = userMapper.selectNameAndAvatar(post.getUserId());
//            postDtoInfo.setUserName(userDto.getName());
//            postDtoInfo.setUserAvatar(userDto.getAvatar());
//            postDtoInfo.setViews(viewService.getView(post.getId()));
//            postDtoInfo.setIsLike(likeService.check(post.getId(),0));
//            postDtoInfo.setIsStar(starService.check(post.getId(),userId));
//            postWithUser.add(postDtoInfo);
//        }
//
//        return postWithUser;
//    }

}








