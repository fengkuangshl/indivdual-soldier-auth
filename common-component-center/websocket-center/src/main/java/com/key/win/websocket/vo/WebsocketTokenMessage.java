package com.key.win.websocket.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Websocket文本Token VO")
@Data
public class WebsocketTokenMessage extends WebsocketBaseMessage{

    //接收人
    @ApiModelProperty("toToken")
    private String toToken;
}