package com.key.win.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.key.win.basic.enumjson.TextureEnumSerializerCode;

@JsonSerialize(using = TextureEnumSerializerCode.class)
public enum SexEnum {


    MALE(0, "男"),
    FMALE(1, "女");
    @EnumValue
    private int code;
    private String text;

    private SexEnum(int code, String text) {
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
