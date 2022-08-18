package com.key.win.mybatis.page;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.OrderBySegmentList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.key.win.basic.util.IndivdualSoldierAuthConstantUtils;
import com.key.win.basic.web.CodeEnum;
import com.key.win.basic.web.OrderDir;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.mybatis.mapper.KeyWinMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @param <T>  输入参数的泛型类型
 * @param <RT> 输出参数的泛型类型
 */
public abstract class MybatisPageServiceTemplate<T, RT> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BaseMapper baseMapper;

    /**
     * @param baseMapper
     */
    public MybatisPageServiceTemplate(BaseMapper baseMapper) {
        super();
        this.baseMapper = baseMapper;
    }

    /**
     * 分页查询模板类，
     *
     * @param pageParam 翻页、排序参数model
     * @return 泛型分页返回值，包含总记录数和当前页List数据
     */
    public PageResult<RT> doPagingQuery(PageRequest<T> pageParam) {

        //设备mybaties查询分页
        logger.info("进行分页参数组装");
        Page<RT> page = new Page<RT>();
        page.setCurrent(pageParam.getPageNo());
        page.setSize(pageParam.getPageSize());
        //构建Wrapper
        logger.info("分页查询条件构建");
        AbstractWrapper wrapper = this.constructWrapper(pageParam.getT());
        this.constructOrderByCondition(pageParam, wrapper);
        logger.info("执行分页查询");
        IPage<RT> pages = selectPage(page, wrapper, pageParam.getT());
        //设置分页返回参数
        logger.info("执行分页查询结果组装");
        PageResult<RT> pageResult = new PageResult<RT>();
        pageResult.setCount(pages.getTotal());
        pageResult.setPageNo(pageParam.getPageNo());
        pageResult.setPageSize(pageParam.getPageSize());
        pageResult.setData(page.getRecords());
        pageResult.setCode(CodeEnum.SUCCESS.getCode());
        logger.info("执行分页查询结果组装完成，返回PageResult");
        return pageResult;
    }

    /**
     * 构建原生sql
     *
     * @return
     */
    protected String constructNativeSql() {
        return null;
    }

    /**
     * 主要针对原生sql拼写过程需要传入参数
     * 输入参数为Map
     * 了类如需，重写即可
     *
     * @return
     */
    public Map constructNativeSqlArgsToMap(T t) {
        return new HashMap();
    }

    protected boolean isNativeSql() {
        if (StringUtils.isNotBlank(this.constructNativeSql())) {
            return true;
        }
        return false;
    }

    /**
     * 查询数据
     *
     * @param page
     * @param wrapper
     * @return
     */
    private IPage<RT> selectPage(Page<RT> page, AbstractWrapper wrapper, T t) {
        if (this.isNativeSql()) {
            return ((KeyWinMapper) baseMapper).selectPageForNativeSql(page, this.constructNativeSql(), constructNativeSqlArgsToMap(t), wrapper);
        } else {
            return baseMapper.selectPage(page, wrapper);
        }
    }

    /**
     * 构建排序规则
     * 找不到对应的表结构,就直接放弃排序
     *
     * @param pageParam
     * @param wrapper
     */
    private void constructOrderByCondition(PageRequest<T> pageParam, AbstractWrapper wrapper) {
        TableInfo tableInfo = getTableInfo(pageParam);
        if (tableInfo == null) {
            logger.warn("没有找到数据库表结构，不进行排序操作！");
            return;
        }
        appendSqlOrderByCondition(pageParam, wrapper, tableInfo);
    }

    /**
     * 构建sql排序
     * 如果能在映射表中找到字段，就进行排序。
     * 否则，就放弃排序
     *
     * @param pageParam
     * @param wrapper
     * @param tableInfo
     */
    private void appendSqlOrderByCondition(PageRequest<T> pageParam, AbstractWrapper wrapper, TableInfo tableInfo) {
        List<MybatisOderByVo> orderList = getOrderByCondition(pageParam);
        StringBuilder subSqlOrderBy = new StringBuilder();
        Map<String, TableFieldInfo> propertyToTableFieldInfoMap = tableInfo.getFieldList().stream().collect(Collectors.toMap(TableFieldInfo::getProperty, a -> a, (k1, k2) -> k1));
        for (int i = 0; i < orderList.size(); i++) {
            MybatisOderByVo ob = orderList.get(i);
            TableFieldInfo tableFieldInfo = propertyToTableFieldInfoMap.get(ob.getSortName());
            String column = tableFieldInfo == null ? null : tableFieldInfo.getColumn();
            if (StringUtils.isNotBlank(column)) {
                subSqlOrderBy.append(IndivdualSoldierAuthConstantUtils.SQL_SEPARATOR).append(column).append(IndivdualSoldierAuthConstantUtils.SQL_SEPARATOR).append(ob.getSortDir().name());
            } else {
                logger.warn("{}在propertyToTableFieldInfoMap中找不到映射字段！", ob.getSortName());
            }
            if (i < orderList.size() - 1 && subSqlOrderBy.length() > 0) {
                subSqlOrderBy.append(IndivdualSoldierAuthConstantUtils.SQL_COMMA_SEPARATOR);
            }
        }
        if (subSqlOrderBy.length() > 0) {
            StringBuilder sqlOrderBy = getSqlOrderBy(wrapper);
            sqlOrderBy.append(subSqlOrderBy);
            wrapper.last(sqlOrderBy.toString());
            logger.info("分页查询排序条件:{}", sqlOrderBy.toString());
        } else {
            logger.warn("最终在propertyToTableFieldInfoMap中找不到一个映射字段，本次分页查询将放弃排序！");
        }

    }

    /**
     * 检查执行sql中是否已经存在order by排序条件
     * 如果没有，则添加一个order by 条件到执行的sql中
     *
     * @param wrapper
     * @return
     */
    private StringBuilder getSqlOrderBy(AbstractWrapper wrapper) {
        OrderBySegmentList orderBySegmentList = wrapper.getExpression().getOrderBy();
        StringBuilder sqlOrderBy = new StringBuilder();
        if (CollectionUtils.isEmpty(orderBySegmentList)) {
            sqlOrderBy.append(IndivdualSoldierAuthConstantUtils.ORDER_BY);
            logger.info("执行sql中没有order by条件，将为此sql添加order by条件");
        } else {
            sqlOrderBy.append(IndivdualSoldierAuthConstantUtils.SQL_COMMA_SEPARATOR);
            logger.info("执行sql中已有order by条件，将直接为此sql添加具体排序语句。");
        }
        return sqlOrderBy;
    }

    /**
     * 获取用户的自定义的排序条件
     * 如果用户没有提供，系统将会默认按创建时间降序排列
     *
     * @param pageParam
     * @return
     */
    private List<MybatisOderByVo> getOrderByCondition(PageRequest<T> pageParam) {
        List<MybatisOderByVo> orderList = new ArrayList();
        List<MybatisOderByVo> queryOrder = this.getQueryOrder(pageParam);
        if (queryOrder != null && queryOrder.size() > 0) {
            orderList.addAll(queryOrder);
            logger.info("执行自定义分页排序条件");
        } else {
            if (StringUtils.isNotBlank(pageParam.getSortName())) {
                orderList.add(new MybatisOderByVo(pageParam.getSortName(), pageParam.getSortDir()));
                logger.info("执行用户传入的分页条件{}->{}", pageParam.getSortName(), pageParam.getSortDir());
            } else {
                if (!this.isNativeSql()) {
                    orderList.add(new MybatisOderByVo(IndivdualSoldierAuthConstantUtils.QUERY_DEFAULT_ORDER_NAME, OrderDir.DESC));
                    logger.info("执行默认分页排序条件");
                } else {
                    logger.info("原生sql,不添加默认分页排序条件");
                }

            }


        }
        return orderList;
    }

    /**
     * 获取参与排序的字段与数据字段映射关系
     * 如果找不到对应的表结果，则获取Mybatis中维护的表关系列表中的第一个表结构
     * 原因如下：
     * 这么做目地，是为解决用户在提交查询分页条件时，没有提交具体的分页条件实体对象，也就没有提交t对象。
     * 这种情况下系统将按照默认排序规则进行排序，但又缺少表结构映射信息，所以就取Mybatis中维护的表关系列表中的第一个表结构
     * 但这么做，也有可以还是找不到映射到表中的字段，这个情况就直接放弃排序
     *
     * @param pageParam
     * @return
     */
    private TableInfo getTableInfo(PageRequest<T> pageParam) {
        TableInfo tableInfo = null;
        if (pageParam.getT() != null) {
            tableInfo = TableInfoHelper.getTableInfo(pageParam.getT().getClass());
            logger.info("用户:[{}]查询分页时提交了对应的Model对象:{}", AuthenticationUtil.getUserName(), pageParam.getT().getClass());
        }
        if (tableInfo == null) {
            List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
            if (!CollectionUtils.isEmpty(tableInfos)){
                tableInfo = tableInfos.get(0);//只为获取公共字段
                logger.info("tableInfos的大小为{}个，获取第0个TableInfo的名称：{}", tableInfos.size(), tableInfo.getTableName());
            }
        }
        return tableInfo;
    }

    /**
     * 供子类OverWrite，根据传入的查询条件构造
     *
     * @param t
     * @return AbstractWrapper
     */
    abstract protected AbstractWrapper constructWrapper(T t);


    /**
     * 供子类重写，自定义排序规则；
     * 默认返回空，框架会按创建时间createDate降序
     */
    protected List<MybatisOderByVo> getQueryOrder(PageRequest<T> pageParam) {
        return null;
    }


}
