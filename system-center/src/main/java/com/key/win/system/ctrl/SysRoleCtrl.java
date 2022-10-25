package com.key.win.system.ctrl;

import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.common.model.system.*;
import com.key.win.security.annotation.PreAuthorize;
import com.key.win.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role/*")
@Api("Role相关的api")
public class SysRoleCtrl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String AUTHORITY_PREFIX = "system::sys-role::SysRole::";

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/findSysRoleByPaged")
    @ApiOperation(value = "Role分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
    public PageResult<SysRole> findSysRoleByPaged(@RequestBody PageRequest<SysRole> t) {
        return sysRoleService.findSysRoleByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取Role")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::ID')")
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysRoleService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "DELETE')")
    public Result delete(@PathVariable Long id) {
        boolean b = sysRoleService.deleteById(id);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAnyAuthority('" + AUTHORITY_PREFIX + "MODIFY','" + AUTHORITY_PREFIX + "ADD')")
    public Result saveOrUpdate(@RequestBody SysRole sysRole) {
        if (StringUtils.isBlank(sysRole.getCode())) {
            logger.error("角色code为空！");
            throw new IllegalArgumentException("角色code为空！");
        }
        if (StringUtils.isBlank(sysRole.getName())) {
            logger.error("角色名称为空！");
            throw new IllegalArgumentException("角色名称为空！");
        }
        boolean b = sysRoleService.saveOrUpdateRole(sysRole);
        return Result.result(b);
    }

    @GetMapping("/getRoleAll")
    @ApiOperation(value = "获取所有Role")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('"+ AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getRoleAll() {
        return Result.succeed(sysRoleService.list());
    }
}
