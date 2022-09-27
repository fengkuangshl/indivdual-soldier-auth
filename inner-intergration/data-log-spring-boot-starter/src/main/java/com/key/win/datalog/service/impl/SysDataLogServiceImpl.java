package com.key.win.datalog.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.common.auth.detail.Authentication;
import com.key.win.datalog.dao.SysDataLogDao;
import com.key.win.datalog.model.SysDataLog;
import com.key.win.datalog.service.SysDataLogService;
import com.key.win.datasource.annotation.DataSource;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * DataLogServiceImpl
 * </p>
 */
@Service
public class SysDataLogServiceImpl extends ServiceImpl<SysDataLogDao, SysDataLog> implements SysDataLogService {

    @Autowired
    private SysDataLogDao dataLogDao;

    public SysDataLogServiceImpl() {
        super();
    }

    @Async
    @DataSource(name = "log")
    @Override
    public Boolean saveDataLog(SysDataLog sysDataLog) {
        return this.save(sysDataLog);
    }

    @Async
    @DataSource(name = "log")
    @Override
    public Boolean saveDataLog(String content, String fkId) {
        SysDataLog sysDataLog = new SysDataLog();
        sysDataLog.setFkId(fkId);
        sysDataLog.setContent(content);
        return this.save(sysDataLog);
    }


    @Async
    @DataSource(name = "log")
    @Override
    public Boolean saveBatchDataLog(List<SysDataLog> sysDataLogs) {
        return this.saveBatch(sysDataLogs);
    }

    @Async
    @DataSource(name = "log")
    @Override
    public Boolean deleteDataLogById(String id) {
        return this.removeById(id);
    }

    @Override
    public PageResult<SysDataLog> findSysLogByPaged(PageRequest<SysDataLog> pageRequest) {
        MybatisPageServiceTemplate<SysDataLog, SysDataLog> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysDataLog, SysDataLog>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysDataLog sysDataLog) {
                LambdaQueryWrapper<SysDataLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                if (StringUtils.isNotBlank(sysDataLog.getSearchContent())) {
                    lambdaQueryWrapper.like(SysDataLog::getContent, sysDataLog.getSearchContent());
                }
                if (StringUtils.isNotBlank(sysDataLog.getFkId())) {
                    lambdaQueryWrapper.eq(SysDataLog::getFkId, sysDataLog.getFkId());
                }

                return lambdaQueryWrapper;
            }
        };

        return mybatiesPageServiceTemplate.doPagingQuery(pageRequest);
    }
}
