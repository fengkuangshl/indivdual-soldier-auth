package com.key.win.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.exception.BizException;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import com.key.win.basic.util.BeanUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.system.dao.SysPermissionDao;
import com.key.win.common.model.system.*;
import com.key.win.system.service.SysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<SysPermission> findSysPermission(SysPermission sysPermission) {
        LambdaQueryWrapper<SysPermission> lambdaQueryWrapper = new LambdaQueryWrapper<SysPermission>();
        if (sysPermission != null) {
            if (StringUtils.isNotBlank(sysPermission.getName())) {
                lambdaQueryWrapper.eq(SysPermission::getName, sysPermission.getName());
            }
            if (StringUtils.isNotBlank(sysPermission.getPermission())) {
                lambdaQueryWrapper.eq(SysPermission::getPermission, sysPermission.getPermission());
            }
        }
        List<SysPermission> list = this.list(lambdaQueryWrapper);
        return list;
    }

    @Override
    public PageResult<SysPermission> findSysPermissionByPaged(PageRequest<SysPermission> t) {

        MybatisPageServiceTemplate<SysPermission, SysPermission> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysPermission, SysPermission>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysPermission sysPermission) {
                LambdaQueryWrapper<SysPermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                if (sysPermission != null && StringUtils.isNotBlank(sysPermission.getName())) {
                    lambdaQueryWrapper
                            .like(SysPermission::getName, sysPermission.getName())
                            .or()
                            .like(SysPermission::getPermission, sysPermission.getName());
                }

                return lambdaQueryWrapper;
            }
        };

        return mybatiesPageServiceTemplate.doPagingQuery(t);
    }


    @Override
    public boolean saveOrUpdatePermission(SysPermission sysPermission) {
        SysPermission po = null;
        if (sysPermission.getId() != null) {
            po = this.getById(sysPermission.getId());
            if (po == null) {
                logger.error("权限不存在!");
                throw new BizException("权限不存在!");
            }
            if (!po.getPermission().equals(sysPermission.getPermission())) {
                logger.error("权限标识[{}]已锁定，不允许修改！", sysPermission.getPermission());
                throw new BizException("权限标识已锁定，不允许修改！!");
            }
            BeanUtils.copyPropertiesToPartField(sysPermission, po);
        } else {
            po = sysPermission;
            List<SysPermission> sysOrgans = this.findSysPermissionByPermission(sysPermission.getPermission());
            if (!CollectionUtils.isEmpty(sysOrgans)) {
                logger.error("权限标识[{}]已存在！", sysPermission.getPermission());
                throw new BizException("权限标识已存在！");
            }
        }
        return this.saveOrUpdate(po);
    }

    private List<SysPermission> findSysPermissionByPermission(String permission) {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setPermission(permission);
        return this.findSysPermission(sysPermission);
    }
}
