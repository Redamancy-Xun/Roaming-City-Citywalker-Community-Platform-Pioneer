package com.forum.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.forum.common.EnumExceptionType;
import com.forum.entity.Admin;
import com.forum.entity.User;
import com.forum.exception.MyException;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * session缓存实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("SessionData 会话实体")
public class SessionData  implements Serializable {

    /**
     * {@link User}
     */
    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("头像")
    private String headportrait;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("角色 0用户 1管理员")
    private Integer role;

    @ApiModelProperty("性别 0为女性 1为男性 2为私密")
    private Integer gender;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("个人标签")
    private List<String> label;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("邮箱")
    private String email;

    public SessionData(User user) {
        if (user == null)
            throw new MyException(EnumExceptionType.USER_NOT_EXIST);
        this.id = user.getId();
        this.headportrait = user.getHeadportrait();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.label = JSON.parseObject(user.getLabel(), new TypeReference<List<String>>(){});
        this.birthday = user.getBirthday();
        this.email = user.getEmail();
        this.signature = user.getSignature();
    }
}
