package com.key.win.websocket.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.key.win.basic.enumjson.TextureEnumSerializer;

@JsonSerialize(using = TextureEnumSerializer.class)
public enum MessageTypeEnum {

    NORMAL(0, "普通消息"),
    CONFIRM(1, "确认消息");


    private MessageTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    private int code;

    private String text;

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }


}
