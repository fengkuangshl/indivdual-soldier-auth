package com.key.win.system.util;

import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.common.model.system.SysMenu;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuUtils {
    /**
     * 两层循环实现建树
     *
     * @return
     */
    public static List<SysMenu> treeBuilder(List<SysMenu> sysMenus) {
        List<SysMenu> menus = new ArrayList<SysMenu>();
        if (CollectionUtils.isNotEmpty(sysMenus)) {
            for (SysMenu sysMenu : sysMenus) {
                if (sysMenu.getParentId().equals(IndividualSoldierAuthConstantUtils.TREE_PARENT_ID)) {
                    menus.add(sysMenu);
                }
            }
            if (!CollectionUtils.isEmpty(menus)) {
                Map<Long, SysMenu> parentSysMenuMap = menus.stream().collect(Collectors.toMap(SysMenu::getId, sysMenu -> sysMenu));
                for (SysMenu menu : sysMenus) {
                    SysMenu parentSysMenu = parentSysMenuMap.get(menu.getParentId());
                    if (parentSysMenu != null) {
                        if (parentSysMenu.getSubMenus() == null) {
                            parentSysMenu.setSubMenus(new ArrayList<>());
                        }
                        parentSysMenu.getSubMenus().add(menu);
                    }
                }
            }

        }

        return menus;
    }
}
