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
import com.key.win.system.dao.SysRoleMenuDao;
import com.key.win.system.dao.SysRolePermissionDao;
import com.key.win.system.dao.SysUserRoleDao;
import com.key.win.common.model.system.*;
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
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysRolePermissionDao sysRolePermissionDao;

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
                logger.error("角色不存在!");
                throw new BizException("角色不存在!");
            }
            if (!po.getCode().equals(sysRole.getCode())) {
                logger.error("角色code[{}]已锁定，不允许修改！", sysRole.getCode());
                throw new BizException("角色code已锁定，不允许修改！!");
            }
            BeanUtils.copyPropertiesToPartField(sysRole, po);
        } else {
            po = sysRole;
            List<SysRole> sysRoles = this.findSysRoleByCode(sysRole.getCode());
            if (!CollectionUtils.isEmpty(sysRoles)) {
                logger.error("角色code[{}]已存在！", sysRole.getCode());
                throw new BizException("角色code已存在！");
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

    public SysRole getRoleFullById(Long id) {
        SysRole sysRole = this.getById(id);
        List<SysMenu> menuByRoleId = sysRoleMenuDao.findMenuByRoleId(id);
        List<SysMenuPermission> sysPermissionByRoleId = sysRolePermissionDao.findSysPermissionByRoleId(id);
        sysRole.setSysMenus(menuByRoleId);
        sysRole.setSysPermissions(sysPermissionByRoleId);
        return sysRole;
    }

    @Override
    public boolean deleteById(Long id) {
        SysRole roleFullById = this.getRoleFullById(id);
        if (!CollectionUtils.isEmpty(roleFullById.getSysMenus())) {
            logger.error("删除角色[{}]时，发现已关联SysMenu信息！", id);
            throw new BizException("请先解除关联的菜单信息！");
        }
        if (!CollectionUtils.isEmpty(roleFullById.getSysPermissions())) {
            logger.error("删除角色[{}]时，发现已关联SysPermission信息！", id);
            throw new BizException("请先解除关联的权限信息！");
        }
        return this.removeById(id);
    }
}
