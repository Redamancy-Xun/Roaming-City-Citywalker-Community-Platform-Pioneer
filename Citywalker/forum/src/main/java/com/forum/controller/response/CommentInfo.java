package com.forum.controller.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.forum.common.CommonConstants;
import com.forum.entity.Comment;
import com.forum.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.ImagingOpException;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("CommentInfo")
public class CommentInfo {

    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("评论楼层数")
    private Long floor;

    @ApiModelProperty("postId")
    private Long postId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户昵称")
    private String userName;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty("评论时间")
    private Date createAt;

    @ApiModelProperty("删除时间")
    private Date deleteAt;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论图片")
    private List<String> images;

    @ApiModelProperty("评论点赞数")
    private Long likes;

    @ApiModelProperty("评论回复数")
    private Long replies;

    @ApiModelProperty("是否点赞")
    private Boolean isLike;

    public CommentInfo(Comment comment, User user, Boolean isLike){
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.userId = comment.getUserId();
        this.createAt = comment.getCreateAt();
        this.deleteAt = comment.getDeleteAt();
        this.content = comment.getContent();
        this.likes = comment.getLikes();
        this.replies = comment.getReplies();
        this.floor = comment.getFloor();

        this.userName = user.getUsername();
        this.userAvatar = CommonConstants.IMAGE_PATH + user.getHeadportrait();

        this.isLike = isLike;

        String images = comment.getImages();
        this.images = JSON.parseObject(images, new TypeReference<List<String>>(){});

        //如果image里面有对象 给images中的每一个对象加上IMAGE_PATH前缀
        if (this.images != null && this.images.size() > 0){
            for (int i = 0; i < this.images.size(); i++) {
                this.images.set(i, CommonConstants.IMAGE_PATH + this.images.get(i));
            }
        }
    }
}



