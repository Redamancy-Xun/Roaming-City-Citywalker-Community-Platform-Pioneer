package com.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "route")
@ApiModel("Route 路线")
public class Route {

    @Id
    @TableId(value = "route_id", type = IdType.AUTO)//指定实体类中对应数据库表的主键字段
    @Column(name = "route_id")
    @NotNull(message = "路线id不能为空")
    @ApiModelProperty("路线id")
    private Long routeId;

    @Column(name = "theme")
    @NotNull(message = "路线主题不能为空")
    @ApiModelProperty("路线主题")
    private String theme;

    @Column(name = "subtitle")
    @NotNull(message = "路线副标题不能为空")
    @ApiModelProperty("路线副标题")
    private String subtitle;

    @Column(name = "photo")
    @NotNull(message = "路线图片不能为空")
    @ApiModelProperty("路线图片")
    private String photo;

    @Column(name = "summary")
    @NotNull(message = "路线概括不能为空")
    @ApiModelProperty("路线概括")
    private String summary;

    @Column(name = "highlight")
    @NotNull(message = "行程亮点不能为空")
    @ApiModelProperty("行程亮点")
    private String highlight;

    @Column(name = "price")
    @NotNull(message = "路线价格不能为空")
    @ApiModelProperty("路线价格")
    private String price;

    @Column(name = "time")
    @NotNull(message = "路线时长不能为空")
    @ApiModelProperty("路线时长")
    private String time;

    @Column(name = "start")
    @NotNull(message = "路线起点不能为空")
    @ApiModelProperty("路线起点")
    private String start;

    @Column(name = "people")
    @NotNull(message = "路线人数不能为空")
    @ApiModelProperty("路线人数")
    private String people;

    @Column(name = "schedule")
    @NotNull(message = "路线时间安排不能为空")
    @ApiModelProperty("路线时间安排")
    private String schedule;

    @Column(name = "costInclude")
    @NotNull(message = "路线费用包括不能为空")
    @ApiModelProperty("路线费用包括")
    private String costInclude;

    @Column(name = "costExclude")
    @NotNull(message = "路线费用不包括不能为空")
    @ApiModelProperty("路线费用不包括")
    private String costExclude;

    @Column(name = "refund")
    @NotNull(message = "路线退款退订不能为空")
    @ApiModelProperty("路线退款退订")
    private String refund;

    @Column(name = "map")
    @NotNull(message = "路线地图不能为空")
    @ApiModelProperty("路线地图")
    private String map;

    @Column(name = "other")
    @NotNull(message = "关于citywalker不能为空")
    @ApiModelProperty("关于citywalker")
    private String other;

    @Column(name = "remind")
    @NotNull(message = "行前提示不能为空")
    @ApiModelProperty("行前提示")
    private String remind;
}
