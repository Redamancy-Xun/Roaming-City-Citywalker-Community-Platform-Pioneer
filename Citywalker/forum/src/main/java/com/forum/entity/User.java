package com.forum.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Entity(name = "user")
@ApiModel("User 用户")
public class User implements Serializable {

    @Id
    @TableId(value = "id", type = IdType.INPUT)//指定实体类中对应数据库表的主键字段
    @Column(name = "id")
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private String id;

    @Column(name = "role")
    @NotNull(message = "身份不能为空")
    @ApiModelProperty("0是超级管理员，1是管理员，2是已认证用户，3是同意协议用户，4是未同意协议用户")
    private Integer role;

    @Column(name = "username", nullable = false)
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度为6-20")
    @ApiModelProperty("用户名")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度为6-20")
    @ApiModelProperty("密码")
    private String password;

    @Column(name = "signature", nullable = true)
    @ApiModelProperty("个性签名")
    private String signature;

    @Column(name = "routes", nullable = true)
    @ApiModelProperty("加入购物车中的路线")
    private String cartRoutes;

    @Column(name = "birthday", nullable = true)
    @ApiModelProperty("生日")
    private Date birthday;

    @Column(name = "email", nullable = true)
    @Email(message = "邮箱格式错误")
    @ApiModelProperty("邮箱")
    private String email;

    @Column(name = "headportrait", nullable = true)
    @ApiModelProperty("头像")
    private String headportrait;

    @Column(name = "gender")
    @ApiModelProperty("性别 0为女性 1为男性 2为私密")
    private Integer gender;

    @Column(name = "age")
    @ApiModelProperty("年龄")
    private Integer age;

    @Column(name = "label")
    @ApiModelProperty("个人标签")
    private String label;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private int version;
}
