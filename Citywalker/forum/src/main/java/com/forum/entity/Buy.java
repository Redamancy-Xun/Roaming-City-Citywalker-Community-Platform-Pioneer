package com.forum.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Buy")
@ApiModel("Buy 用户购买的路线订单")
public class Buy implements Serializable {

    @Id
    @TableId(value = "id", type = IdType.AUTO)//指定实体类中对应数据库表的主键字段
    @Column(name = "id")
    @NotNull(message = "路线订单id不能为空")
    @ApiModelProperty("路线订单id")
    private Long id;

    @Column(name = "userId", nullable = true)
    @ApiModelProperty("用户Id")
    private String userId;

    @Column(name = "buyRoutes", nullable = true)
    @ApiModelProperty("用户买的路线")
    private String buyRoutes;

    @Column(name = "deleteAt", nullable = true)
    @ApiModelProperty("删除时间")
    private Date deleteAt;
}