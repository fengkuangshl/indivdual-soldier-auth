package com.key.win.common.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("用户角色实体")
@Data
@TableName("sys_user_role")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends MybatisID {
    @ApiModelProperty("用户Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @ApiModelProperty("角色Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

}
