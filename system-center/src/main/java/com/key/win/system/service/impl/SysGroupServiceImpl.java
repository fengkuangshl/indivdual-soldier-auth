package com.key.win.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.exception.BizException;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.system.dao.SysGroupDao;
import com.key.win.system.dao.SysUserGroupDao;
import com.key.win.common.model.system.*;
import com.key.win.system.service.SysGroupService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class SysGroupServiceImpl extends ServiceImpl<SysGroupDao, SysGroup> implements SysGroupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserGroupDao sysUserGroupDao;

    @Override
    public List<SysGroup> findSysGroup(SysGroup sysGroup) {
        LambdaQueryWrapper<SysGroup> lambdaQueryWrapper = new LambdaQueryWrapper<SysGroup>();
        if (sysGroup != null) {
            if (StringUtils.isNotBlank(sysGroup.getCode())) {
                lambdaQueryWrapper.eq(SysGroup::getCode, sysGroup.getCode());
            }
            if (StringUtils.isNotBlank(sysGroup.getName())) {
                lambdaQueryWrapper.eq(SysGroup::getName, sysGroup.getName());
            }
            if (sysGroup.getParentId() != null) {
                lambdaQueryWrapper.eq(SysGroup::getParentId, sysGroup.getParentId());
            }
        }
        List<SysGroup> list = this.list(lambdaQueryWrapper);
        return list;
    }

    public List<SysGroup> findSysGroupByCode(String code) {
        SysGroup sysGroup = new SysGroup();
        sysGroup.setCode(code);
        return this.findSysGroup(sysGroup);
    }

    @Override
    public List<SysGroup> getGroupTree() {
        List<SysGroup> list = this.list();
        List<SysGroup> topList = new ArrayList<>();
        Map<Long, SysGroup> levelAll = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SysGroup organ : list) {
                if (organ.getParentId().equals(IndividualSoldierAuthConstantUtils.TREE_PARENT_ID)) {
                    topList.add(organ);
                }
                levelAll.put(organ.getId(), organ);
            }
            for (Map.Entry<Long, SysGroup> entry : levelAll.entrySet()) {
                SysGroup value = entry.getValue();
                if (!value.getParentId().equals(IndividualSoldierAuthConstantUtils.TREE_PARENT_ID)) {
                    Long key = value.getParentId();
                    SysGroup parentGroup = levelAll.get(key);
                    parentGroup.addSubGroup(value);
                }
            }
        }
        topList.sort(new Comparator<SysGroup>() {
            @Override
            public int compare(SysGroup o1, SysGroup o2) {
                if (o1.getCreateDate().getTime() > o2.getCreateDate().getTime()) {
                    return 1;
                } else if (o1.getCreateDate().getTime() < o2.getCreateDate().getTime()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return topList;
    }

    @Override
    public List<SysGroup> findSysGroupByParentId(Long parentId) {
        if (parentId == null) {
            log.error("parent Id is null !");
            throw new IllegalArgumentException("??????????????????");
        }
        SysGroup sysGroup = new SysGroup();
        sysGroup.setParentId(parentId);
        return this.findSysGroup(sysGroup);
    }

    @Override
    public PageResult<SysGroup> findSysGroupByPaged(PageRequest<SysGroup> t) {

        MybatisPageServiceTemplate<SysGroup, SysGroup> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysGroup, SysGroup>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysGroup sysGroup) {
                LambdaQueryWrapper<SysGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                if (StringUtils.isNotBlank(sysGroup.getName())) {
                    lambdaQueryWrapper.
                            like(SysGroup::getCode, sysGroup.getName())
                            .or()
                            .like(SysGroup::getName, sysGroup.getName());
                }
                lambdaQueryWrapper.orderByDesc(SysGroup::getName);
                return lambdaQueryWrapper;
            }
        };

        return mybatiesPageServiceTemplate.doPagingQuery(t);
    }

    @Override
    public List<SysGroup> findLeafNode() {
        return this.baseMapper.findLeafNode();
    }

    @Override
    public boolean saveOrUpdateGroup(SysGroup sysGroup) {
        SysGroup po = null;
        if (sysGroup.getId() != null) {
            po = this.getById(sysGroup.getId());
            if (po == null) {
                logger.error("???????????????");
                throw new BizException("????????????!");
            }
            if (!po.getCode().equals(sysGroup.getCode())) {
                logger.error("???code[{}]??????????????????????????????", sysGroup.getCode());
                throw new BizException("???code??????????????????????????????!");
            }
            po.setCode(sysGroup.getCode());
            po.setName(sysGroup.getName());
        } else {
            po = sysGroup;
            List<SysGroup> sysGroupByCode = this.findSysGroupByCode(sysGroup.getCode());
            if (!CollectionUtils.isEmpty(sysGroupByCode)) {
                logger.error("???code[{}]????????????", sysGroup.getCode());
                throw new BizException("???code????????????");
            }
        }
        boolean b = this.saveOrUpdate(sysGroup);
        if (sysGroup.getUserId() != null) {
            //Set<String> userIds = Arrays.asList(sysGroup.getUserId().split(",")).stream().collect(Collectors.toSet());
//            if (!CollectionUtils.isEmpty(userIds)) {
//                sysUserGroupDao.deleteUserGroup(null, sysGroup.getStringId());
//                sysUserGroupDao.saveBatchUserIdsAndGroupId(userIds, sysGroup.getStringId());
//                /*userIds.forEach(userId -> {
//                    SysUserGroup sysUserGroup = new SysUserGroup();
//                    sysUserGroup.setUserId(userId);
//                    sysUserGroup.setGroupId(sysGroup.getId());
//                    sysUserGroupDao.insert(sysUserGroup);
//                });*/
//            }
            if (!CollectionUtils.isEmpty(sysGroup.getUserIds())) {
                sysUserGroupDao.deleteUserGroup(null, sysGroup.getId());
                sysUserGroupDao.saveBatchUserIdsAndGroupId(sysGroup.getUserIds(), sysGroup.getId());

            }
        }
        return b;
    }

    @Override
    public List<SysGroup> findSysGroupByUserId(Long userId) {
        return sysUserGroupDao.findGroupByUserId(userId);
    }

    public SysGroup getSysGroupFullById(Long id) {
        SysGroup byId = this.getById(id);
        List<SysUser> userByGroupId = sysUserGroupDao.findUserByGroupId(id);
        byId.setSysUsers(userByGroupId);
        return byId;
    }

    @Override
    public boolean deleteById(Long id) {
        SysGroup sysGroupFullById = this.getSysGroupFullById(id);
        if (!CollectionUtils.isEmpty(sysGroupFullById.getSysUsers())) {
            logger.error("???????????????[{}]??????????????????????????????", id);
            throw new BizException("????????????????????????????????????");
        }
        List<SysGroup> sysGroupByParentId = findSysGroupByParentId(id);
        if (!CollectionUtils.isEmpty(sysGroupByParentId)) {
            logger.error("???????????????[{}]?????????????????????", id);
            throw new BizException("???????????????????????????");
        }
        return this.removeById(id);
    }
}
