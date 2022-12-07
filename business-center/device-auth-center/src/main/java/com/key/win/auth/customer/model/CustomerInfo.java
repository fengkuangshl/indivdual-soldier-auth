package com.key.win.auth.customer.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("device_customer_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel("客户实体")
public class CustomerInfo extends MybatisID {
    // 授权码
    @ApiModelProperty("客户编号")
    private String sequence;
    //  授权码
    @ApiModelProperty("授权码")
    private String authDeviceCode;
    //	客户设备授权到期日
    @ApiModelProperty("客户设备授权到期日")
    private Date expireDeviceDate;
    //	授权设备数
    @ApiModelProperty("授权设备数")
    private Long authDeviceNum;
    // 是否校验日期
    @ApiModelProperty("是否校验日期：true-启用，false-禁用")
    private Boolean isVerify = Boolean.TRUE;
    // 公司名称
    @ApiModelProperty("公司名称")
    private String companyName;
    @ApiModelProperty("公司地址")
    private String companyAddress;
    // 公司电话
    @ApiModelProperty("公司电话")
    private String companyPhone;
    // 负责人姓名
    @ApiModelProperty("负责人姓名")
    private String leadName;
    // 负责人手机
    @ApiModelProperty("负责人手机")
    private String leadMobile;
    // 负责人座机
    @ApiModelProperty("负责人座机")
    private String leadPhone;
    //项目号
    @ApiModelProperty("项目号")
    private String projectNo;
    //项目号
    @ApiModelProperty("项目名称")
    private String projectName;

}
