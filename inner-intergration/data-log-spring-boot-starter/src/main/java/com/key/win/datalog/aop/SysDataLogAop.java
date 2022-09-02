package com.key.win.datalog.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.basic.util.JsonUtils;
import com.key.win.common.model.basic.MybatisID;
import com.key.win.datalog.annotation.DataLog;
import com.key.win.datalog.handle.BaseDataLog;
import com.key.win.datalog.model.SysDataLog;
import com.key.win.datalog.util.SysDataLogUtil;
import com.key.win.datalog.vo.DataChangeVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;


@Aspect
@Order(99)
@AllArgsConstructor
public class SysDataLogAop {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //日志记录基础类
    private final BaseDataLog baseDataLog;

    /**
     * <p>
     * 初始化
     * 主要用来
     * 设置排除某张表、某些字段
     * </p>
     *
     * @return void
     * @author Tophua
     * @since 2020/10/30
     */
    @PostConstruct
    public void init() {
        baseDataLog.setting();
    }

    /**
     * <p>
     * 切面前执行
     * </p>
     *
     * @param dataLog dataLog
     */
    @Before("@annotation(dataLog)")
    public void before(JoinPoint joinPoint, DataLog dataLog) {
        // 使用 ThreadLocal 记录一次操作
        SysDataLogUtil.setDataChangeList(new LinkedList<>());
        SysDataLogUtil.setJoinPoint(joinPoint);
        SysDataLogUtil.setDataLog(dataLog);
        if (baseDataLog.isIgnore(dataLog)) {//是否需要此次操作
            SysDataLogUtil.setDataChangeList(null);
        }
    }

    /**
     * <p>
     * 切面后执行
     * </p>
     */
    @AfterReturning("@annotation(dataLog)")
    public void after(DataLog dataLog) {
        SqlCommandType sqlCommandType = SysDataLogUtil.getSqlCommandType();
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            this.insertChange();
        }
        if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
            this.updateChange();
        }
        if (SqlCommandType.DELETE.equals(sqlCommandType)) {
            this.deleteChange();
        }
        baseDataLog.transfer();
    }

    private void deleteChange() {
        List<SysDataLog> sysDataLogs = new ArrayList<>();
        List<DataChangeVo> list = SysDataLogUtil.getDataChangeList();//获取本次请求的变更信息
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(change -> {
            List<?> oldData = change.getOldData();
            if (CollUtil.isEmpty(oldData)) {
                return;
            }
            SysDataLogUtil.setChangeRecord(new ArrayList<>());
            for (int i = oldData.size() - 1; i >= 0; i--) {
                MybatisID idEntityOld = (MybatisID) oldData.get(i);
                StringBuilder sb = new StringBuilder();
                SysDataLog sysDataLog = new SysDataLog();
                sysDataLog.setFkId(idEntityOld.getId().toString());
                sb.append(StrUtil.format("修改表：[{}]", change.getTableName()));
                sb.append(StrUtil.format("id：[{}]", idEntityOld.getId()));
                sb.append(StrUtil.LF);
                sb.append("记录被彻底删除，不能恢复！");
                sysDataLog.setContent(sb.toString());
                sysDataLogs.add(sysDataLog);
                logger.info("对比结果:{}", sb.toString());

            }
        });
        mergeSysDataLog(list, sysDataLogs);
    }

    private void insertChange() {
        List<SysDataLog> sysDataLogs = new ArrayList<>();
        List<DataChangeVo> list = SysDataLogUtil.getDataChangeList();//获取本次请求的变更信息
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(change -> {
            List<?> newDatas = change.getNewData();
            if (CollUtil.isEmpty(newDatas)) {
                return;
            }
            for (int i = 0; i < newDatas.size(); i++) {
                Map<String, String> columnVaue = (Map<String, String>) newDatas.get(i);
                StringBuilder sb = new StringBuilder();
                SysDataLog sysDataLog = new SysDataLog();
                sysDataLog.setFkId(columnVaue.get(IndividualSoldierAuthConstantUtils.MODEL_ID));
                sb.append(StrUtil.format("新插入数据：[{}]", change.getTableName()));
                sb.append(StrUtil.LF);
                sb.append(JsonUtils.toJsonNoException(columnVaue));
                sysDataLog.setContent(sb.toString());
                sysDataLogs.add(sysDataLog);
                logger.info("对比结果:{}", sb.toString());
            }
        });
        mergeSysDataLog(list, sysDataLogs);
    }

    public void updateChange() {
        List<DataChangeVo> list = SysDataLogUtil.getDataChangeList();//获取本次请求的变更信息
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(change -> {
            List<?> oldData = change.getOldData();
            if (CollUtil.isEmpty(oldData)) {
                return;
            }
            List<String> ids = oldData.stream()
                    .map(o -> ReflectUtil.invoke(o, "getId").toString())
                    .filter(ObjectUtil::isNotNull)
                    .map(String::toString)
                    .collect(Collectors.toList());
            SqlSession sqlSession = change.getSqlSessionFactory().openSession();
            try {
                Map<String, Object> map = new HashMap<>(1);
                map.put(Constants.WRAPPER, Wrappers.query().in(IndividualSoldierAuthConstantUtils.MODEL_ID, ids));
                List<?> newData = sqlSession.selectList(change.getSqlStatement(), map);
                change.setNewData(Optional.ofNullable(newData).orElse(new ArrayList<>()));
            } finally {
                SqlSessionUtils.closeSqlSession(sqlSession, change.getSqlSessionFactory());
            }
            logger.info("oldData:" + JSONUtil.toJsonStr(change.getOldData()));
            logger.info("newData:" + JSONUtil.toJsonStr(change.getNewData()));
        });
        // 对比调模块
        this.compareAndTransfer(list);
    }

    /**
     * <p>
     * 对比保存
     * </p>
     */
    public void compareAndTransfer(List<DataChangeVo> list) {
        List<SysDataLog> sysDataLogs = new ArrayList<>();
        list.forEach(change -> {
            SysDataLogUtil.setChangeRecord(new ArrayList<>());
            List<?> oldData = change.getOldData();
            List<?> newData = change.getNewData();
            // 更新前后数据量不对必定是删除（逻辑删除)
            if (newData == null) {
                return;
            }
            if (oldData == null) {
                return;
            }

            // 按id排序
            oldData.sort(Comparator.comparing(d -> ReflectUtil.invoke(d, "getId").toString()));
            newData.sort(Comparator.comparing(d -> ReflectUtil.invoke(d, "getId").toString()));
            if (oldData.size() != newData.size()) {
                for (int i = oldData.size() - 1; i >= 0; i--) {
                    MybatisID idEntityOld = (MybatisID) oldData.get(i);
                    boolean isExist = false;
                    for (int j = 0; j < newData.size(); j++) {
                        MybatisID idEntityNew = (MybatisID) oldData.get(i);
                        if (idEntityOld.getId().equals(idEntityNew.getId())) {
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        StringBuilder sb = new StringBuilder();
                        SysDataLog sysDataLog = new SysDataLog();
                        sysDataLog.setFkId(idEntityOld.getId().toString());
                        sb.append(StrUtil.format("修改表：[{}]", change.getTableName()));
                        sb.append(StrUtil.format("id：[{}]", idEntityOld.getId()));
                        sb.append(StrUtil.LF);
                        sb.append("记录被删除！");
                        sysDataLog.setContent(sb.toString());
                        sysDataLogs.add(sysDataLog);
                        logger.info("对比结果:{}", sb.toString());
                        oldData.remove(idEntityOld);
                    }
                }
            }
            for (int i = 0; i < oldData.size(); i++) {
                final int[] finalI = {0};
                StringBuilder sb = new StringBuilder();
                StringBuilder rsb = new StringBuilder();
                StringBuilder newFkId = new StringBuilder();
                baseDataLog.sameClazzDiff(oldData.get(i), newData.get(i)).forEach(r -> {
                    String oldV = r.getOldValue() == null ? "无" : r.getOldValue().toString();
                    String newV = r.getNewValue() == null ? "无" : r.getNewValue().toString();
                    if (ObjectUtil.equal(oldV.trim(), newV.trim())) {
                        return;
                    }
                    if (finalI[0] == 0) {
                        sb.append(StrUtil.LF);
                        sb.append(StrUtil.format("修改表：[{}]", change.getTableName()));
                        sb.append(StrUtil.format("id：[{}]", r.getId()));
                        newFkId.append(r.getId());
                    }
                    sb.append(StrUtil.LF);
                    rsb.append(StrUtil.LF);
                    sb.append(StrUtil.format("把字段[{}]从[{}]改为[{}]", r.getFieldName(), r.getOldValue(), r.getNewValue()));
                    rsb.append(StrUtil.indexedFormat(baseDataLog.getLogFormat(), r.getId(), r.getFieldName(), r.getFieldComment(), oldV, newV));
                    finalI[0]++;
                });
                if (sb.length() > 0) {
                    sb.deleteCharAt(0);
                    rsb.deleteCharAt(0);
                    SysDataLog sysDataLog = new SysDataLog();
                    sysDataLog.setFkId(newFkId.toString());
                    sysDataLog.setContent(sb.toString());
                    sysDataLogs.add(sysDataLog);
                }
                logger.info("对比结果:{}", sb.toString());
                logger.info("对比结果:{}", rsb.toString());
            }
        });
        mergeSysDataLog(list, sysDataLogs);
    }

    private void mergeSysDataLog(List<DataChangeVo> list, List<SysDataLog> sysDataLogs) {
        String fkId = SysDataLogUtil.getFkId();
        if (StringUtils.isNotBlank(fkId)) {
            StringBuilder mergeRecordStr = new StringBuilder();
            sysDataLogs.forEach(sysDataLog -> {
                mergeRecordStr.append(sysDataLog.getContent()).append(";");
            });
            SysDataLog sysDataLog = new SysDataLog();
            sysDataLog.setFkId(fkId);
            sysDataLog.setContent(mergeRecordStr.toString());
            SysDataLogUtil.setChangeRecord(new ArrayList<>());
            SysDataLogUtil.addChangeRecord(sysDataLog);
        } else {
            SysDataLogUtil.setChangeRecord(sysDataLogs);
        }
        SysDataLogUtil.setDataChangeList(list);
    }

}
