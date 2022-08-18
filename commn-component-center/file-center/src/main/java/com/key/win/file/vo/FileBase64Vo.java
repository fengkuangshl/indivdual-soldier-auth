package com.key.win.file.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("图片Baseb64编码Vo")
@Data
public class FileBase64Vo implements Serializable {
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("base64编码")
    private String qrcode;

}
