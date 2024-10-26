package com.forum.controller.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.*;
import com.forum.common.CommonConstants;
import com.forum.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ShowUserResponse 个人信息返回")
public class ShowUserResponse {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("0是超级管理员，1是管理员，2是已认证用户，3是同意协议用户，4是未同意协议用户")
    private Integer role;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String headportrait;

    @ApiModelProperty("性别 0为女性 1为男性 2为私密")
    private Integer gender;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("个人标签")
    private List<String> label;

    @TableField(fill = FieldFill.INSERT)
    private int version;

    public ShowUserResponse(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.username = user.getUsername();
        this.birthday = user.getBirthday();
        this.email = user.getEmail();
        this.headportrait = CommonConstants.IMAGE_PATH + user.getHeadportrait();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.label = JSON.parseObject(user.getLabel(), new TypeReference<List<String>>(){});
        this.version = user.getVersion();
        this.signature = user.getSignature();
    }
}

