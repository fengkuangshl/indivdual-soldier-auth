package com.key.win.websocket.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Websocket文本VO")
@Data
public class WebsocketUserMessage extends WebsocketBaseMessage{

    //接收人
    @ApiModelProperty("接收人")
    private String toUserName;
}