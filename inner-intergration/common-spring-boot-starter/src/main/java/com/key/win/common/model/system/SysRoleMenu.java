package com.key.win.common.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("角色菜单实体")
@Data
@TableName("sys_role_menu")
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenu extends MybatisID {
    @ApiModelProperty("角色Id")
    private Long roleId;
    @ApiModelProperty("菜单Id")
    private Long menuId;

}
