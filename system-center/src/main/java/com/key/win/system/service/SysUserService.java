package com.key.win.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.*;
import com.key.win.common.model.system.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysUserService extends IService<SysUser> {

    PageResult<SysUser> findSysUserByPaged(PageRequest<SysUser> t);

    Map<String, Object> login(SysUser sysUser, String userAgent);

    List<SysUser> findSysUserByUserName(String userName);

    boolean updateSysUser(SysUser sysUser);

    boolean saveSysUser(SysUser sysUser);

    boolean modifyPassword(SysUser sysUser);

    boolean resetPassword(Long id);

    void setUserToGroup(Long groupId, Set<Long> userIds);

    SysUser getUserFullById(Long id);

    boolean deleteById(Long id);

    Map<String, Object> refreshToken(String token, String userAgent);

    boolean updateEnabled(SysUser sysUser);
}
