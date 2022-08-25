package com.key.win.auth.device.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("DEVICE_AUTH")
@EqualsAndHashCode(callSuper = true)
@ApiModel("设备认证")
public class DeviceAuth extends MybatisID {
    // andriod APILevel
    @ApiModelProperty("andriod APILevel")
    private String aPILevel;
    //屏幕对角线的像素值/对角线的尺寸
    @ApiModelProperty("屏幕对角线的像素值/对角线的尺寸")
    private String densityDpi;
    //高
    @ApiModelProperty("高")
    private String heightPixels;
    //宽
    @ApiModelProperty("宽")
    private String widthPixels;
    //androidId
    @ApiModelProperty("androidId")
    private String androidId;
    //主板名称
    @ApiModelProperty("主板名称")
    private String board;
    //厂商名称
    @ApiModelProperty("厂商名称")
    private String brand;
    //时间
    @ApiModelProperty("时间")
    private String buildTime;
    //硬件识别码
    @ApiModelProperty("硬件识别码")
    private String fingerPrint;
    //硬件名称
    @ApiModelProperty("硬件名称")
    private String hardware;
    //Mac地址
    @ApiModelProperty("Mac地址")
    private String macAddress;
    //无线电固件版本号
    @ApiModelProperty("无线电固件版本号")
    private String radio;
    //serialNumber
    @ApiModelProperty("serialNumber")
    private String serialNumber;
    //软件版本
    @ApiModelProperty("软件版本")
    private String softwareVersion;
    //授权码
    @ApiModelProperty("授权code")
    private String authCode;
    //code
    @ApiModelProperty("验证码")
    private String code;

    //唯一码
    @ApiModelProperty("唯一码")
    private String md5Code;


    @ApiModelProperty("设备的状态：[true:在线 false:离线]")
    @TableField(exist = false)
    private boolean isOnLine = false;
}
