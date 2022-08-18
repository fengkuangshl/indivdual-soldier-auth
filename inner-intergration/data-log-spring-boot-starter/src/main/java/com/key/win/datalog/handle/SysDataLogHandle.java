package com.key.win.datalog.handle;

import cn.hutool.core.collection.CollUtil;
import com.key.win.datalog.annotation.DataLog;
import com.key.win.datalog.model.SysDataLog;
import com.key.win.datalog.service.SysDataLogService;
import com.key.win.datalog.vo.DataLogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 数据日志处理
 * </p>
 */
public class SysDataLogHandle extends BaseDataLog {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysDataLogService dataLogService;

    @Override
    public void setting() {
        // 设置排除某张表、某些字段
        this.addExcludeTableName("createDate")
                .addExcludeFieldName("updateDate")
                .addExcludeFieldName("createUserId")
                .addExcludeFieldName("updateUserId")
                .addExcludeFieldName("createUserName")
                .addExcludeFieldName("updateUserName")
                .addExcludeFieldName("enableFlag");
    }

    @Override
    public boolean isIgnore(DataLog dataLog) {
        // 根据注解判断是否忽略某次操作
        return false;
    }

    @Override
    public void change(DataLog dataLog, DataLogVo data) {
        List<SysDataLog> sysDataLogs = data.getSysDataLogs();
        if (CollUtil.isEmpty(sysDataLogs)) {
            logger.info("不需要存库！");
        } else {
            // 存库
            dataLogService.saveBrachDataLog(data.getSysDataLogs());
            logger.info("存库成功{}条数据日志", sysDataLogs.size());
        }
    }

}
