package com.key.win.mongo.page;

import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.basic.web.OrderDir;
import com.key.win.basic.web.PageRequest;
import com.key.win.basic.web.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>  查询条件泛型
 * @param <RT> 分页返回值泛型
 */
public abstract class MongoQueryPageServiceTemplate<T, RT> {

    private final MongoTemplate mongoTemplate;

    private final Class<RT> entityClass;

    public MongoQueryPageServiceTemplate(MongoTemplate mongoTemplate) {
        entityClass = (Class<RT>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 分页查询模板类，
     *
     * @param pageParam 翻页、排序参数model
     * @return 泛型分页返回值，包含总记录数和当前页List数据
     */
    public PageResult<RT> doPagingQuery(PageRequest<T> pageParam) {
        PageResult<RT> pageResult = new PageResult<RT>();
        //排序方法
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();

        if (StringUtils.isNotBlank(pageParam.getSortName()) && pageParam.getSortDir() != null) {
            if (pageParam.getSortDir() == OrderDir.ASC) {
                orderList.add(new Sort.Order(Sort.Direction.ASC, pageParam.getSortName()));
            } else {
                orderList.add(new Sort.Order(Sort.Direction.DESC, pageParam.getSortName()));
            }
        } else {// 不指定排序，默认按id降序
            List<Sort.Order> sos = this.getDefaultQueryOrder(pageParam.getT());
            if (sos != null && sos.size() > 0) {
                orderList.addAll(sos);
            } else {
                orderList.add(new Sort.Order(Sort.Direction.DESC, IndividualSoldierAuthConstantUtils.QUERY_DEFAULT_ORDER_NAME));
            }
        }
        Sort sort = Sort.by(orderList);
        Criteria criteriaEnableFlag = Criteria.where(IndividualSoldierAuthConstantUtils.MODEL_ENABLE_FLAG).is(Boolean.TRUE);
        Criteria criteria = this.constructQuery(pageParam.getT());
        criteriaEnableFlag.andOperator(criteriaEnableFlag, criteria);
        Query query = Query.query(criteriaEnableFlag);
        long count = mongoTemplate.count(query, entityClass);
        query.with(sort);
        query.skip(pageParam.getFirstResult()).limit(pageParam.getPageSize());
        List<RT> result = mongoTemplate.find(query, entityClass);
        pageResult.setCount(count);
        pageResult.setPageNo(pageParam.getPageNo());
        pageResult.setPageSize(pageParam.getPageSize());
        pageResult.setData(result);
        return pageResult;
    }

    /**
     * 供子类重写，指定默认的Criteria排序；
     * 默认返回空，框架会按创建时间crtDttm降序
     */
    protected List<Sort.Order> getDefaultQueryOrder(T t) {
        return null;
    }

    /**
     * 供子类OverWrite，根据传入的查询条件构造
     *
     * @param t
     * @return
     */
    abstract protected Criteria constructQuery(T t);


}