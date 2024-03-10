package com.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("Notice 通知")
public class Notice {

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("通知id")
    private Long id;

    @ApiModelProperty("发送者id")
    private String senderId;

    @ApiModelProperty("接收者id")
    private String receiverId;

    @ApiModelProperty("objectId")
    private Long objectId;

    @ApiModelProperty("通知内容")
    private String content;

    @ApiModelProperty("通知类型(0为系统通知，1为点赞通知，2为评论通知，3为回复通知)")
    private Integer type;

    @ApiModelProperty("发送时间")
    private Date sendAt;

    @ApiModelProperty("删除时间")
    private Date deleteAt;
}









