package com.key.win.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.key.win.basic.enumjson.*;
import org.apache.commons.lang3.StringUtils;

@JsonSerialize(using = TextureEnumSerializerCode.class)
@JsonDeserialize(using = TextureEnumDeserializer.class)
public enum SexEnum implements JsonEnum {


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

    @Override
    public Object selectEnumByName(String value) {
        if(StringUtils.isNotBlank(value)){
            for (SexEnum sexEnum : SexEnum.values()) {
                if(sexEnum.name().equals(value)){
                    return sexEnum;
                }
            }
        }
        return null;
    }
}
