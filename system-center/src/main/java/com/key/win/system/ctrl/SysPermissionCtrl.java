package com.key.win.system.ctrl;

import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.*;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.common.model.system.*;
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
    @Autowired
    private SysPermissionService sysPermissionService;

    @PostMapping("/findSysPermissionByPaged")
    @ApiOperation(value = "Permission分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public PageResult<SysPermission> findSysPermissionByPaged(@RequestBody PageRequest<SysPermission> t) {
        return sysPermissionService.findSysPermissionByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取Permission")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysPermissionService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result delete(@PathVariable Long id) {
        boolean b = sysPermissionService.removeById(id);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = true)
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
    public Result getPermissionAll() {
        return Result.succeed(sysPermissionService.list());
    }

    @PostMapping("/granted")
    @ApiOperation(value = "角色分配权限")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result setPermissionToRole(@RequestBody SysPermission sysPermission) {
        if (sysPermission.getRoleId() == null) {
            logger.error("角色Id为空！");
            throw new BizException("角色Id为空！");
        }
        if (CollectionUtils.isEmpty(sysPermission.getAuthIds())) {
            logger.error("权限为空！");
            throw new BizException("权限为空！");
        }
        sysPermissionService.setPermissionToRole(sysPermission.getRoleId(), sysPermission.getAuthIds());
        return Result.succeed("角色分配权限成功");
    }

    @GetMapping("/findPermissionByRoleId/{roleId}")
    @ApiOperation(value = "角色获取权限")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result findPermissionByRoleId(@PathVariable Long roleId) {
        return Result.succeed(this.sysPermissionService.findSysPermissionByRoleId(roleId));
    }

    @GetMapping("/findPermissionIdsByRoleId/{roleId}")
    @ApiOperation(value = "角色获取权限Ids")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result findPermissionIdsByRoleId(@PathVariable Long roleId) {
        Set<Long> sysPermissionIds = new HashSet<>();
        List<SysPermission> sysPermissionByRoleId = this.sysPermissionService.findSysPermissionByRoleId(roleId);
        if (!CollectionUtils.isEmpty(sysPermissionByRoleId)) {
            sysPermissionIds = sysPermissionByRoleId.stream().map(SysPermission::getId).collect(Collectors.toSet());
        }
        return Result.succeed(sysPermissionIds);
    }

    @ApiOperation(value = "根据roleId获取对应的权限")
    @GetMapping("/{roleId}")
    @LogAnnotation(module="user-center",recordRequestParam=false)
    public Result getAuthByRoleId(@PathVariable Long roleId) {
        List<Map<String, Object>> authTrees = new ArrayList<>();
        List<SysPermission> roleAuths = sysPermissionService.findSysPermissionByRoleId(roleId);//根据roleId获取对应的权限
        List<SysPermission> allAuths = sysPermissionService.list();//根据roleId获取对应的权限
        Map<Long, SysPermission> roleAuthsMap = roleAuths.stream().collect(Collectors.toMap(SysPermission::getId, SysPermission->SysPermission));

        for (SysPermission sysPermission : allAuths){
            Map<String, Object> authTree = new HashMap<>();
            authTree.put("id",sysPermission.getId());
            authTree.put("name",sysPermission.getName());
            authTree.put("open",true);
            authTree.put("checked", false);
            if (roleAuthsMap.get(sysPermission.getId())!=null){
                authTree.put("checked", true);
            }
            authTrees.add(authTree);
        }
        return Result.succeed(authTrees);
    }
}
