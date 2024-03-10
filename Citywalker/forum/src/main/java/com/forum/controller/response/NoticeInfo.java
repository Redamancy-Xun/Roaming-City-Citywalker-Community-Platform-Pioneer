package com.forum.controller.response;

import com.forum.entity.Notice;
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
@ApiModel("NoticeDto 通知")
public class NoticeInfo {

    @ApiModelProperty("通知id")
    private Long id;

    @ApiModelProperty("发送者id")
    private String senderId;

    @ApiModelProperty("接收者id")
    private String receiverId;

    @ApiModelProperty("objectId")
    private Long objectId;

    @ApiModelProperty("操作对象object")
    private Object object;

    @ApiModelProperty("通知内容")
    private String content;

    @ApiModelProperty("通知类型(0为系统通知，1为点赞通知，2为评论通知，3为回复通知)")
    private Integer type;

    @ApiModelProperty("发送时间")
    private Date sendAt;

    @ApiModelProperty("删除时间")
    private Date deleteAt;

    @ApiModelProperty("发送者用户昵称")
    private String userName;

    @ApiModelProperty("发送者用户头像")
    private String userAvatar;

    public NoticeInfo(Notice notice, User user, Object object){
        this.content = notice.getContent();
        this.deleteAt = notice.getDeleteAt();
        this.id = notice.getId();
        this.objectId = notice.getObjectId();
        this.receiverId = notice.getReceiverId();
        this.sendAt = notice.getSendAt();
        this.senderId = notice.getSenderId();
        this.type = notice.getType();

        this.userAvatar = user.getHeadportrait();
        this.userName = user.getUsername();

        this.object = object;
    }

    public NoticeInfo(Notice notice, User user){
        this.content = notice.getContent();
        this.deleteAt = notice.getDeleteAt();
        this.id = notice.getId();
        this.objectId = notice.getObjectId();
        this.receiverId = notice.getReceiverId();
        this.sendAt = notice.getSendAt();
        this.senderId = notice.getSenderId();
        this.type = notice.getType();

        this.userAvatar = user.getHeadportrait();
        this.userName = user.getUsername();
    }

    public NoticeInfo(Notice notice) {
        this.content = notice.getContent();
        this.deleteAt = notice.getDeleteAt();
        this.id = notice.getId();
        this.objectId = notice.getObjectId();
        this.receiverId = notice.getReceiverId();
        this.sendAt = notice.getSendAt();
        this.senderId = notice.getSenderId();
        this.type = notice.getType();
    }
}
