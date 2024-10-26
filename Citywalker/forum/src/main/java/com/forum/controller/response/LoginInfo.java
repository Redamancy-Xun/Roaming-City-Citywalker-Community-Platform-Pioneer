package com.forum.controller.response;

import com.forum.dto.SessionData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("LoginInfo")
public class LoginInfo {

    @ApiModelProperty("userId")
    private String userId;

    @ApiModelProperty("role")
    private Integer role;

    @ApiModelProperty("会话id")
    private String sessionId;

    @ApiModelProperty("会话Data")
    private SessionData sessionData;
}