package com.key.win.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.common.model.system.SysMenu;
import com.key.win.common.model.system.SysMenuPermission;
import com.key.win.common.model.system.SysPermission;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import com.key.win.system.dao.SysMenuPermissionDao;
import com.key.win.system.service.SysMenuPermissionService;
import com.key.win.system.service.SysMenuService;
import com.key.win.system.service.SysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysMenuPermissionServiceImpl extends ServiceImpl<SysMenuPermissionDao, SysMenuPermission> implements SysMenuPermissionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysPermissionService sysPermissionService;


    @Override
    public List<SysMenuPermission> findSysMenuPermission(SysMenuPermission sysMenuPermission) {
        List<SysMenuPermission> list = this.list(buildLambdaQueryWrapper(sysMenuPermission));
        return list;
    }

    @Override
    public PageResult<SysMenuPermission> findSysMenuPermissionByPaged(PageRequest<SysMenuPermission> t) {

        MybatisPageServiceTemplate<SysMenuPermission, SysMenuPermission> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysMenuPermission, SysMenuPermission>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysMenuPermission sysMenuPermission) {
                LambdaQueryWrapper<SysMenuPermission> lambdaQueryWrapper = buildLambdaQueryWrapper(sysMenuPermission);

                return lambdaQueryWrapper;
            }
        };

        return mybatiesPageServiceTemplate.doPagingQuery(t);
    }

    public List<SysMenuPermission> findSysMenuPermissionByChecked(boolean checked) {
        SysMenuPermission sysMenuPermission = new SysMenuPermission();
        sysMenuPermission.setChecked(checked);
        List<SysMenuPermission> list = this.list(buildLambdaQueryWrapper(sysMenuPermission));
        return list;
    }
    public List<SysMenuPermission> findSysMenuPermissionByIds(Set<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        SysMenuPermission sysMenuPermission = new SysMenuPermission();
        sysMenuPermission.setMenuPermissionIds(ids);
        List<SysMenuPermission> list = this.list(buildLambdaQueryWrapper(sysMenuPermission));
        return list;
    }

    private LambdaQueryWrapper<SysMenuPermission> buildLambdaQueryWrapper(SysMenuPermission sysMenuPermission) {
        LambdaQueryWrapper<SysMenuPermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (sysMenuPermission != null) {
            if (StringUtils.isNotBlank(sysMenuPermission.getPermissionCode())) {
                lambdaQueryWrapper.eq(SysMenuPermission::getPermissionCode, sysMenuPermission.getPermissionCode());
            }
            if (!CollectionUtils.isEmpty(sysMenuPermission.getMenuPermissionIds())) {
                lambdaQueryWrapper.in(SysMenuPermission::getId, sysMenuPermission.getMenuPermissionIds());
            }
            if (sysMenuPermission.getChecked() != null) {
                lambdaQueryWrapper.eq(SysMenuPermission::getChecked, sysMenuPermission.getChecked());
            }
        }
        return lambdaQueryWrapper;
    }


    @Override
    public boolean saveOrUpdateSysMenuPermission(SysMenuPermission sysMenuPermission) {
        SysMenuPermission po = null;
        if (sysMenuPermission.getId() != null) {
            po = this.getById(sysMenuPermission.getId());
            po.setChecked(sysMenuPermission.getChecked());
        } else {
            po = sysMenuPermission;
            SysPermission byId = sysPermissionService.getById(po.getPermissionId());
            if (byId != null) {
                po.setPermissionCode(sysMenuPermission.getMenuId() + "::" + byId.getPermission());
            }

        }
        return this.saveOrUpdate(po);
    }

    @Override
    public boolean saveOrUpdateSysMenuPermissionForBatch(List<SysMenuPermission> sysMenuPermissions) {
        List<SysMenuPermission> newSysMenuPermission = new ArrayList<>();
        List<SysPermission> sysPermissions = sysPermissionService.list();
        List<SysMenu> sysMenus = menuService.list();
        List<SysMenuPermission> sysMenuPermissionAll = this.findSysMenuPermission(null);
        Map<Long, SysMenu> sysMenuMap = sysMenus.stream().collect(Collectors.toMap(SysMenu::getId, sysMenu -> sysMenu));
        Map<Long, SysPermission> sysPermissionMap = sysPermissions.stream().collect(Collectors.toMap(SysPermission::getId, sysPermission -> sysPermission));
        Map<Long, SysMenuPermission> sysMenuPermissionMap = sysMenuPermissionAll.stream().collect(Collectors.toMap(SysMenuPermission::getId, sysMenuPermission -> sysMenuPermission));
        for (int i = sysMenuPermissions.size() - 1; i >= 0; i--) {
            SysMenuPermission sysMenuPermission = sysMenuPermissions.get(i);
            if (sysMenuPermission.getId() != null) {
                SysMenuPermission sysMenuPermissionDb = sysMenuPermissionMap.get(sysMenuPermission.getId());
                sysMenuPermissionDb.setDelete(Boolean.FALSE);
                if (sysMenuPermissionDb.getChecked() != sysMenuPermission.getChecked()) {
                    sysMenuPermissionDb.setChecked(sysMenuPermission.getChecked());
                    newSysMenuPermission.add(sysMenuPermissionDb);
                }
            } else {
                SysPermission sysPermission = sysPermissionMap.get(sysMenuPermission.getPermissionId());
                if (sysPermission != null) {
                    SysMenu sysMenu = sysMenuMap.get(sysMenuPermission.getMenuId());
                    String path = sysMenu.getPath().replaceAll("/", "::");
                    sysMenuPermission.setPermissionCode(path + "::" + sysPermission.getPermission());
                    newSysMenuPermission.add(sysMenuPermission);
                }
            }
        }
        //checkPermission(sysMenuPermissions);
        Set<Long> deleteIds = sysMenuPermissionAll.stream().filter(sysMenuPermission -> sysMenuPermission.isDelete()).map(sysMenuPermission -> sysMenuPermission.getId()).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(deleteIds)) {
            super.removeByIds(deleteIds);
        }
        if (!CollectionUtils.isEmpty(newSysMenuPermission)) {
            return super.saveOrUpdateBatch(newSysMenuPermission);
        }
        return true;
    }

    private void checkPermission(List<SysMenuPermission> sysMenuPermissions) {
        StringBuffer errorInfo = new StringBuffer();
        List<SysMenuPermission> sysMenuPermissionAll = this.findSysMenuPermission(null);
        Map<Long, SysMenuPermission> mapKeyId = sysMenuPermissionAll.stream().collect(Collectors.toMap(SysMenuPermission::getId, sysMenuPermission -> sysMenuPermission));
        Map<String, SysMenuPermission> mapKeyPermissionCode = sysMenuPermissionAll.stream().collect(Collectors.toMap(SysMenuPermission::getPermissionCode, sysMenuPermission -> sysMenuPermission));
        for (SysMenuPermission sysMenuPermission : sysMenuPermissions) {
            if (sysMenuPermission.getId() != null) {
                SysMenuPermission existSysMenuPermissions = mapKeyId.get(sysMenuPermission.getId());
                if (existSysMenuPermissions == null) {
                    errorInfo.append("[" + sysMenuPermission.getMenuName() + "]菜单的[" + sysMenuPermission.getPermissionName() + "]权限不存在!\n");
                } else if (!existSysMenuPermissions.getPermissionCode().equals(sysMenuPermission.getPermissionCode())) {
                    errorInfo.append("[" + sysMenuPermission.getMenuName() + "]菜单的权限标识[" + sysMenuPermission.getPermissionCode() + "]已锁定，不允许修改！!\n");
                }
            } else {
                SysMenuPermission existSysMenuPermissions = mapKeyPermissionCode.get(sysMenuPermission.getPermissionCode());
                if (existSysMenuPermissions != null) {
                    errorInfo.append("[" + sysMenuPermission.getMenuName() + "]菜单的权限标识[" + sysMenuPermission.getPermissionCode() + "]已存在！\n");
                }
            }
        }
        if (errorInfo.length() > 0) {
            errorInfo.deleteCharAt(errorInfo.length());
            throw new BizException(errorInfo.toString());
        }
    }

    private List<SysMenuPermission> findSysMenuPermissionByPermissionCode(String permissionCode) {
        SysMenuPermission sysMenuPermission = new SysMenuPermission();
        sysMenuPermission.setPermissionCode(permissionCode);
        return this.findSysMenuPermission(sysMenuPermission);
    }


}
