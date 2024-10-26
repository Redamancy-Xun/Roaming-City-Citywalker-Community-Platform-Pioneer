package com.forum.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("comment 评论")
public class Comment {

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("评论楼层数")
    private Long floor;

    @ApiModelProperty("postId")
    private Long postId;

    @ApiModelProperty("用户id")
    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("评论时间")
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("删除时间")
    private Date deleteAt;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论图片")
    private String images;

    @ApiModelProperty("评论点赞数")
    private Long likes;

    @ApiModelProperty("评论回复数")
    private Long replies;

    @Version
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("评论乐观锁组件")
    private Integer version;
}
