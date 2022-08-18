package com.key.win.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.key.win.basic.enumjson.TextureEnumSerializerCode;

@JsonSerialize(using = TextureEnumSerializerCode.class)
public enum UserTypeEnum {

    NORMAL(0, "普通"),
    ADMIN(1, "管理员");
    @EnumValue
    private int code;
    private String text;

    private UserTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }


    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
