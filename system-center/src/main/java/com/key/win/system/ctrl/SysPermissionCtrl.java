package com.key.win.system.ctrl;

import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.*;
import com.key.win.common.enums.PermissionEnum;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.common.model.system.*;
import com.key.win.security.annotation.PreAuthorize;
import com.key.win.system.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/permission/*")
@Api("Permission相关的api")
public class SysPermissionCtrl {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String AUTHORITY_PREFIX = "system::permission::SysPermission::";
    @Autowired
    private SysPermissionService sysPermissionService;

    @PostMapping("/findSysPermissionByPaged")
    @ApiOperation(value = "Permission分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
    public PageResult<SysPermission> findSysPermissionByPaged(@RequestBody PageRequest<SysPermission> t) {
        return sysPermissionService.findSysPermissionByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取Permission")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::ID')")
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysPermissionService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = true)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "DELETE')")
    public Result delete(@PathVariable Long id) {
        boolean b = sysPermissionService.removeById(id);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = true)
    @PreAuthorize("hasAnyAuthority('" + AUTHORITY_PREFIX + "MODIFY','" + AUTHORITY_PREFIX + "ADD')")
    public Result saveOrUpdate(@RequestBody SysPermission sysPermission) {
        if (StringUtils.isBlank(sysPermission.getPermission())) {
            logger.error("权限标识为空！");
            throw new BizException("权限标识为空！");
        }
        if (StringUtils.isBlank(sysPermission.getName())) {
            logger.error("权限名称为空！");
            throw new BizException("权限名称为空！");
        }
        boolean b = sysPermissionService.saveOrUpdatePermission(sysPermission);
        return Result.result(b);
    }

    @GetMapping("/getPermissionAll")
    @ApiOperation(value = "获取所有Permission")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getPermissionAll() {
        return Result.succeed(sysPermissionService.list());
    }

    @GetMapping("/getPermissionEnum")
    @ApiOperation(value = "获取所有PermissionEnum")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getPermissionEnum(){
        return Result.succeed(PermissionEnum.values());
    }
}
