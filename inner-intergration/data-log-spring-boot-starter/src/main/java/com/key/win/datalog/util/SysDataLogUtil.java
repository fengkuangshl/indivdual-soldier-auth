package com.key.win.datalog.util;

import com.key.win.datalog.annotation.DataLog;
import com.key.win.datalog.model.SysDataLog;
import com.key.win.datalog.vo.CompareResultVo;
import com.key.win.datalog.vo.DataChangeVo;
import org.apache.ibatis.mapping.SqlCommandType;
import org.aspectj.lang.JoinPoint;

import java.util.ArrayList;
import java.util.List;

public class SysDataLogUtil {

    /**
     * 注解
     */
    private static final ThreadLocal<DataLog> dataLogThreadLocal = new ThreadLocal<>();

    /**
     * 切点
     */
    private static final ThreadLocal<JoinPoint> joinPointThreadLocal = new ThreadLocal<>();

    /**
     * 数据变化 多张表-多条数据
     */
    private static final ThreadLocal<List<DataChangeVo>> dataChangeThreadLocal = new ThreadLocal<>();

    /**
     * 全部变化对比结果
     */
    private static final ThreadLocal<List<CompareResultVo>> compareResultThreadLocal = ThreadLocal.withInitial(ArrayList::new);


    /**
     * 全部变化记录 默认：将[{}]由{}修改为{}
     */
    private static final ThreadLocal<List<SysDataLog>> changeRecordThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<SqlCommandType> sqlCommandTypeThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> fkIdThreadLocal = new ThreadLocal<>();

    public static String getFkId() {
        return fkIdThreadLocal.get();
    }

    public static void setFkId(String fkId) {
        fkIdThreadLocal.set(fkId);
    }

    public static void removeFkId() {
        fkIdThreadLocal.remove();
    }

    public static List<SysDataLog> getChangeRecord() {
        return changeRecordThreadLocal.get();
    }

    public static void setChangeRecord(List<SysDataLog> changeRecords) {
        changeRecordThreadLocal.set(changeRecords);
    }

    public static void addChangeRecord(SysDataLog changeRecord) {
        changeRecordThreadLocal.get().add(changeRecord);
    }

    public static void addChangeRecord(List<SysDataLog> changeRecords) {
        changeRecordThreadLocal.get().addAll(changeRecords);
    }

    public static void removeChangeRecord() {
        changeRecordThreadLocal.remove();
    }


    public static SqlCommandType getSqlCommandType() {
        return sqlCommandTypeThreadLocal.get();
    }

    public static void setSqlCommandType(SqlCommandType sqlCommandType) {
        sqlCommandTypeThreadLocal.set(sqlCommandType);
    }

    public static void removeSqlCommandType() {
        sqlCommandTypeThreadLocal.remove();
    }


    public static DataLog getDataLog() {
        return dataLogThreadLocal.get();
    }

    public static void setDataLog(DataLog dataLog) {
        dataLogThreadLocal.set(dataLog);
    }

    public static void removeDataLog() {
        dataLogThreadLocal.remove();
    }


    public static JoinPoint getJoinPoint() {
        return joinPointThreadLocal.get();
    }

    public static void setJoinPoint(JoinPoint joinPoint) {
        joinPointThreadLocal.set(joinPoint);
    }

    public static void removeJoinPoint() {
        joinPointThreadLocal.remove();
    }


    public static List<DataChangeVo> getDataChangeList() {
        return dataChangeThreadLocal.get();
    }

    public static void setDataChangeList(List<DataChangeVo> dataChangeList) {
        dataChangeThreadLocal.set(dataChangeList);
    }

    public static void addDataChange(DataChangeVo dataChange) {
        dataChangeThreadLocal.get().add(dataChange);
    }

    public static void addDataChange(List<DataChangeVo> dataChanges) {
        dataChangeThreadLocal.get().addAll(dataChanges);
    }

    public static void removeDataChange() {
        dataChangeThreadLocal.remove();
    }


    public static List<CompareResultVo> getCompareResultList() {
        return compareResultThreadLocal.get();
    }

    public static void setCompareResultList(List<CompareResultVo> compareResultList) {
        compareResultThreadLocal.set(compareResultList);
    }

    public static void addCompareResult(CompareResultVo compareResult) {
        compareResultThreadLocal.get().add(compareResult);
    }

    public static void addCompareResult(List<CompareResultVo> compareResults) {
        compareResultThreadLocal.get().addAll(compareResults);
    }

    public static void removeCompareResult() {
        compareResultThreadLocal.remove();
    }


}
