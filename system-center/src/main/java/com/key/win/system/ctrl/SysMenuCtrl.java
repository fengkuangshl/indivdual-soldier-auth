package com.key.win.system.ctrl;

import com.key.win.basic.util.IndivdualSoldierAuthConstantUtils;
import com.key.win.basic.web.*;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.common.model.system.*;
import com.key.win.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu/*")
@Api("Menu相关的api")
public class SysMenuCtrl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/current")
    @ApiOperation(value = "获取当前用户授权的Menus")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getCurrentMenus() {
        Authentication authentication = AuthenticationUtil.getAuthentication();
        return Result.succeed(treeBuilder(authentication.getMenus()));
    }

    /**
     * 两层循环实现建树
     *
     * @param sysMenus
     * @return
     */
    public static List<SysMenu> treeBuilder(List<SysMenu> sysMenus) {
        List<SysMenu> menus = new ArrayList<SysMenu>();
        if (CollectionUtils.isNotEmpty(sysMenus)) {
            for (SysMenu sysMenu : sysMenus) {
                if (sysMenu.getParentId().equals(IndivdualSoldierAuthConstantUtils.TREE_PARENT_ID)) {
                    menus.add(sysMenu);
                }
                for (SysMenu menu : sysMenus) {
                    if (menu.getParentId().equals(sysMenu.getId())) {
                        if (sysMenu.getSubMenus() == null) {
                            sysMenu.setSubMenus(new ArrayList<>());
                        }
                        sysMenu.getSubMenus().add(menu);
                    }
                }
            }
        }

        return menus;
    }


    @PostMapping("/findSysMenuByPaged")
    @ApiOperation(value = "Menu分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public PageResult<SysMenu> findSysMenuByPaged(@RequestBody PageRequest<SysMenu> t) {
        return sysMenuService.findSysMenuByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取Menu")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysMenuService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result delete(@PathVariable Long id) {
        boolean b = sysMenuService.deleteById(id);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result saveOrUpdate(@RequestBody SysMenu sysMenu) {
        if (sysMenu.getSort() == null) {
            logger.error("排序号为空！");
            throw new IllegalArgumentException("排序号为空！");
        }
        if (StringUtils.isBlank(sysMenu.getName())) {
            logger.error("菜单名称为空！");
            throw new IllegalArgumentException("菜单名称为空！");
        }
        boolean b = sysMenuService.saveOrUpdateMenu(sysMenu);
        return Result.result(b);
    }

    @GetMapping("/getMenuAll")
    @ApiOperation(value = "获取所有Menu")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getMenuAll() {
        return Result.succeed(sysMenuService.list());
    }

    @GetMapping("/getMenuTree")
    @ApiOperation(value = "获取MenuTree")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getMenuTree() {
        return Result.succeed(sysMenuService.getMenuTree());
    }

    @PostMapping("/granted")
    @ApiOperation(value = "角色分配菜单")
    @LogAnnotation(module = "system", recordRequestParam = true)
    public Result setMenuToRole(@RequestBody SysMenu sysMenu) {
        if (CollectionUtils.isEmpty(sysMenu.getMenuIds())) {
            logger.error("授权菜单为空！");
            throw new IllegalArgumentException("授权菜单为空！");
        }
        if (sysMenu.getRoleId() == null) {
            logger.error("角色信息为空！");
            throw new IllegalArgumentException("角色信息为空！");
        }
        sysMenuService.setMenuToRole(sysMenu.getRoleId(), sysMenu.getMenuIds());
        return Result.succeed("角色分配菜单成功");
    }

    @GetMapping("/findSysMenuByRoleId/{roleId}")
    @ApiOperation(value = "角色获取菜单")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result findSysMenuByRoleId(@PathVariable Long roleId) {
        return Result.succeed(this.sysMenuService.findSysMenuByRoleId(roleId));
    }

    @GetMapping("/findSysMenuIdsByRoleId/{roleId}")
    @ApiOperation(value = "角色获取菜单Ids")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result findSysMenuIdsByRoleId(@PathVariable Long roleId) {
        List<SysMenu> sysMenuByRoleId = this.sysMenuService.findSysMenuByRoleId(roleId);
        Set<Long> collect = new HashSet<>();
        if (!CollectionUtils.isEmpty(sysMenuByRoleId)) {
            collect = sysMenuByRoleId.stream().map(SysMenu::getId).collect(Collectors.toSet());
        }
        return Result.succeed(collect);
    }

    @GetMapping("/getOnes")
    @ApiOperation(value = "获取菜单以及顶级菜单")
    public Result getOnes() {
        List<SysMenu> list = sysMenuService.findOnes();
        return Result.succeed(list, "");

    }

    @GetMapping("/{roleId}")
    @ApiOperation(value = "根据roleId获取对应的菜单")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getMenusByRoleId(@PathVariable Long roleId) {
        List<SysMenu> roleMenus = sysMenuService.findSysMenuByRoleId(roleId); // 获取该角色对应的菜单
        List<SysMenu> allMenus = sysMenuService.list(); // 全部的菜单列表
        List<Map<String, Object>> authTrees = new ArrayList<>();
        Map<Long, SysMenu> roleMenusMap = roleMenus.stream()
                .collect(Collectors.toMap(SysMenu::getId, SysMenu -> SysMenu));
        for (SysMenu sysMenu : allMenus) {
            Map<String, Object> authTree = new HashMap<>();
            authTree.put("id", sysMenu.getId());
            authTree.put("name", sysMenu.getName());
            authTree.put("pId", sysMenu.getParentId());
            authTree.put("open", true);
            authTree.put("checked", false);
            if (roleMenusMap.get(sysMenu.getId()) != null) {
                authTree.put("checked", true);
            }
            authTrees.add(authTree);
        }
        return Result.succeed(authTrees);
    }
}
