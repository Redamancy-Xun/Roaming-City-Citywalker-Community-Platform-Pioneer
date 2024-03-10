package com.forum.controller.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.forum.entity.Post;
import com.forum.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("PostInfo")
public class PostInfo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createAt;

    @ApiModelProperty("更新时间")
    private Date updateAt;

    @ApiModelProperty("删除时间")
    private Date deleteAt;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户昵称")
    private String userName;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty("图片")
    private List<String> images;

    @ApiModelProperty("路线id")
    private Long routeId;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("话题")
    private List<String> topic;

    @ApiModelProperty("浏览数")
    private Long views;

    @ApiModelProperty("点赞数")
    private Long likes;

    @ApiModelProperty("收藏数")
    private Long stars;

    @ApiModelProperty("评论数")
    private Long comments;

    @ApiModelProperty("是否点赞")
    private Boolean isLike;

    @ApiModelProperty("是否收藏")
    private Boolean isStar;

    public PostInfo(Post post, User user, Boolean isLike, Boolean isStar){
        this.id = post.getId();
        this.createAt = post.getCreateAt();
        this.updateAt = post.getUpdateAt();
        this.deleteAt = post.getDeleteAt();
        this.content = post.getContent();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.views = post.getViews();
        this.likes = post.getLikes();
        this.stars = post.getStars();
        this.comments = post.getComments();
        this.routeId = post.getRouteId();

        this.userName = user.getUsername();
        this.userAvatar = user.getHeadportrait();

        this.isLike = isLike;
        this.isStar = isStar;

        String topic = post.getTopic();
        String images = post.getImages();
        this.images = JSON.parseObject(images, new TypeReference<List<String>>(){});
        this.topic = JSON.parseObject(topic, new TypeReference<List<String>>(){});
    }
}







