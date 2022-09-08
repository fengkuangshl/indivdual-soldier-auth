package com.key.win.auth.device.vo;

import com.key.win.auth.device.model.DeviceAuth;
import com.key.win.rsa.web.IEncryptor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("设备端提交设备信息Vo")
public class DeviceAuthRequestVo extends DeviceAuth implements IEncryptor {

}
