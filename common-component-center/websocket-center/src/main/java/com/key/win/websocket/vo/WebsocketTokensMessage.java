package com.key.win.websocket.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("Websocket文本Tokens VO")
@Data
public class WebsocketTokensMessage extends WebsocketBaseMessage{

    //接收人
    @ApiModelProperty("toTokens")
    private List<String> toTokens;
}