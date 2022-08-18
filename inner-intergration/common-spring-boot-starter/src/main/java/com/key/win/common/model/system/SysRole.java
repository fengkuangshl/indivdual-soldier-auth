package com.key.win.common.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.key.win.common.model.basic.MybatisID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@ApiModel("角色实体")
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends MybatisID {
    @ApiModelProperty("code")
    private String code;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("用户Id")
    @TableField(exist = false)
    private Long userId;
    @ApiModelProperty("菜单列表")
    @TableField(exist = false)
    private List<SysMenu> sysMenus;
    @ApiModelProperty("权限列表")
    @TableField(exist = false)
    private List<SysPermission> sysPermissions;
    @ApiModelProperty("用户Id列表")
    @TableField(exist = false)
    private Set<Long> userIds;
}
