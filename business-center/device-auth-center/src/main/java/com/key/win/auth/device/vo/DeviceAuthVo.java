package com.key.win.auth.device.vo;

import com.key.win.auth.device.model.DeviceAuth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("客户实体Vo")
public class DeviceAuthVo extends DeviceAuth {
    // 授权码
    @ApiModelProperty("客户编号")
    private String sequence;

    //项目号
    @ApiModelProperty("项目号")
    private String projectNo;

    // 是否校验日期
    @ApiModelProperty("是否校验日期：true-启用，false-禁用")
    private Boolean isVerify = Boolean.TRUE;

    @ApiModelProperty("查询条件的开始时间")
    private String startDate;
    @ApiModelProperty("查询条件的结束时间")
    private String endDate;

    @ApiModelProperty("查询条件的开始数量")
    private Integer startNum;
    @ApiModelProperty("查询条件的结束数量")
    private Integer endNum;

    //	授权设备数
    @ApiModelProperty("授权设备数")
    private Long authDeviceNum;

    // 负责人姓名
    @ApiModelProperty("负责人姓名")
    private String leadName;
    // 负责人手机
    @ApiModelProperty("负责人手机")
    private String leadMobile;
}
