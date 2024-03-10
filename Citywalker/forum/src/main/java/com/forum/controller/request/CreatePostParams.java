package com.forum.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("CreatePostParams")
public class CreatePostParams {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("图片")
    private List<String> images;

    @ApiModelProperty("话题")
    private List<String> topic;

    @ApiModelProperty("路线id")
    private Long routeId;
}
