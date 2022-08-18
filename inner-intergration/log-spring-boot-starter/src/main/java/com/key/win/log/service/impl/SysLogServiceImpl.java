package com.key.win.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.datasource.annotation.DataSource;
import com.key.win.log.dao.SysLogDao;
import com.key.win.log.model.SysLog;
import com.key.win.log.service.SysLogService;
import com.key.win.mybatis.page.MybatisPageServiceTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@DataSource(name = "log")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLog> implements SysLogService {

    @Autowired
    private SysLogDao logDao;

    @Async
    @Override
    public Boolean saveLog(SysLog log) {
        if (log.getFlag() == null) {
            log.setFlag(Boolean.TRUE);
        }
        return this.save(log);
    }

    @Async
    @Override
    public Boolean deleteLogById(String id) {
        return this.removeById(id);
    }

    @Override
    public PageResult<SysLog> findSysLogByPaged(PageRequest<SysLog> t) {
        MybatisPageServiceTemplate<SysLog, SysLog> mybatiesPageServiceTemplate = new MybatisPageServiceTemplate<SysLog, SysLog>(this.baseMapper) {
            @Override
            protected AbstractWrapper constructWrapper(SysLog sysLog) {
                LambdaQueryWrapper<SysLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                if (StringUtils.isNotBlank(sysLog.getSearchContent())) {
                    lambdaQueryWrapper
                            .like(SysLog::getModule, sysLog.getSearchContent())
                            .or()
                            .like(SysLog::getParams, sysLog.getSearchContent());
                }

                return lambdaQueryWrapper;
            }
        };

        return mybatiesPageServiceTemplate.doPagingQuery(t);
    }
}
