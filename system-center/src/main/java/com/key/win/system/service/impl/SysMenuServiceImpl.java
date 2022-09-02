package com.key.win.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.exception.BizException;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import com.key.win.basic.util.BeanUtils;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.system.dao.SysMenuDao;
import com.key.win.common.model.system.*;
import com.key.win.system.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<SysMenu> findOnes() {
        LambdaQueryWrapper<SysMenu> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysMenuLambdaQueryWrapper.eq(SysMenu::getIsMenu, 1);
        return this.list(sysMenuLambdaQueryWrapper);
    }

    @Override
    public List<SysMenu> findSysMenu(SysMenu sysMenu) {
        LambdaQueryWrapper<SysMenu> lambdaQueryWrapper = new LambdaQueryWrapper<SysMenu>();
        if (sysMenu != null) {
            if (StringUtils.isNotBlank(sysMenu.getName())) {
                lambdaQueryWrapper.eq(SysMenu::getName, sysMenu.getName());
            }
            if (StringUtils.isNotBlank(sysMenu.getCss())) {
                lambdaQueryWrapper.eq(SysMenu::getCss, sysMenu.getCss());
            }
            if (StringUtils.isNotBlank(sysMenu.getPath())) {
                lambdaQueryWrapper.eq(SysMenu::getPath, sysMenu.getPath());
            }
            if (StringUtils.isNotBlank(sysMenu.getUrl())) {
                lambdaQueryWrapper.eq(SysMenu::getUrl, sysMenu.getUrl());
            }
            if (StringUtils.isNotBlank(sysMenu.getTitle())) {
                lambdaQueryWrapper.eq(SysMenu::getTitle, sysMenu.getTitle());
            }
//            if (sysMenu.getIsHidden() != null) {
//                lambdaQueryWrapper.eq(SysMenu::getIsHidden, sysMenu.getIsHidden());
//            }
//            if (sysMenu.getIsMenu() != null) {
//                lambdaQueryWrapper.eq(SysMenu::getIsMenu, sysMenu.getIsMenu());
//            }
//            if (sysMenu.getSort() != null) {
//                lambdaQueryWrapper.eq(SysMenu::getSort, sysMenu.getSort());
//            }
            if (!CollectionUtils.isEmpty(sysMenu.getMenuIds())) {
                lambdaQueryWrapper.in(SysMenu::getId, sysMenu.getMenuIds());
            }
        }
        List<SysMenu> list = this.list(lambdaQueryWrapper);
        return list;
    }

    @Override
    public PageResult<SysMenu> findSysMenuByPaged(PageRequest<SysMenu> t) {

        MybatisPageServiceTemplate<SysMenu, SysMenu> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysMenu, SysMenu>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysMenu sysMenu) {
                LambdaQueryWrapper<SysMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                if (StringUtils.isNotBlank(sysMenu.getName())) {
                    lambdaQueryWrapper
                            .like(SysMenu::getName, sysMenu.getName())
                            .or()
                            .like(SysMenu::getPath, sysMenu.getName())
                            .or()
                            .like(SysMenu::getCss, sysMenu.getName())
                            .or()
                            .like(SysMenu::getTitle, sysMenu.getName())
                            .or()
                            .like(SysMenu::getUrl, sysMenu.getName());
                }

                return lambdaQueryWrapper;
            }
        };

        return mybatiesPageServiceTemplate.doPagingQuery(t);
    }


    @Override
    public boolean saveOrUpdateMenu(SysMenu sysMenu) {
        SysMenu po = null;
        if (sysMenu.getId() != null) {
            po = this.getById(sysMenu.getId());
            if (po == null) {
                logger.error("菜单不存在!");
                throw new BizException("菜单不存在!");
            }
            BeanUtils.copyPropertiesToPartField(sysMenu, po);
        } else {
            po = sysMenu;
        }
        return this.saveOrUpdate(sysMenu);
    }

    @Override
    public List<SysMenu> getMenuTree() {
        List<SysMenu> list = this.list();
        List<SysMenu> topTreeList = new ArrayList<>();
        Map<Long, SysMenu> levelAll = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SysMenu sysMenu : list) {
                if (sysMenu.getParentId().equals(IndividualSoldierAuthConstantUtils.TREE_PARENT_ID)) {
                    topTreeList.add(sysMenu);
                }
                levelAll.put(sysMenu.getId(), sysMenu);
            }
            for (Map.Entry<Long, SysMenu> entry : levelAll.entrySet()) {
                SysMenu value = entry.getValue();
                if (!value.getParentId().equals(IndividualSoldierAuthConstantUtils.TREE_PARENT_ID)) {
                    Long key = value.getParentId();
                    SysMenu parentMenu = levelAll.get(key);
                    parentMenu.addSubMenu(value);
                }
            }
        }
        return topTreeList;
    }

    public List<SysMenu> findSysMenuByParentId(Long parentId) {
        if (parentId == null) {
            logger.error("父节点Id[{}]为空", parentId);
            throw new BizException("父节点为Id空！");
        }
        LambdaQueryWrapper<SysMenu> lambdaQueryWrapper = new LambdaQueryWrapper<SysMenu>();
        lambdaQueryWrapper.eq(SysMenu::getParentId, parentId);
        return this.list(lambdaQueryWrapper);

    }

    @Override
    public boolean deleteById(Long id) {
        List<SysMenu> sysMenuByParentId = this.findSysMenuByParentId(id);
        if (!CollectionUtils.isEmpty(sysMenuByParentId)) {
            logger.error("删除Id[{}]的菜单时，发现有[{}]孩子节点", sysMenuByParentId.size());
            throw new BizException("请先删除孩子节点！");
        }
        return this.removeById(id);
    }

    @Override
    public List<SysMenu> findSysMenuByMenuIds(Set<Long> menuIds) {
        if(CollectionUtils.isEmpty(menuIds)){
            return new ArrayList<>();
        }
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuIds(menuIds);
        return this.findSysMenu(sysMenu);
    }
}
