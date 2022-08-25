package com.key.win.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.common.model.system.SysMenuPermission;
import com.key.win.common.model.system.SysPermission;

import java.util.List;
import java.util.Set;

public interface SysMenuPermissionService extends IService<SysMenuPermission> {

    PageResult<SysMenuPermission> findSysMenuPermissionByPaged(PageRequest<SysMenuPermission> t);

    List<SysMenuPermission> findSysMenuPermission(SysMenuPermission sysMenuPermission);

    List<SysMenuPermission> findSysMenuPermissionByChecked(boolean checked);

    boolean saveOrUpdateSysMenuPermission(SysMenuPermission sysMenuPermission);

    boolean saveOrUpdateSysMenuPermissionForBatch(List<SysMenuPermission> sysMenuPermissions);

    void setSysMenuPermissionToRole(Long roleId, Set<Long> menuIds);

    List<SysMenuPermission> findSysMenuPermissionByRoleId(Long roleId);
}
