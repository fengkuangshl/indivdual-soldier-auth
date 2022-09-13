package com.key.win.system.ctrl;

import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.common.model.system.SysMenu;
import com.key.win.common.model.system.SysMenuPermission;
import com.key.win.common.model.system.SysRoleMenuPermission;
import com.key.win.common.model.system.SysPermission;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.security.annotation.PreAuthorize;
import com.key.win.system.service.*;
import com.key.win.system.service.SysRoleMenuPermissionService;
import com.key.win.system.util.MenuUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sysrmpc/*")
@Api("授权中心api")
public class SysRoleMenuPermissionCtrl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String AUTHORITY_PREFIX = "system::role-menu-permission::SysRoleMenuPermission::";
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysRoleMenuPermissionService sysRoleMenuPermissionService;
    @Autowired
    private SysMenuPermissionService sysMenuPermissionService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @PostMapping("/findSysRoleMenuPermissionByPaged")
    @ApiOperation(value = "SysRoleMenuPermission分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
    public PageResult<SysRoleMenuPermission> findSysRoleMenuPermissionByPaged(@RequestBody PageRequest<SysRoleMenuPermission> t) {
        return sysRoleMenuPermissionService.findSysRoleMenuPermissionByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取SysRoleMenuPermission")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::ID')")
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysRoleMenuPermissionService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = true)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "DELETE')")
    public Result delete(@PathVariable Long id) {
        boolean b = sysRoleMenuPermissionService.removeById(id);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = true)
    @PreAuthorize("hasAnyAuthority('" + AUTHORITY_PREFIX + "MODIFY','" + AUTHORITY_PREFIX + "ADD')")
    public Result saveOrUpdateSysRoleMenuPermission(@RequestBody SysRoleMenuPermission sysRoleMenuPermission) {
        if (sysRoleMenuPermission.getRoleId() == null) {
            logger.error("角色信息为空！");
            throw new BizException("角色信息为空！");
        }
        if (sysRoleMenuPermission.getMenuPermissionId() == null) {
            logger.error("权限信息为空！");
            throw new BizException("权限信息为空！");
        }
        boolean b = sysRoleMenuPermissionService.saveOrUpdateSysRoleMenuPermission(sysRoleMenuPermission);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value = "批量新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = true)
    @PreAuthorize("hasAnyAuthority('" + AUTHORITY_PREFIX + "MODIFY','" + AUTHORITY_PREFIX + "ADD')")
    public Result saveOrUpdateSysRoleMenuPermissionForBatch(@RequestBody List<SysRoleMenuPermission> sysMenuPermissions) {
        if (org.springframework.util.CollectionUtils.isEmpty(sysMenuPermissions)) {
            return Result.failed("页面权限集合为空！！");
        }
        boolean b = sysRoleMenuPermissionService.saveOrUpdateSysRoleMenuPermissionForBatch(sysMenuPermissions);
        return Result.result(b);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有Permission")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getAll() {
        return Result.succeed(sysRoleMenuPermissionService.list());
    }

    @GetMapping("/get/grant/menu/{roleId}")
    @ApiOperation(value = "根据角色Id获取菜单")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getGrantMenus(@PathVariable Long roleId) {
        List<SysRoleMenuPermission> sysRoleMenuPermissions = sysRoleMenuPermissionService.findGrantMenus(roleId);
        return Result.succeed(sysRoleMenuPermissions);
    }

    @GetMapping("/get/grant/permission/{roleId}")
    @ApiOperation(value = "根据角色Id获取权限")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getGrantPermissions(@PathVariable Long roleId) {
        List<SysRoleMenuPermission> sysRoleMenuPermissions = sysRoleMenuPermissionService.findGrantMenuPermissions(roleId);
        return Result.succeed(sysRoleMenuPermissions);
    }


    @GetMapping("/get/grant/{roleId}")
    @ApiOperation(value = "根据角色Id获取菜单和权限并进行组装")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getPagePermission(@PathVariable Long roleId) {
        Map<String, Object> content = new HashMap<>();
        List<SysMenuPermission> sysMenuPermissionByChecked = sysMenuPermissionService.findSysMenuPermissionByChecked(true);
        Map<Long, SysMenuPermission> sysMenuPermissionMap = sysMenuPermissionByChecked.stream().collect(Collectors.toMap(SysMenuPermission::getId, sysMenuPermission -> sysMenuPermission));
        Map<String, SysMenuPermission> stringSysMenuPermissionMap = sysMenuPermissionByChecked.stream().collect(Collectors.toMap(sysMenuPermission -> {
            return sysMenuPermission.getMenuId() + "" + sysMenuPermission.getPermissionId();
        }, sysMenuPermission -> sysMenuPermission));
        List<SysMenu> treeMenu = MenuUtils.treeBuilder(menuService.list());
        List<SysRoleMenuPermission> sysRoleMenuPermissions = sysRoleMenuPermissionService.findGrantSysRoleMenuPermissionByRoleId(roleId);
        Map<String, SysRoleMenuPermission> sysRoleMenuPermissionMap = sysRoleMenuPermissions.stream()/*.filter(sysRoleMenuPermission -> sysMenuPermissionMap.get(sysRoleMenuPermission.getMenuPermissionId()) != null || (sysRoleMenuPermission.getRoleId() != null && sysRoleMenuPermission.getMenuId() != null))*/.collect(Collectors.toMap(sysRoleMenuPermission -> {
            Long menuPermissionId = sysRoleMenuPermission.getMenuPermissionId();
            SysMenuPermission sysMenuPermission = sysMenuPermissionMap.get(menuPermissionId);
            if (sysMenuPermission != null) {
                return sysRoleMenuPermission.getRoleId() + "" + sysRoleMenuPermission.getMenuId() + "" + sysMenuPermission.getPermissionId();
            } else {
                return sysRoleMenuPermission.getRoleId() + "" + sysRoleMenuPermission.getMenuId();
            }

        }, Function.identity(), (key1, key2) -> key2));
        List<SysPermission> sysPermissions = sysPermissionService.list();
        Map<Long, SysPermission> sysPermissionMap = sysPermissions.stream().collect(Collectors.toMap(SysPermission::getId, sysPermission -> sysPermission));

        Map<Long, SysRoleMenuPermission> titleMap = setTableTitle(sysPermissionMap, sysMenuPermissionByChecked);
        content.put("title", titleMap.values());
        List<Map<String, Object>> treeSysRoleMenuPermission = setTableBodyData(roleId, sysRoleMenuPermissionMap, treeMenu, titleMap, stringSysMenuPermissionMap);
        content.put("data", treeSysRoleMenuPermission);
        return Result.succeed(content);
    }

    private List<Map<String, Object>> setTableBodyData(Long roleId, Map<String, SysRoleMenuPermission> sysRoleMenuPermissions, List<SysMenu> treeMenu, Map<Long, SysRoleMenuPermission> titleMap, Map<String, SysMenuPermission> stringSysMenuPermissionMap) {
        List<Map<String, Object>> treeSysRoleMenuPermission = new ArrayList<>();
        List<Long> permissions = titleMap.keySet().stream().filter(key -> key != -1).collect(Collectors.toList());
        setRowData(roleId, sysRoleMenuPermissions, treeMenu, null, treeSysRoleMenuPermission, titleMap, permissions, stringSysMenuPermissionMap);
        return treeSysRoleMenuPermission;
    }

    private void setRowData(Long roleId, Map<String, SysRoleMenuPermission> sysRoleMenuPermissionMap, List<SysMenu> treeMenu, LinkedHashMap<String, Object> currentRow, List<Map<String, Object>> treeSysRoleMenuPermission, Map<Long, SysRoleMenuPermission> titleMap, List<Long> permissions, Map<String, SysMenuPermission> stringSysMenuPermissionMap) {
        for (SysMenu menu : treeMenu) {
            LinkedHashMap<String, Object> rowPermission = new LinkedHashMap<>();
            setRowMenu(roleId, titleMap, menu, rowPermission, sysRoleMenuPermissionMap);
            for (Long key : permissions) {
                SysRoleMenuPermission sysRoleMenuPermission = sysRoleMenuPermissionMap.get(roleId + "" + menu.getId() + "" + titleMap.get(key).getPermissionId());
                if (sysRoleMenuPermission == null) {
                    SysMenuPermission sysMenuPermission = stringSysMenuPermissionMap.get(menu.getId() + "" + titleMap.get(key).getPermissionId());
                    sysRoleMenuPermission = new SysRoleMenuPermission();
                    if (sysMenuPermission != null) {
                        sysRoleMenuPermission.setMenuPermissionId(menu.getSubMenus().size() == 0 ? sysMenuPermission.getId() : null);
                        sysRoleMenuPermission.setMenuId(menu.getSubMenus().size() == 0 ? menu.getId() : null);
                        sysRoleMenuPermission.setPermissionId(key);
                        sysRoleMenuPermission.setRoleId(roleId);
                        sysRoleMenuPermission.setChecked(Boolean.FALSE);
                    }

                }
                rowPermission.put(titleMap.get(key).getPropertyName(), sysRoleMenuPermission);
            }
            if (currentRow != null) {
                List children = (List) currentRow.get("children");
                if (children == null) {
                    children = new ArrayList();
                    currentRow.put("children", children);
                }
                children.add(rowPermission);
            } else {
                treeSysRoleMenuPermission.add(rowPermission);
            }
            if (menu.getSubMenus().size() > 0) {
                this.setRowData(roleId, sysRoleMenuPermissionMap, menu.getSubMenus(), rowPermission, treeSysRoleMenuPermission, titleMap, permissions, stringSysMenuPermissionMap);
            }
        }
    }

    private void setRowMenu(Long roleId, Map<Long, SysRoleMenuPermission> titleMap, SysMenu menu, LinkedHashMap<String, Object> rowPagePermission, Map<String, SysRoleMenuPermission> sysRoleMenuPermissionMap) {
        SysRoleMenuPermission sysRoleMenuPermission = sysRoleMenuPermissionMap.get(roleId + "" + menu.getId());
        SysRoleMenuPermission colSysMenu = new SysRoleMenuPermission();
        colSysMenu.setPermissionName(menu.getName());
        colSysMenu.setMenuId(menu.getId());
        colSysMenu.setRoleId(roleId);
        if (sysRoleMenuPermission != null) {
            colSysMenu.setChecked(sysRoleMenuPermission.getChecked());
            colSysMenu.setId(sysRoleMenuPermission.getId());
        }
        rowPagePermission.put(titleMap.get(-1L).getPropertyName(), colSysMenu);
    }

    private Map<Long, SysRoleMenuPermission> setTableTitle(Map<Long, SysPermission> sysPermissionMap, List<SysMenuPermission> sysMenuPermissionByChecked) {
        Map<Long, SysRoleMenuPermission> titleMap = new LinkedHashMap<>();
        SysRoleMenuPermission menuTitle = new SysRoleMenuPermission();
        menuTitle.setPropertyName("menu1");
        menuTitle.setPermissionName("菜单");
        titleMap.put(-1L, menuTitle);
        Map<Long, SysMenuPermission> sysMenuPermissionMap = sysMenuPermissionByChecked.stream().collect(Collectors.toMap(SysMenuPermission::getPermissionId, Function.identity(), (key1, key2) -> key2));
        for (Long key : sysMenuPermissionMap.keySet()) {
            SysPermission sysPermission = sysPermissionMap.get(key);
            SysRoleMenuPermission permissionTitle = new SysRoleMenuPermission();
            permissionTitle.setPermissionName(sysPermission.getName());
            permissionTitle.setPropertyName("permission" + sysPermission.getId());
            permissionTitle.setPermissionId(key);
            titleMap.put(sysPermission.getId(), permissionTitle);

        }
        return titleMap;
    }
}
