package com.key.win.datalog.vo;

import com.key.win.datalog.model.SysDataLog;
import lombok.Data;

import java.util.List;

@Data
public class DataLogVo {
    /**
     * 数据变化 多张表-多条数据
     */
    private List<DataChangeVo> dataChanges;
    /**
     * 全部变化对比结果
     */
    private List<CompareResultVo> compareResults;
    /**
     * 全部变化记录 默认：将[{}]由{}修改为{}
     */
    private List<SysDataLog> sysDataLogs;
}
