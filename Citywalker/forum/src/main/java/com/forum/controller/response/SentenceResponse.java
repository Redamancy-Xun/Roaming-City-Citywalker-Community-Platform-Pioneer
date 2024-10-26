package com.forum.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("SentenceResponse")
public class SentenceResponse {

    @ApiModelProperty("句子")
    private String sentence;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("来源")
    private String source;
}
