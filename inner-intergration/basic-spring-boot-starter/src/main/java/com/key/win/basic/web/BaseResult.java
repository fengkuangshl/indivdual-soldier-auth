package com.key.win.basic.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult implements Serializable {
    @ApiModelProperty("code")
    protected Integer code;
    @ApiModelProperty("提示信息")
    protected String msg;
}
