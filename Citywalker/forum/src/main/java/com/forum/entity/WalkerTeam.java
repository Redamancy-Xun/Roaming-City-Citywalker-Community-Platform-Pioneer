package com.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "walkerteam")
@ApiModel("WalkerTeam 组队")
public class WalkerTeam {

    @Id
    @TableId(value = "team_id", type = IdType.AUTO)//指定实体类中对应数据库表的主键字段
    @Column(name = "team_id")
    @NotNull(message = "组队id不能为空")
    @ApiModelProperty("组队id")
    private Long teamId;

    @Column(name = "user_id")
    @NotNull(message = "发起人id不能为空")
    @ApiModelProperty("发起人id")
    private String userId;

    @Column(name = "topic")
    @ApiModelProperty("组队话题")
    private String topic;

    @Column(name = "title")
    @NotNull(message = "组队标题不能为空")
    @ApiModelProperty("组队标题")
    private String title;

    @Column(name = "time")
    @ApiModelProperty("时间安排")
    private String time;

    @Column(name = "anticipate")
    @ApiModelProperty("个人预期")
    private String anticipate;

    @Column(name = "introduction")
    @ApiModelProperty("自我介绍")
    private String introduction;

    @Column(name = "photo")
    @ApiModelProperty("图片分享")
    private String photo;

    @Column(name = "routeId")
    @ApiModelProperty("所选路线id")
    private Long routeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createAt;
}
