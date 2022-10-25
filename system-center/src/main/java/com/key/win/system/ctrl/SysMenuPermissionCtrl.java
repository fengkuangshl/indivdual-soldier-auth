package com.key.win.system.ctrl;

import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.basic.web.Result;
import com.key.win.common.model.system.SysMenu;
import com.key.win.common.model.system.SysMenuPermission;
import com.key.win.common.model.system.SysPermission;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.security.annotation.PreAuthorize;
import com.key.win.system.service.SysMenuPermissionService;
import com.key.win.system.service.SysMenuService;
import com.key.win.system.service.SysPermissionService;
import com.key.win.system.util.MenuUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagePermission/*")
@Api("页面权限相关的api")
public class SysMenuPermissionCtrl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String AUTHORITY_PREFIX = "system::menu-permission::SysMenuPermission::";
    @Autowired
    private SysMenuPermissionService sysMenuPermissionService;
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @PostMapping("/findSysMenuPermissionByPaged")
    @ApiOperation(value = "SysMenuPermission分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
    public PageResult<SysMenuPermission> findSysMenuPermissionByPaged(@RequestBody PageRequest<SysMenuPermission> t) {
        return sysMenuPermissionService.findSysMenuPermissionByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取SysMenuPermission")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::ID')")
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysMenuPermissionService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "DELETE')")
    public Result delete(@PathVariable Long id) {
        boolean b = sysMenuPermissionService.removeById(id);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAnyAuthority('" + AUTHORITY_PREFIX + "MODIFY','" + AUTHORITY_PREFIX + "ADD')")
    public Result saveOrUpdateSysMenuPermission(@RequestBody SysMenuPermission sysMenuPermission) {
        if (StringUtils.isBlank(sysMenuPermission.getPermissionCode())) {
            logger.error("权限标识为空！");
            throw new BizException("权限标识为空！");
        }
        boolean b = sysMenuPermissionService.saveOrUpdateSysMenuPermission(sysMenuPermission);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value = "批量新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAnyAuthority('" + AUTHORITY_PREFIX + "MODIFY','" + AUTHORITY_PREFIX + "ADD')")
    public Result saveOrUpdateSysMenuPermissionForBatch(@RequestBody List<SysMenuPermission> sysMenuPermissions) {
        if (org.springframework.util.CollectionUtils.isEmpty(sysMenuPermissions)) {
            return Result.failed("页面权限集合为空！！");
        }
        boolean b = sysMenuPermissionService.saveOrUpdateSysMenuPermissionForBatch(sysMenuPermissions);
        return Result.result(b);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有Permission")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getAll() {
        return Result.succeed(sysMenuPermissionService.list());
    }

    @GetMapping("/getGrantMenuPermission")
    @ApiOperation(value = "获取所有Permission")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getGrantMenuPermission() {
        List<SysMenuPermission> sysMenuPermissionByChecked = sysMenuPermissionService.findSysMenuPermissionByChecked(true);
        return Result.succeed(sysMenuPermissionByChecked);
    }


    @GetMapping("/get/page")
    @ApiOperation(value = "获取所有页面权限，并进行组装返回")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getPagePermission() {
        Map<String, Object> content = new HashMap<>();
        List<SysMenu> treeMenu = MenuUtils.treeBuilder(menuService.list());
        List<SysPermission> sysPermissions = sysPermissionService.list();
        List<SysMenuPermission> sysMenuPermissions = sysMenuPermissionService.list();
        Map<String, SysMenuPermission> sysMenuPermissionMap = sysMenuPermissions.stream().collect(Collectors.toMap(sysMenuPermission -> sysMenuPermission.getMenuId() + "" + sysMenuPermission.getPermissionId(), sysMenuPermission -> sysMenuPermission));
        Map<Long, SysMenuPermission> titleMap = setTableTitle(sysPermissions);
        content.put("title", titleMap.values());
        List<Map<String, Object>> treeSysMenuPermission = setTableBodyData(sysMenuPermissionMap, treeMenu, titleMap, sysPermissions);
        content.put("data", treeSysMenuPermission);
        return Result.succeed(content);
    }

    private List<Map<String, Object>> setTableBodyData(Map<String, SysMenuPermission> sysMenuPermissionMap, List<SysMenu> treeMenu, Map<Long, SysMenuPermission> titleMap, List<SysPermission> sysPermissions) {
        List<Map<String, Object>> treeSysMenuPermission = new ArrayList<>();
        List<Long> permissions = titleMap.keySet().stream().filter(key -> key != -1).collect(Collectors.toList());
        Map<Long, SysPermission> sysPermissionMap = sysPermissions.stream().collect(Collectors.toMap(SysPermission::getId, sysPermission -> sysPermission));
        setRowData(sysMenuPermissionMap, treeMenu, null, treeSysMenuPermission, titleMap, permissions, sysPermissionMap);
        return treeSysMenuPermission;
    }

    private void setRowData(Map<String, SysMenuPermission> sysMenuPermissionMap, List<SysMenu> treeMenu, LinkedHashMap<String, Object> currentRow, List<Map<String, Object>> treeSysMenuPermission, Map<Long, SysMenuPermission> titleMap, List<Long> permissions, Map<Long, SysPermission> sysPermissionMap) {
        for (SysMenu menu : treeMenu) {
            LinkedHashMap<String, Object> rowPagePermission = new LinkedHashMap<>();
            setRowMenu(titleMap, menu, rowPagePermission);
            for (Long key : permissions) {
                SysMenuPermission sysMenuPermission = sysMenuPermissionMap.get(menu.getId() + "" + titleMap.get(key).getPermissionId());
                if (sysMenuPermission == null) {
                    SysPermission sysPermission = sysPermissionMap.get(key);
                    sysMenuPermission = new SysMenuPermission();
                    sysMenuPermission.setPermissionId(menu.getSubMenus().size() == 0 ? sysPermission.getId() : null);
                    sysMenuPermission.setMenuId(menu.getSubMenus().size() == 0 ? menu.getId() : null);
                    sysMenuPermission.setChecked(Boolean.FALSE);
                }
                rowPagePermission.put(titleMap.get(key).getPropertyName(), sysMenuPermission);
            }
            if (currentRow != null) {
                List children = (List) currentRow.get("children");
                if (children == null) {
                    children = new ArrayList();
                    currentRow.put("children", children);
                }
                children.add(rowPagePermission);
            } else {
                treeSysMenuPermission.add(rowPagePermission);
            }
            if (menu.getSubMenus().size() > 0) {
                this.setRowData(sysMenuPermissionMap, menu.getSubMenus(), rowPagePermission, treeSysMenuPermission, titleMap, permissions, sysPermissionMap);
            }
        }
    }

    private void setRowMenu(Map<Long, SysMenuPermission> titleMap, SysMenu menu, LinkedHashMap<String, Object> rowPagePermission) {
        SysMenuPermission colSysMenu = new SysMenuPermission();
        colSysMenu.setPermissionName(menu.getName());
        rowPagePermission.put(titleMap.get(-1L).getPropertyName(), colSysMenu);
    }

    private Map<Long, SysMenuPermission> setTableTitle(List<SysPermission> sysPermissions) {
        Map<Long, SysMenuPermission> titleMap = new LinkedHashMap<>();
        SysMenuPermission menuTitle = new SysMenuPermission();
        menuTitle.setPropertyName("menu1");
        menuTitle.setPermissionName("菜单");
        titleMap.put(-1L, menuTitle);
        for (SysPermission sysPermission : sysPermissions) {
            SysMenuPermission permissionTitle = new SysMenuPermission();
            permissionTitle.setPermissionName(sysPermission.getName());
            permissionTitle.setPermissionId(sysPermission.getId());
            permissionTitle.setPropertyName("permission" + sysPermission.getId());
            titleMap.put(sysPermission.getId(), permissionTitle);
        }
        return titleMap;
    }
}
