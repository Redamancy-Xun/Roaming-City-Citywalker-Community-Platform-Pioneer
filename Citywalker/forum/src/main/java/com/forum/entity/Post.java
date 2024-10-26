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
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("Post 帖子")
public class Post implements Serializable {

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("更新时间")
    private Date updateAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("删除时间")
    private Date deleteAt;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("路线id")
    private Long routeId;

    @ApiModelProperty("话题")
    private String topic;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("图片")
    private String images;

    @ApiModelProperty("浏览数")
    private Long views;

    @ApiModelProperty("点赞数")
    private Long likes;

    @ApiModelProperty("收藏数")
    private Long stars;

    @ApiModelProperty("评论数")
    private Long comments;

    @Version
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("乐观锁")
    private Integer version;
}
