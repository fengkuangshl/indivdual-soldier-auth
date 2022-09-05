package com.key.win.datalog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.datalog.model.SysDataLog;

import java.util.List;

/**
 * <p>
 * DataLogService
 * </p>
 */
public interface SysDataLogService extends IService<SysDataLog> {

    Boolean saveDataLog(SysDataLog sysDataLog);

    Boolean saveDataLog(String content, String fkId);

    Boolean saveBrachDataLog(List<SysDataLog> sysDataLogs);

    Boolean deleteDataLogById(String id);

    PageResult<SysDataLog> findSysLogByPaged(PageRequest<SysDataLog> pageRequest);

}
