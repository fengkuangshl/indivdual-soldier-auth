package com.key.win.common.auth.detail;

import com.key.win.common.model.system.SysMenu;
import com.key.win.common.model.system.SysPermission;
import com.key.win.common.model.system.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Authentication extends SysUser {

    @ApiModelProperty("权限")
    private List<SysPermission> permissions;
    @ApiModelProperty("菜单")
    private List<SysMenu> menus;
    @ApiModelProperty("登录时间")
    private Date loginTime = new Date();
    @ApiModelProperty("在线类型：[windows,mac,android,ipad,ios,other]")
    private String loginType;


}
