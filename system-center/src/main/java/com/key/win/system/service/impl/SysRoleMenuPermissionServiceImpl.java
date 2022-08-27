package com.key.win.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.common.model.system.SysRoleMenuPermission;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import com.key.win.system.dao.SysRoleMenuPermissionDao;
import com.key.win.system.service.SysRoleMenuPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysRoleMenuPermissionServiceImpl extends ServiceImpl<SysRoleMenuPermissionDao, SysRoleMenuPermission> implements SysRoleMenuPermissionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<SysRoleMenuPermission> findSysRoleMenuPermission(SysRoleMenuPermission sysRoleMenuPermission) {
        List<SysRoleMenuPermission> list = this.list(buildLambdaQueryWrapper(sysRoleMenuPermission));
        return list;
    }

    @Override
    public PageResult<SysRoleMenuPermission> findSysRoleMenuPermissionByPaged(PageRequest<SysRoleMenuPermission> t) {

        MybatisPageServiceTemplate<SysRoleMenuPermission, SysRoleMenuPermission> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysRoleMenuPermission, SysRoleMenuPermission>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysRoleMenuPermission sysRoleMenuPermission) {
                LambdaQueryWrapper<SysRoleMenuPermission> lambdaQueryWrapper = buildLambdaQueryWrapper(sysRoleMenuPermission);

                return lambdaQueryWrapper;
            }
        };

        return mybatiesPageServiceTemplate.doPagingQuery(t);
    }

    public List<SysRoleMenuPermission> findGrantMenus(Long roleId) {
        return this.findGrantSysRoleMenuPermissionByRoleId(true, roleId, true);
    }

    public List<SysRoleMenuPermission> findGrantMenuPermissions(Long roleId) {
        return this.findGrantSysRoleMenuPermissionByRoleId(true, roleId, false);
    }

    public List<SysRoleMenuPermission> findGrantSysRoleMenuPermissionByRoleId(boolean checked, Long roleId, boolean menuPermissionIdIsNull) {
        Set<Long> roleIds = new HashSet<>();
        roleIds.add(roleId);
        return this.findGrantSysRoleMenuPermissionByRoleId(checked, roleIds, menuPermissionIdIsNull);
    }

    public List<SysRoleMenuPermission> findGrantSysRoleMenuPermissionByRoleId(boolean checked, Set<Long> roleIds, boolean menuPermissionIdIsNull) {
        checkRoleIds(roleIds);
        LambdaQueryWrapper<SysRoleMenuPermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRoleMenuPermission::getChecked, checked);
        lambdaQueryWrapper.in(SysRoleMenuPermission::getRoleId, roleIds);
        if (menuPermissionIdIsNull) {
            lambdaQueryWrapper.isNull(SysRoleMenuPermission::getMenuPermissionId);
        } else {
            lambdaQueryWrapper.isNotNull(SysRoleMenuPermission::getMenuPermissionId);
        }

        List<SysRoleMenuPermission> list = this.list(lambdaQueryWrapper);
        return list;
    }

    private void checkRoleIds(Set<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            logger.error("角色Id为空！！");
            throw new BizException("角色Id为空！！");
        }
    }

    public List<SysRoleMenuPermission> findGrantSysRoleMenuPermissionByRoleId(Long roleId) {
        checkRoleId(roleId);
        SysRoleMenuPermission sysRoleMenuPermission = new SysRoleMenuPermission();
        sysRoleMenuPermission.setRoleId(roleId);
        return this.findSysRoleMenuPermission(sysRoleMenuPermission);
    }
    public List<SysRoleMenuPermission> findGrantSysRoleMenuPermissionByRoleId(Set<Long> roleIds) {
        checkRoleIds(roleIds);
        SysRoleMenuPermission sysRoleMenuPermission = new SysRoleMenuPermission();
        sysRoleMenuPermission.setRoleIds(roleIds);
        return this.findSysRoleMenuPermission(sysRoleMenuPermission);
    }

    public List<SysRoleMenuPermission> findGrantMenus(Set<Long> roleIds) {
        return this.findGrantSysRoleMenuPermissionByRoleId(true, roleIds, true);
    }

    public List<SysRoleMenuPermission> findGrantMenuPermissions(Set<Long> roleIds) {
        return this.findGrantSysRoleMenuPermissionByRoleId(true, roleIds, false);
    }

    private void checkRoleId(Long roleId) {
        if (roleId == null) {
            logger.error("角色Id为空！！");
            throw new BizException("角色Id为空！！");
        }
    }

    private LambdaQueryWrapper<SysRoleMenuPermission> buildLambdaQueryWrapper(SysRoleMenuPermission sysRoleMenuPermission) {
        LambdaQueryWrapper<SysRoleMenuPermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (sysRoleMenuPermission != null) {
            if (sysRoleMenuPermission.getRoleId() != null) {
                lambdaQueryWrapper.eq(SysRoleMenuPermission::getRoleId, sysRoleMenuPermission.getRoleId());
            }
            if (!CollectionUtils.isEmpty(sysRoleMenuPermission.getRoleIds())) {
                lambdaQueryWrapper.in(SysRoleMenuPermission::getRoleId, sysRoleMenuPermission.getRoleIds());
            }
            if (sysRoleMenuPermission.getMenuId() != null) {
                lambdaQueryWrapper.eq(SysRoleMenuPermission::getMenuId, sysRoleMenuPermission.getMenuId());
            }
            if (sysRoleMenuPermission.getMenuPermissionId() != null) {
                lambdaQueryWrapper.eq(SysRoleMenuPermission::getMenuPermissionId, sysRoleMenuPermission.getMenuPermissionId());
            }
            if (sysRoleMenuPermission.getChecked() != null) {
                lambdaQueryWrapper.eq(SysRoleMenuPermission::getChecked, sysRoleMenuPermission.getChecked());
            }
        }
        return lambdaQueryWrapper;
    }


    @Override
    public boolean saveOrUpdateSysRoleMenuPermission(SysRoleMenuPermission sysRoleMenuPermission) {
        SysRoleMenuPermission po = null;
        if (sysRoleMenuPermission.getId() != null) {
            po = this.getById(sysRoleMenuPermission.getId());
            po.setChecked(sysRoleMenuPermission.getChecked());
        } else {
            po = sysRoleMenuPermission;

        }
        return this.saveOrUpdate(po);
    }

    @Override
    public boolean saveOrUpdateSysRoleMenuPermissionForBatch(List<SysRoleMenuPermission> sysRoleMenuPermissions) {
        List<SysRoleMenuPermission> newSysRoleMenuPermission = new ArrayList<>();
        List<SysRoleMenuPermission> sysRoleMenuPermissionByRoleId = this.findSysRoleMenuPermissionByRoleId(sysRoleMenuPermissions.get(0).getRoleId());
        Map<Long, SysRoleMenuPermission> sysRoleMenuPermissionMap = sysRoleMenuPermissionByRoleId.stream().collect(Collectors.toMap(SysRoleMenuPermission::getId, sysRoleMenuPermission -> sysRoleMenuPermission));
        for (int i = sysRoleMenuPermissions.size() - 1; i >= 0; i--) {
            SysRoleMenuPermission sysRoleMenuPermission = sysRoleMenuPermissions.get(i);
            if (sysRoleMenuPermission.getId() != null) {
                SysRoleMenuPermission sysMenuPermissionDb = sysRoleMenuPermissionMap.get(sysRoleMenuPermission.getId());
                sysMenuPermissionDb.setDelete(Boolean.FALSE);
                if (sysMenuPermissionDb.getChecked() != sysRoleMenuPermission.getChecked()) {
                    sysMenuPermissionDb.setChecked(sysRoleMenuPermission.getChecked());
                    newSysRoleMenuPermission.add(sysMenuPermissionDb);
                }
            } else {
                if (sysRoleMenuPermission.getRoleId() != null && sysRoleMenuPermission.getMenuId() != null) {
                    sysRoleMenuPermission.setChecked(sysRoleMenuPermission.getChecked());
                    newSysRoleMenuPermission.add(sysRoleMenuPermission);
                }

            }
        }
        //checkPermission(sysMenuPermissions);
        Set<Long> deleteIds = sysRoleMenuPermissionByRoleId.stream().filter(sysRoleMenuPermission -> sysRoleMenuPermission.isDelete()).map(sysRoleMenuPermission -> sysRoleMenuPermission.getId()).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(deleteIds)) {
            super.removeByIds(deleteIds);
        }
        if (!CollectionUtils.isEmpty(newSysRoleMenuPermission)) {
            return super.saveOrUpdateBatch(newSysRoleMenuPermission);
        }
        return true;
    }

    private List<SysRoleMenuPermission> findSysRoleMenuPermissionByRoleId(Long roleId) {
        SysRoleMenuPermission sysRoleMenuPermission = new SysRoleMenuPermission();
        sysRoleMenuPermission.setRoleId(roleId);
        return this.findSysRoleMenuPermission(sysRoleMenuPermission);
    }
}
