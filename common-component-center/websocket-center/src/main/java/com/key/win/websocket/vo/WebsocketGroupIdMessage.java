package com.key.win.websocket.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Websocket群ID发VO")
@Data
public class WebsocketGroupIdMessage extends WebsocketBaseMessage {
    @ApiModelProperty("用户组的Id")
    private String groupId;
}