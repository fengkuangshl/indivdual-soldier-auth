package com.key.win.common.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("角色权限实体")
@Data
@TableName("sys_role_permission")
@EqualsAndHashCode(callSuper=true)
public class SysRolePermission  extends MybatisID {
    @ApiModelProperty("角色Id")
	private Long roleId;
    @ApiModelProperty("权限Id")
    private Long permissionId;

}
