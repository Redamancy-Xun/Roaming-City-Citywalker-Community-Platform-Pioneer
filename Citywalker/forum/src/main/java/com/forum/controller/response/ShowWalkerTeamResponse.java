package com.forum.controller.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.forum.entity.Route;
import com.forum.entity.User;
import com.forum.entity.WalkerTeam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ShowWalkerTeamResponse 组队信息返回")
public class ShowWalkerTeamResponse {

    @ApiModelProperty("组队id")
    private Long teamId;

    @ApiModelProperty("发起人")
    private ShowUserResponse user;

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

    @ApiModelProperty("所选路线")
    private Route route;

    public ShowWalkerTeamResponse(WalkerTeam walkerTeam, ShowUserResponse user, Route route) {
        this.teamId = walkerTeam.getTeamId();
        this.user = user;
        this.topic = JSON.parseObject(walkerTeam.getTopic(), new TypeReference<List<String>>(){});
        this.title = walkerTeam.getTitle();
        this.time = walkerTeam.getTime();
        this.anticipate = walkerTeam.getAnticipate();
        this.introduction = walkerTeam.getIntroduction();
        this.photo = JSON.parseObject(walkerTeam.getPhoto(), new TypeReference<List<String>>(){});
        this.route = route;
    }
}
