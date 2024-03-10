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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "shoppingCart")
@ApiModel("ShoppingCart 用户购物车")
public class ShoppingCart {

    @Id
    @TableId(value = "cart_id", type = IdType.AUTO)//指定实体类中对应数据库表的主键字段
    @Column(name = "cart_id")
    @NotNull(message = "购物车id不能为空")
    @ApiModelProperty("购物车id")
    private Long CartId;

    @Column(name = "user_id")
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private String userId;

    @Column(name = "routes", nullable = true)
    @ApiModelProperty("购物车中的路线")
    private String routes;

    @Column(name = "routePeople", nullable = true)
    @ApiModelProperty("路线选择人数")
    private String routePeople;

    @Column(name = "routeTime", nullable = true)
    @ApiModelProperty("路线选择时间段")
    private String routeTime;
}
