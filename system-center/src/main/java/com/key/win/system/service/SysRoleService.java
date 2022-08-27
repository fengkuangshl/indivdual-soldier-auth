package com.key.win.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.*;
import com.key.win.common.model.system.*;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    PageResult<SysRole> findSysRoleByPaged(PageRequest<SysRole> t);

    List<SysRole> findSysRole(SysRole sysRole);
    
    boolean saveOrUpdateRole(SysRole sysRole);

    boolean deleteById(Long id);
}
