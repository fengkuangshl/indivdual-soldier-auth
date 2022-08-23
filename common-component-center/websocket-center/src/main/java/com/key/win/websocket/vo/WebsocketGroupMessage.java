package com.key.win.websocket.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("Websocket群发VO")
@Data
public class WebsocketGroupMessage extends WebsocketBaseMessage {
    @ApiModelProperty("接收人列表")
    private List<String> toUserNames;
}