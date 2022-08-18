package com.key.win.mongo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class MongoCommonField extends MongoVersion {
    /**
     *
     */
    private static final long serialVersionUID = 3803687288306105240L;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("更新时间")
    private Date updateDate;

    @ApiModelProperty("创建人Id")
    private String createUserId;

    @ApiModelProperty("更新人Id")
    private String updateUserId;

    @ApiModelProperty("逻辑删除：True-正常,False-删除")
    private Boolean enableFlag = Boolean.TRUE;

    @ApiModelProperty("创建用户名称")
    private String createUserName;

    @ApiModelProperty("更新用户名称")
    private String updateUserName;


}