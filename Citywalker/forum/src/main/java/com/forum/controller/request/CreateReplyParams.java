package com.forum.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("CreateReplyParams")
public class CreateReplyParams {

    @ApiModelProperty("commentId")
    private Long commentId;

    @ApiModelProperty("回复的回复id")
    private Long replyId;

    @ApiModelProperty("内容")
    private String content;
}
