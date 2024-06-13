package com.forum.controller.response;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ShowShoppingCartResponse 用户购物车信息返回")
public class ShowShoppingCartReponse {

    @ApiModelProperty("购物车id")
    private Long CartId;

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("路线选择人数")
    private Integer routePeople;

    @ApiModelProperty("路线选择时间段")
    private String routeTime;

    @ApiModelProperty("路线花销")
    private Integer routeCost;

    @ApiModelProperty("购物车中的路线")
    private Route route;
}
