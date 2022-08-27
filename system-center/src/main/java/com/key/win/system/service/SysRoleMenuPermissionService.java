package com.key.win.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.common.model.system.SysRoleMenuPermission;
import com.key.win.common.model.system.SysRoleMenuPermission;

import java.util.List;

public interface SysRoleMenuPermissionService extends IService<SysRoleMenuPermission> {

    PageResult<SysRoleMenuPermission> findSysRoleMenuPermissionByPaged(PageRequest<SysRoleMenuPermission> t);

    List<SysRoleMenuPermission> findSysRoleMenuPermission(SysRoleMenuPermission sysRoleMenuPermission);

    boolean saveOrUpdateSysRoleMenuPermission(SysRoleMenuPermission sysRoleMenuPermission);

    boolean saveOrUpdateSysRoleMenuPermissionForBatch(List<SysRoleMenuPermission> sysRoleMenuPermissions);

    List<SysRoleMenuPermission> findGrantMenus(Long roleId);

    List<SysRoleMenuPermission> findGrantMenuPermissions(Long roleId);

    List<SysRoleMenuPermission> findGrantSysRoleMenuPermissionByRoleId(Long roleId);
}
