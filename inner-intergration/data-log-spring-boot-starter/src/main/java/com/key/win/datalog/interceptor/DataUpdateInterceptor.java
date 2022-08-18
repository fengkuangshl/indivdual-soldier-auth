package com.key.win.datalog.interceptor;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.key.win.datalog.annotation.IgnoreDataLog;
import com.key.win.datalog.handle.BaseDataLog;
import com.key.win.datalog.util.SysDataLogUtil;
import com.key.win.datalog.vo.DataChangeVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 数据更新拦截器
 * </p>
 */
@AllArgsConstructor
@Intercepts({
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
})
public class DataUpdateInterceptor extends AbstractSqlParserHandler implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @SneakyThrows
    public Object intercept(Invocation invocation) {
        // 判断是否需要记录日志
        if (SysDataLogUtil.getDataChangeList() == null) {
            return invocation.proceed();
        }
        Statement statement;
        Object firstArg = invocation.getArgs()[0];

        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            statement = (Statement) stmtMetaObj.getValue("stmt.statement");
        } catch (Exception e) {
            // do nothing
        }
        if (stmtMetaObj.hasGetter("delegate")) {
            //Hikari
            try {
                statement = (Statement) stmtMetaObj.getValue("delegate");
            } catch (Exception ignored) {

            }
        }

        String originalSql = statement.toString();
        originalSql = originalSql.replaceAll("[\\s]+", StringPool.SPACE);
        int index = indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }
        logger.info("执行SQL：" + originalSql);

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 获取执行Sql
        String sql = originalSql.replace("where", "WHERE");

        // 使用mybatis-plus 工具解析sql获取表名
        Collection<String> tables = new TableNameParser(sql).tables();
        if (CollectionUtils.isEmpty(tables)) {
            return invocation.proceed();
        }
        String tableName = tables.iterator().next();
        // 排除表名判断
        if (BaseDataLog.getExcludeTableNames().contains(tableName)) {
            return invocation.proceed();
        }
        TableInfo tableInfo = null;
        for (TableInfo t : TableInfoHelper.getTableInfos()) {
            if (t.getTableName().equals(tableName)) {
                tableInfo = t;
                break;
            }
        }
//            // 使用mybatis-plus 工具根据表名找出对应的实体类
//            TableInfo tableInfo = Optional.ofNullable(TableInfoHelper.getTableInfo(Class.forName(tableName)))
//                    .orElse(new TableInfo());
        Class<?> entityType = tableInfo.getEntityType();

        if (entityType == null || entityType.isAnnotationPresent(IgnoreDataLog.class)) {
            return invocation.proceed();
        }
        DataChangeVo change = new DataChangeVo();
        change.setTableName(tableName);
        change.setEntityType(entityType);
        SysDataLogUtil.setSqlCommandType(mappedStatement.getSqlCommandType());
        SysDataLogUtil.addDataChange(change);
        // 插入
        if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
            String insertSql = sql.toUpperCase().replaceAll(" ", "").replaceAll("'", "").trim();
            if (insertSql.contains("(ID,") || insertSql.contains(",ID,")) {
                logger.info("包含Id,进行Id分隔！");
                String[] isa = insertSql.split("VALUES");
                String insertTop = isa[0];
                String patterInsertTop = "(?i)\\(\\s*[^\\(]+\\)";
                Pattern r = Pattern.compile(patterInsertTop);
                Matcher m = r.matcher(insertTop);
                String[] columns = {};
                while (m.find()) {
                    String group = m.group();
                    String express = group.substring(1, group.length() - 1);
                    logger.info("columns:{}", express);
                    columns = express.split(",");
                }
                List<Map<String, String>> valueList = new ArrayList<>();
                String[] values = isa[1].split("\\),\\(");
                for (int i = 0; i < values.length; i++) {
                    String v = values[i];
                    if (i == 0) {
                        v = v.substring(1);
                    }
                    if (i == values.length - 1) {
                        v = v.substring(0, v.length() - 1);
                    }
                    String[] columnValues = v.split(",");
                    Map<String, String> columnMap = new LinkedHashMap<>();
                    for (int j = 0; j < columnValues.length; j++) {
                        String columnValue = columnValues[j];
                        String column = columns[j];
                        columnMap.put(column, columnValue);
                    }
                    valueList.add(columnMap);
                    change.setNewData(valueList);
                }
            } else {
                logger.error("Id不存在,不做任何处理！");
            }
        }
        // 更新或者删除
        if (SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType()) || SqlCommandType.DELETE.equals(mappedStatement.getSqlCommandType())) {
            // 设置sql用于执行完后查询新数据
            String selectSql = "AND " + sql.substring(sql.lastIndexOf("WHERE") + 5);
            // 同表对同条数据操作多次只进行一次对比
            if (SysDataLogUtil.getDataChangeList().stream().anyMatch(c -> tableName.equals(c.getTableName())
                    && selectSql.equals(c.getWhereSql()))) {
                return invocation.proceed();
            }
            change.setWhereSql(selectSql);
            Map<String, Object> map = new HashMap<>(1);
            map.put(Constants.WRAPPER, Wrappers.query().eq("1", 1).last(selectSql));
            // 查询更新前数据
            SqlSessionFactory sqlSessionFactory = GlobalConfigUtils.currentSessionFactory(entityType);
            change.setSqlSessionFactory(sqlSessionFactory);
            change.setSqlStatement(tableInfo.getSqlStatement(SqlMethod.SELECT_LIST.getMethod()));
            SqlSession sqlSession = sqlSessionFactory.openSession();
            try {
                List<?> oldData = sqlSession.selectList(change.getSqlStatement(), map);
                change.setOldData(Optional.ofNullable(oldData).orElse(new ArrayList<>()));
            } finally {
                SqlSessionUtils.closeSqlSession(sqlSession, sqlSessionFactory);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 获取sql语句开头部分
     *
     * @param sql ignore
     * @return ignore
     */
    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Comparator.naturalOrder());
        return list.get(0);
    }

    public static void main(String[] args) {
        String ss = "INSERT INTO " +
                "sys_organ_device " +
                "(id,create_date,create_user_id,create_user_name,enable_flag,update_date,update_user_id,update_user_name,version,organ_id,device_id) " +
                "VALUES " +
                "('${@com.key.win.base.util.DefaultIdentifierGeneratorUtils@getGeneratorLongId()}',current_timestamp(),'${@com.key.win.base.auth.AuthenticationUtil@getUserId()}','${@com.key.win.base.auth.AuthenticationUtil@getUserName()}',1,null,null,null,0,#{organId},#{deviceId})," +
                "('${@com.key.win.base.util.DefaultIdentifierGeneratorUtils@getGeneratorLongId()}',current_timestamp(),'${@com.key.win.base.auth.AuthenticationUtil@getUserId()}','${@com.key.win.base.auth.AuthenticationUtil@getUserName()}',1,null,null,null,0,#{organId},#{deviceId})," +
                "('${@com.key.win.base.util.DefaultIdentifierGeneratorUtils@getGeneratorLongId()}',current_timestamp(),'${@com.key.win.base.auth.AuthenticationUtil@getUserId()}','${@com.key.win.base.auth.AuthenticationUtil@getUserName()}',1,null,null,null,0,#{organId},#{deviceId})," +
                "('${@com.key.win.base.util.DefaultIdentifierGeneratorUtils@getGeneratorLongId()}',current_timestamp(),'${@com.key.win.base.auth.AuthenticationUtil@getUserId()}','${@com.key.win.base.auth.AuthenticationUtil@getUserName()}',1,null,null,null,0,#{organId},#{deviceId})," +
                "('${@com.key.win.base.util.DefaultIdentifierGeneratorUtils@getGeneratorLongId()}',current_timestamp(),'${@com.key.win.base.auth.AuthenticationUtil@getUserId()}','${@com.key.win.base.auth.AuthenticationUtil@getUserName()}',1,null,null,null,0,#{organId},#{deviceId})";
        ss = ss.toUpperCase().replaceAll(" ", "").trim();
        if (ss.contains("(ID,") || ss.contains(",ID,")) {
            System.out.println("包含Id");
        }
        String[] value = ss.split("VALUES");
        String insertDML = value[0];
        String patternDML = "(?i)\\(\\s*[^\\(]+\\)";
        Pattern r = Pattern.compile(patternDML);
        Matcher m = r.matcher(insertDML);
        String[] columns = {};
        while (m.find()) {
            String group = m.group();
            String express = group.substring(1, group.length() - 1);
            System.out.println(express);
            columns = express.split(",");
        }
        List<Map<String, String>> valueList = new ArrayList<>();
        String[] values = value[1].split("\\),\\(");
        for (int i = 0; i < values.length; i++) {
            String v = values[i];
            if (i == 0) {
                v = v.substring(1);
            }
            if (i == values.length - 1) {
                v = v.substring(0, v.length() - 1);
            }
            String[] columnValues = v.split(",");
            Map<String, String> columnMap = new LinkedHashMap<>();
            for (int j = 0; j < columnValues.length; j++) {
                String columnValue = columnValues[j];
                String column = columns[j];
                columnMap.put(column, columnValue);
            }
            valueList.add(columnMap);
        }
        System.out.println(values);
    }

}
