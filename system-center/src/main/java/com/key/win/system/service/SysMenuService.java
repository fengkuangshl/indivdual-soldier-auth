package com.key.win.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.*;
import com.key.win.common.model.system.*;

import java.util.List;
import java.util.Set;

public interface SysMenuService extends IService<SysMenu> {

    PageResult<SysMenu> findSysMenuByPaged(PageRequest<SysMenu> t);

    List<SysMenu> findSysMenu(SysMenu sysMenu);

    boolean saveOrUpdateMenu(SysMenu sysMenu);

    List<SysMenu> getMenuTree();

    boolean deleteById(Long id);

    List<SysMenu> findOnes();

    List<SysMenu> findSysMenuByMenuIds(Set<Long> menuIds);

}
