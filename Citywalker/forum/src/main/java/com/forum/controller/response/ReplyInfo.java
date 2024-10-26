package com.forum.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forum.common.CommonConstants;
import com.forum.entity.Reply;
import com.forum.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("ReplyDto 回复")
public class ReplyInfo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("commentId")
    private Long commentId;

    @ApiModelProperty("回复的回复id")
    private Long replyId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名字")
    private String userName;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty("被回复的用户id")
    private String replyUserId;

    @ApiModelProperty("被回复的用户头像")
    private String replyUserAvatar;

    @ApiModelProperty("被回复的用户名字")
    private String replyUserName;

    @ApiModelProperty("postId")
    private Long postId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("删除时间")
    private Date deleteAt;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("点赞数")
    private Long likes;

    @ApiModelProperty("是否点赞")
    private Boolean isLike;

    public ReplyInfo(Reply reply, User user, User replyUser, Boolean isLike) {
        this.id = reply.getId();
        this.commentId = reply.getCommentId();
        this.replyId = reply.getReplyId();
        this.userId = reply.getUserId();
        this.replyUserId = reply.getReplyUserId();
        this.postId = reply.getPostId();
        this.createAt = reply.getCreateAt();
        this.deleteAt = reply.getDeleteAt();
        this.content = reply.getContent();
        this.likes = reply.getLikes();

        this.userName = user.getUsername();
        this.userAvatar = CommonConstants.IMAGE_PATH + user.getHeadportrait();

        this.replyUserAvatar = CommonConstants.IMAGE_PATH + replyUser.getHeadportrait();
        this.replyUserName = replyUser.getUsername();

        this.isLike = isLike;
    }

    public ReplyInfo(Reply reply, User user, Boolean isLike) {
        this.id = reply.getId();
        this.commentId = reply.getCommentId();
        this.replyId = reply.getReplyId();
        this.userId = reply.getUserId();
        this.replyUserId = reply.getReplyUserId();
        this.postId = reply.getPostId();
        this.createAt = reply.getCreateAt();
        this.deleteAt = reply.getDeleteAt();
        this.content = reply.getContent();
        this.likes = reply.getLikes();

        this.userName = user.getUsername();
        this.userAvatar = CommonConstants.IMAGE_PATH + user.getHeadportrait();

        this.isLike = isLike;
    }
}
