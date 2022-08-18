package com.key.win.basic.web;

/**
 * 统一的返回状态码
 */
public enum CodeEnum {
    SUCCESS(200),
    ERROR(-200);

    private Integer code;

    CodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
