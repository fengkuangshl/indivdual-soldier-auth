package com.key.win.mongo.query;

import com.key.win.basic.util.IndivdualSoldierAuthConstantUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> 查询条件泛型
 */
public abstract class MongoServiceTemplate<T, RT> {


    private final MongoTemplate mongoTemplate;

    private final Class<RT> entityClass;

    public MongoServiceTemplate(MongoTemplate mongoTemplate) {
        entityClass = (Class<RT>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 分页查询模板类，
     *
     * @return 泛型分页返回值，包含总记录数和当前页List数据
     */
    public List<RT> doQuery(T t) {
        //排序方法
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        List<Sort.Order> sos = this.getDefaultQueryOrder(t);
        if (sos != null && sos.size() > 0) {
            orderList.addAll(sos);
        } else {
            orderList.add(new Sort.Order(Sort.Direction.DESC, IndivdualSoldierAuthConstantUtils.QUERY_DEFAULT_ORDER_NAME));
        }

        Sort sort = Sort.by(orderList);
        Criteria criteriaEnableFlag = Criteria.where(IndivdualSoldierAuthConstantUtils.MODEL_ENABLE_FLAG).is(Boolean.TRUE);
        Criteria criteria = this.constructQuery(t);
        criteriaEnableFlag.andOperator(criteria);
        Query query = Query.query(criteriaEnableFlag);
        query.with(sort);
        List<RT> list = mongoTemplate.find(query, entityClass);
        return list;
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