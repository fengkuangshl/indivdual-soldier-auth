package com.key.win.param.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.exception.BizException;
import com.key.win.basic.util.BeanUtils;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.datasource.annotation.DataSource;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import com.key.win.param.dao.SysDictDataDao;
import com.key.win.param.model.SysDictData;
import com.key.win.param.service.SysDictDataService;
import com.key.win.param.utils.ParamUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@DataSource(name = "param")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataDao, SysDictData> implements SysDictDataService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public PageResult<SysDictData> getSysDictDataByPaged(PageRequest<SysDictData> t) {
        MybatisPageServiceTemplate<SysDictData, SysDictData> mybatisPageServiceTemplate = new MybatisPageServiceTemplate<SysDictData, SysDictData>(super.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysDictData sysDictData) {
                return buildSysDictDataLambdaQueryWrapper(sysDictData);
            }
        };
        return mybatisPageServiceTemplate.doPagingQuery(t);
    }

    @Override
    public List<SysDictData> findSysDictData(SysDictData sysDictData) {
        LambdaQueryWrapper<SysDictData> queryWrapper = buildSysDictDataLambdaQueryWrapper(sysDictData);
        return super.list(queryWrapper);
    }

    private LambdaQueryWrapper<SysDictData> buildSysDictDataLambdaQueryWrapper(SysDictData sysDictData) {
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        if (sysDictData != null) {
            if (StringUtils.isNotBlank(sysDictData.getLabel())) {
                queryWrapper.eq(SysDictData::getLabel, sysDictData.getLabel());
            }
            if (StringUtils.isNotBlank(sysDictData.getValue())) {
                queryWrapper.like(SysDictData::getValue, sysDictData.getValue());
            }
            if (sysDictData.getType() != null) {
                queryWrapper.eq(SysDictData::getType, sysDictData.getType());
            } else {
                logger.error("数据字典类型信息不存在！");
                throw new BizException("数据字典类型信息不存在!");
            }
        }
        return queryWrapper;
    }

    @Override
    @CacheEvict(cacheNames = ParamUtils.REDIS_SYS_DICT_DATA_KEY_PREFIX, key = "#sysDictData.type")
    public boolean saveOrUpdateSysDictData(SysDictData sysDictData) {
        SysDictData po = null;
        if (sysDictData.getId() != null) {
            po = this.getSysDictDataById(sysDictData.getId());
            BeanUtils.copyPropertiesToPartField(sysDictData, po);
        } else {
            po = sysDictData;
        }
        checkValue(sysDictData, po);
        if (po.getIsDefault()) {
            UpdateWrapper<SysDictData> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("is_Default", Boolean.FALSE).eq("type", sysDictData.getType());
            super.update(null, updateWrapper);
        }
        return super.saveOrUpdate(po);
    }

    private void checkValue(SysDictData sysDictData, SysDictData po) {
        SysDictData sdd = new SysDictData();
        sdd.setValue(sdd.getValue());
        sdd.setType(sysDictData.getType());
        List<SysDictData> sysDictDatas = this.findSysDictData(sysDictData);
        if (!CollectionUtils.isEmpty(sysDictDatas)) {
            SysDictData sdt = sysDictDatas.get(0);
            if (po.getId() == null) {
                throw new BizException("value[" + sdd.getValue() + "]对应的数据字典已经存在！");
            } else if (!po.getId().equals(sdt.getId())) {
                throw new BizException("value[" + sdd.getValue() + "]对应的数据字典已经存在！");
            }
        }
    }

    @Override
    @CacheEvict(cacheNames = ParamUtils.REDIS_SYS_DICT_DATA_KEY_PREFIX, allEntries = true, beforeInvocation = true)
    public boolean deleteSysDictData(Long id) {
        return super.removeById(id);
    }

    @Override
    public SysDictData getSysDictDataById(Long id) {
        return super.getById(id);
    }

    @Override
    // sync:防止缓存并发，public synchronized <T> T get(Object key, Callable<T> valueLoader) {...}
    // @Cacheable(cacheNames = ParamUtils.REDIS_SYS_DICT_DATA_KEY_PREFIX, key = "#type",sync = true)
    @Cacheable(cacheNames = ParamUtils.REDIS_SYS_DICT_DATA_KEY_PREFIX, key = "#type")
    public List<SysDictData> findSysDictDataByType(Long type) {
        SysDictData sdd = new SysDictData();
        sdd.setType(type);
        return this.findSysDictData(sdd);
    }

    @Override
    @CacheEvict(cacheNames = ParamUtils.REDIS_SYS_DICT_DATA_KEY_PREFIX, allEntries = true, beforeInvocation = true)
    public boolean updateEnabled(Long id, Boolean status) {
        if (id != null) {
            SysDictData byId = super.getById(id);
            if (byId != null) {
                byId.setStatus(status == null ? Boolean.FALSE : status);
                return updateById(byId);
            }
        }
        return false;
    }
}
