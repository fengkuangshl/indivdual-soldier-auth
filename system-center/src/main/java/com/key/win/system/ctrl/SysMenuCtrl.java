package com.key.win.system.ctrl;

import com.key.win.basic.web.*;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.common.model.system.*;
import com.key.win.security.annotation.PreAuthorize;
import com.key.win.system.service.SysMenuService;
import com.key.win.system.util.MenuUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/menu/*")
@Api("Menu相关的api")
public class SysMenuCtrl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String AUTHORITY_PREFIX = "system::menu::SysMenu::";
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/current")
    @ApiOperation(value = "获取当前用户授权的Menus")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getCurrentMenus() {
        Authentication authentication = AuthenticationUtil.getAuthentication();
        return Result.succeed(MenuUtils.treeBuilder(authentication.getMenus()));
    }
    
    @PostMapping("/findSysMenuByPaged")
    @ApiOperation(value = "Menu分页")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::PAGED')")
    public PageResult<SysMenu> findSysMenuByPaged(@RequestBody PageRequest<SysMenu> t) {
        return sysMenuService.findSysMenuByPaged(t);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取Menu")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::ID')")
    public Result get(@PathVariable Long id) {
        return Result.succeed(sysMenuService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "DELETE')")
    public Result delete(@PathVariable Long id) {
        boolean b = sysMenuService.deleteById(id);
        return Result.result(b);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增/更新")
    @LogAnnotation(module = "system", recordRequestParam = false)
    @PreAuthorize("hasAnyAuthority('" + AUTHORITY_PREFIX + "MODIFY','" + AUTHORITY_PREFIX + "ADD')")
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
    @PreAuthorize("hasAuthority('" + AUTHORITY_PREFIX + "QUERY::LIST')")
    public Result getMenuAll() {
        return Result.succeed(sysMenuService.list());
    }

    @GetMapping("/getMenuTree")
    @ApiOperation(value = "获取MenuTree")
    @LogAnnotation(module = "system", recordRequestParam = false)
    public Result getMenuTree() {
        return Result.succeed(sysMenuService.getMenuTree());
    }

    @GetMapping("/getOnes")
    @ApiOperation(value = "获取菜单以及顶级菜单")
    public Result getOnes() {
        List<SysMenu> list = sysMenuService.findOnes();
        return Result.succeed(list, "");

    }
}
