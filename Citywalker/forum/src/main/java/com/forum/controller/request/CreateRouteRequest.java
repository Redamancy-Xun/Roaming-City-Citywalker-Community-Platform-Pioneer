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
@ApiModel("CreateRouteRequest")
public class CreateRouteRequest {

    @ApiModelProperty("路线主题")
    private String theme;

    @ApiModelProperty("路线副标题")
    private String subtitle;

    @ApiModelProperty("路线图片")
    private List<String> photo;

    @ApiModelProperty("路线概括")
    private String summary;

    @ApiModelProperty("行程亮点")
    private String highlight;

    @ApiModelProperty("路线价格")
    private String price;

    @ApiModelProperty("路线时长")
    private String time;

    @ApiModelProperty("路线起点")
    private String start;

    @ApiModelProperty("路线人数")
    private String people;

    @ApiModelProperty("路线时间安排")
    private String schedule;

    @ApiModelProperty("路线费用包括")
    private String costInclude;

    @ApiModelProperty("路线费用不包括")
    private String costExclude;

    @ApiModelProperty("路线退款退订")
    private String refund;

    @ApiModelProperty("路线地图")
    private String map;

    @ApiModelProperty("关于citywalker")
    private String other;

    @ApiModelProperty("行前提示")
    private String remind;
}
