package com.key.win.param.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.util.BeanUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.datasource.annotation.DataSource;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.key.win.param.dao.SysDictTypeDao;
import com.key.win.param.model.SysDictType;
import com.key.win.param.service.SysDictTypeService;
import com.key.win.param.utils.ParamUtils;

import java.util.List;

@Service
@DataSource(name = "business")
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeDao, SysDictType> implements SysDictTypeService {
    @Override
    public PageResult<SysDictType> getSysDictTypeByPaged(PageRequest<SysDictType> t) {
        MybatisPageServiceTemplate<SysDictType, SysDictType> mybatisPageServiceTemplate = new MybatisPageServiceTemplate<SysDictType, SysDictType>(super.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysDictType sysDictType) {
                return buildSysDictTypeLambdaQueryWrapper(sysDictType);
            }
        };
        return mybatisPageServiceTemplate.doPagingQuery(t);
    }

    @Override
    public List<SysDictType> findSysDictType(SysDictType sysDictType) {
        LambdaQueryWrapper<SysDictType> queryWrapper = buildSysDictTypeLambdaQueryWrapper(sysDictType);
        return super.list(queryWrapper);
    }

    private LambdaQueryWrapper<SysDictType> buildSysDictTypeLambdaQueryWrapper(SysDictType sysDictType) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        if (sysDictType != null) {
            if (StringUtils.isNotBlank(sysDictType.getCode())) {
                queryWrapper.eq(SysDictType::getCode, sysDictType.getCode());
            }
            if (StringUtils.isNotBlank(sysDictType.getName())) {
                queryWrapper.like(SysDictType::getName, sysDictType.getName());
            }
            if (sysDictType.getType() != null) {
                queryWrapper.like(SysDictType::getType, sysDictType.getType());
            }
        }
        return queryWrapper;
    }

    @Override
    @CacheEvict(cacheNames = ParamUtils.REDIS_SYS_DICT_TYPE_KEY_PREFIX, key = "#sysDictType.code")
    public boolean saveOrUpdateSysDictType(SysDictType sysDictType) {
        SysDictType po = null;
        if (sysDictType.getId() != null) {
            po = this.getSysDictTypeById(sysDictType.getId());
            BeanUtils.copyPropertiesToPartField(sysDictType, po);
        } else {
            po = sysDictType;
        }
        checkCode(sysDictType.getCode(), po);
        return super.saveOrUpdate(po);
    }

    private void checkCode(String code, SysDictType po) {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setCode(code);
        List<SysDictType> sysDictTypes = this.findSysDictType(sysDictType);
        if (!CollectionUtils.isEmpty(sysDictTypes)) {
            SysDictType sdt = sysDictTypes.get(0);
            if (po.getId() == null) {
                throw new BizException("code[" + code + "]对应的数据字典已经存在！");
            } else if (!po.getId().equals(sdt.getId())) {
                throw new BizException("code[" + code + "]对应的数据字典已经存在！");
            }
        }
    }

    @Override
    @CacheEvict(cacheNames = ParamUtils.REDIS_SYS_DICT_TYPE_KEY_PREFIX, allEntries = true, beforeInvocation = true)
    public boolean deleteSysDictType(Long id) {
        return super.removeById(id);
    }

    @Override
    public SysDictType getSysDictTypeById(Long id) {
        return super.getById(id);
    }

    @Override
    @Cacheable(cacheNames = ParamUtils.REDIS_SYS_DICT_TYPE_KEY_PREFIX, key = "#code")
    public SysDictType findSysDictTypeByCode(String code) {
        SysDictType sdt = new SysDictType();
        sdt.setCode(code);
        List<SysDictType> sysDictType = findSysDictType(sdt);
        if (!CollectionUtils.isEmpty(sysDictType)) {
            return sysDictType.get(0);
        }
        return null;
    }

    @Override
    @CacheEvict(cacheNames = ParamUtils.REDIS_SYS_DICT_TYPE_KEY_PREFIX, allEntries = true, beforeInvocation = true)
    public boolean updateEnabled(Long id, Boolean status) {
        if (id != null) {
            SysDictType byId = super.getById(id);
            if (byId != null) {
                byId.setStatus(status == null ? Boolean.FALSE : status);
                return updateById(byId);
            }
        }
        return false;
    }
}
