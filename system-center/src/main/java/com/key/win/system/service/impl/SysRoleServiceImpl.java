package com.key.win.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.exception.BizException;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import com.key.win.basic.util.BeanUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.system.dao.SysRoleDao;
import com.key.win.system.dao.SysUserRoleDao;
import com.key.win.common.model.system.*;
import com.key.win.system.service.SysRoleMenuPermissionService;
import com.key.win.system.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysRoleMenuPermissionService sysRoleMenuPermissionService;

    @Override
    public List<SysRole> findSysRole(SysRole sysRole) {
        LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<SysRole>();
        if (sysRole != null) {
            if (StringUtils.isNotBlank(sysRole.getName())) {
                lambdaQueryWrapper.eq(SysRole::getName, sysRole.getName());
            }
            if (StringUtils.isNotBlank(sysRole.getCode())) {
                lambdaQueryWrapper.eq(SysRole::getCode, sysRole.getCode());
            }
        }
        List<SysRole> list = this.list(lambdaQueryWrapper);
        return list;
    }

    @Override
    public PageResult<SysRole> findSysRoleByPaged(PageRequest<SysRole> t) {

        MybatisPageServiceTemplate<SysRole, SysRole> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysRole, SysRole>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysRole sysRole) {
                LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                if (StringUtils.isNotBlank(sysRole.getName())) {
                    lambdaQueryWrapper
                            .like(SysRole::getName, sysRole.getName())
                            .or()
                            .like(SysRole::getCode, sysRole.getName());
                }

                return lambdaQueryWrapper;
            }
        };

        return mybatiesPageServiceTemplate.doPagingQuery(t);
    }


    @Override
    public boolean saveOrUpdateRole(SysRole sysRole) {
        SysRole po = null;
        if (sysRole.getId() != null) {
            po = this.getById(sysRole.getId());
            if (po == null) {
                logger.error("???????????????!");
                throw new BizException("???????????????!");
            }
            if (!po.getCode().equals(sysRole.getCode())) {
                logger.error("??????code[{}]??????????????????????????????", sysRole.getCode());
                throw new BizException("??????code??????????????????????????????!");
            }
            BeanUtils.copyPropertiesToPartField(sysRole, po);
        } else {
            po = sysRole;
            List<SysRole> sysRoles = this.findSysRoleByCode(sysRole.getCode());
            if (!CollectionUtils.isEmpty(sysRoles)) {
                logger.error("??????code[{}]????????????", sysRole.getCode());
                throw new BizException("??????code????????????");
            }
        }
        boolean b = this.saveOrUpdate(sysRole);
        if (sysRole.getUserId() != null) {
//            Set<String> userIds = Arrays.asList(sysRole.getUserId().split(",")).stream().collect(Collectors.toSet());
//            if (!CollectionUtils.isEmpty(userIds)) {
//                sysUserRoleDao.deleteUserRole(null, sysRole.getId());
//                sysUserRoleDao.saveBatchUserIdsAndRoleId(userIds, sysRole.getId());
//                /*userIds.forEach(userId -> {
//                    SysUserRole sysUserRole = new SysUserRole();
//                    sysUserRole.setRoleId(sysRole.getId());
//                    sysUserRole.setUserId(userId);
//                    sysUserRoleDao.insert(sysUserRole);
//                });*/
//            }
            if (!CollectionUtils.isEmpty(sysRole.getUserIds())) {
                sysUserRoleDao.deleteUserRole(null, sysRole.getId());
                sysUserRoleDao.saveBatchUserIdsAndRoleId(sysRole.getUserIds(), sysRole.getId());
            }
        }
        return b;
    }

    private List<SysRole> findSysRoleByCode(String code) {
        SysRole sysRole = new SysRole();
        sysRole.setCode(code);
        return this.findSysRole(sysRole);
    }

    @Override
    public boolean deleteById(Long id) {
        List<SysRoleMenuPermission> grantMenus = sysRoleMenuPermissionService.findGrantMenus(id);
        if (!CollectionUtils.isEmpty(grantMenus)) {
            logger.error("????????????[{}]?????????????????????SysMenu?????????", id);
            throw new BizException("????????????????????????????????????");
        }
        List<SysRoleMenuPermission> grantMenuPermissions = sysRoleMenuPermissionService.findGrantMenuPermissions(id);
        if (!CollectionUtils.isEmpty(grantMenuPermissions)) {
            logger.error("????????????[{}]?????????????????????SysPermission?????????", id);
            throw new BizException("????????????????????????????????????");
        }
        return this.removeById(id);
    }
}
