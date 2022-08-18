package com.key.win.websocket.vo;

import com.key.win.common.auth.AuthenticationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WebsocketBaseMessage implements Serializable {

    // 发送人token
    @ApiModelProperty("发送人token")
    private String token = AuthenticationUtil.getToken();

    //发送人
    @ApiModelProperty("发送人")
    private String fromUserName = AuthenticationUtil.getUserName();

    // 信息
    @ApiModelProperty("发送信息")
    private String message;

    @ApiModelProperty("action")
    private String action;

    @ApiModelProperty("mapper")
    private String mapper;
}