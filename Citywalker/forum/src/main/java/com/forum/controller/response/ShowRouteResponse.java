package com.forum.controller.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.forum.entity.Route;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ShowRouteResponse 路线信息返回")
public class ShowRouteResponse {

    @ApiModelProperty("路线id")
    private Long routeId;

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

    public ShowRouteResponse(Route route) {
        this.routeId = route.getRouteId();
        this.theme = route.getTheme();
        this.subtitle = route.getSubtitle();
        this.photo = JSON.parseObject(route.getPhoto(), new TypeReference<List<String>>(){});
        this.summary = route.getSummary();
        this.highlight = route.getHighlight();
        this.price = route.getPrice();
        this.time = route.getTime();
        this.start = route.getStart();
        this.people = route.getPeople();
        this.schedule = route.getSchedule();
        this.costInclude = route.getCostInclude();
        this.costExclude = route.getCostExclude();
        this.refund = route.getRefund();
        this.map = route.getMap();
        this.other = route.getOther();
        this.remind = route.getRemind();
    }
}
