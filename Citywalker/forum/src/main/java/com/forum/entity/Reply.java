package com.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel("Reply 回复")
public class Reply {

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("commentId")
    private Long commentId;

    @ApiModelProperty("回复的回复id")
    private Long replyId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("被回复的用户id")
    private String replyUserId;

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
}
