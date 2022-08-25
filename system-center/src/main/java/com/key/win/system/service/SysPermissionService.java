package com.key.win.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.*;
import com.key.win.common.model.system.*;

import java.util.List;
import java.util.Set;

public interface SysPermissionService extends IService<SysPermission> {

    PageResult<SysPermission> findSysPermissionByPaged(PageRequest<SysPermission> t);

    List<SysPermission> findSysPermission(SysPermission sysPermission);

    boolean saveOrUpdatePermission(SysPermission sysPermission);
}
