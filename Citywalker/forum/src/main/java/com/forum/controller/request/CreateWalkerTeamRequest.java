package com.forum.controller.request;

import com.forum.controller.response.ShowUserResponse;
import com.forum.entity.Route;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("CreateWalkerTeamRequest 发起组队")
public class CreateWalkerTeamRequest {

    @ApiModelProperty("组队话题")
    private List<String> topic;

    @ApiModelProperty("组队标题")
    private String title;

    @ApiModelProperty("时间安排")
    private String time;

    @ApiModelProperty("个人预期")
    private String anticipate;

    @ApiModelProperty("自我介绍")
    private String introduction;

    @ApiModelProperty("图片分享")
    private List<String> photo;

    @ApiModelProperty("所选路线id")
    private Long routeId;
}
