package com.key.win.mongo.service.impl;

import com.key.win.basic.exception.BizException;
import com.key.win.mongo.model.MongoID;
import com.key.win.mongo.service.IMongoService;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @title mongodb服务接口类
 * @参数
 * @entity 实体对象
 * @collectionName 数据库中的集合名
 * @list 实体集合对象
 * @keys 键名数组
 * @values 键值数组
 * @fields 需要查询的列
 * @id id值
 */
public abstract class MongoServiceImpl<T> implements IMongoService<T> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected MongoTemplate mongoTemplate;

    protected Class<T> entityClass = currentModelClass();

    protected Class<T> currentModelClass() {
        return entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public boolean save(T entity) {
        try {
            mongoTemplate.save(entity);
            return true;
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            throw new BizException("保存失败！");
        }
    }

    @Override
    public boolean save(T entity, String collectionName) {
        try {
            mongoTemplate.save(entity, collectionName);
            return true;
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            throw new BizException("保存失败！");
        }
    }

    @Override
    public boolean saveList(List<T> list) {
        try {
            mongoTemplate.insertAll(list);
            return true;
        } catch (Exception e) {
            logger.error("操作集合失败:{}", e.getMessage(), e);
            throw new BizException("保存失败！");
        }
    }

    @Override
    public boolean saveList(List<T> list, String collectionName) {
        try {
            mongoTemplate.insert(list, collectionName);
            return true;
        } catch (Exception e) {
            logger.error("操作集合失败:{}", e.getMessage(), e);
            throw new BizException("保存失败！");
        }
    }

    @Override
    public boolean removeById(Serializable id) {
        //T byId = this.findById(id);
        try {
            T t = entityClass.newInstance();
            if (t instanceof MongoID) {
                MongoID idModel = (MongoID) t;
                idModel.setId((String) id);
            } else {
                t = this.findById(id);
            }
            return this.removeById(t);
        } catch (Exception e) {
            logger.error("删除失败:{}", e.getMessage(), e);
            throw new BizException("删除失败！");
        }
    }

    @Override
    public boolean removeById(T entity) {
        try {
            mongoTemplate.remove(entity);
            return true;
        } catch (Exception e) {
            logger.error("删除失败:{}", e.getMessage(), e);
            throw new BizException("删除失败！");
        }
    }

    @Override
    public boolean removeById(T entity, String collectionName) {
        try {
            mongoTemplate.remove(entity, collectionName);
            return true;
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            throw new BizException("删除失败！");
        }
    }


    @Override
    public boolean logicDeleted(Serializable id) {
        Query query = buildQueryById(id);
        Update update = new Update();
        update.set(IndividualSoldierAuthConstantUtils.MODEL_ENABLE_FLAG, Boolean.FALSE);
        try {
            mongoTemplate.updateFirst(query, update, entityClass);
            return true;
        } catch (Exception e) {
            logger.error("逻辑删除操作失败:{}", e.getMessage(), e);
            throw new BizException("删除失败！");
        }
    }

    @Override
    public boolean logicDeleted(T entity) {
        return this.logicDeleted(((MongoID) entity).getId());
    }

    @Override
    public boolean logicDeleted(Iterable<? extends T> entities) {
        List<Serializable> ids = new ArrayList<>();
        for (T entity : entities) {
            ids.add(((MongoID) entity).getId());
        }
        return this.logicDeletedByIds(ids);
    }

    @Override
    public boolean logicDeletedByIds(Iterable<? extends Serializable> ids) {
        Criteria criteria = Criteria.where(IndividualSoldierAuthConstantUtils.MODEL_ID_MONGO).in(ids);
        Query query = new Query(criteria);
        Update update = new Update();
        update.set(IndividualSoldierAuthConstantUtils.MODEL_ENABLE_FLAG, Boolean.FALSE);
        try {
            mongoTemplate.updateFirst(query, update, entityClass);
            return true;
        } catch (Exception e) {
            logger.error("逻辑删除操作失败:{}", e.getMessage(), e);
            throw new BizException("删除失败！");
        }
    }

    @Override
    public boolean updateById(Map<String, Object> map, Serializable id) {
        Query query = buildQueryById(id);
        Update update = buildUpdate(map);
        try {
            mongoTemplate.updateFirst(query, update, entityClass);
            return true;
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            throw new BizException("更新失败！");
        }
    }

    protected Update buildUpdate(Map<String, Object> map) {
        Update update = new Update();
        if (!CollectionUtils.isEmpty(map)) {
            map.forEach((key, value) -> {
                update.set(key, value);
            });
        }
        return update;
    }

    protected Query buildQueryById(Serializable id) {
        Criteria criteria = Criteria.where(IndividualSoldierAuthConstantUtils.MODEL_ID_MONGO).is(id);
        return new Query(criteria);
    }

    @Override
    public boolean updateById(Map<String, Object> map, Serializable id, String collectionName) {
        Query query = buildQueryById(id);
        Update update = buildUpdate(map);
        try {
            mongoTemplate.updateFirst(query, update, collectionName);
            return true;
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            throw new BizException("更新失败！");
        }
    }


    public boolean updateById(Map<String, Object> map, Serializable id, Class<?> clazz, String collectionName) {
        Query query = buildQueryById(id);
        Update update = buildUpdate(map);
        try {
            mongoTemplate.updateFirst(query, update, clazz, collectionName);
            return true;
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            throw new BizException("更新失败！");
        }
    }

    @Override
    public T findById(Serializable id) {
        try {
            Query query = buildQueryById(id);
            return mongoTemplate.findOne(query, entityClass);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public T findById(Serializable id, String collectionName) {

        try {
            Query query = buildQueryById(id);
            return mongoTemplate.findOne(query, entityClass, collectionName);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Object findById(Serializable id, String[] fields) {
        try {
            Query query = buildQueryAndField(fields, buildQueryById(id));
            return mongoTemplate.findOne(query, entityClass);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    protected Query buildQueryAndField(String[] fields, Query query) {
        setIncludeFiled(fields, query);
        return query;
    }

    @Override
    public Object findById(Serializable id, String[] fields, String collectionName) {
        try {
            Query query = buildQueryAndField(fields, buildQueryById(id));
            return mongoTemplate.findOne(query, entityClass, collectionName);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<T> findByMap(Map<String, Object> map) {
        try {
            Query query = buildQuery(map);
            return mongoTemplate.find(query, entityClass);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<T> findByMap(Map<String, Object> map, String collectionName) {
        try {
            Query query = buildQuery(map);
            return mongoTemplate.find(query, entityClass, collectionName);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<T> findByMap(Map<String, Object> map, String[] fields) {

        try {
            Criteria criteria = buildCriteria(map);
            Query query = buildQueryAndField(fields, Query.query(criteria));
            return mongoTemplate.find(query, entityClass);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<T> findByMap(Map<String, Object> map, String[] fields, String collectionName) {
        try {
            Criteria criteria = buildCriteria(map);
            Query query = buildQueryAndField(fields, Query.query(criteria));
            return mongoTemplate.find(query, entityClass, collectionName);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    protected Criteria buildCriteria(Map<String, Object> map) {
        Criteria criteria = Criteria.where(IndividualSoldierAuthConstantUtils.MODEL_ENABLE_FLAG).is(Boolean.TRUE);
        if (!CollectionUtils.isEmpty(map)) {
            for (String key : map.keySet()) {
                criteria.and(key).is(map.get(key));
            }
        }
        return criteria;
    }

    @Override
    public List<T> findAll() {
        try {
            Query query = buildQuery(null);
            return mongoTemplate.find(query, entityClass);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    protected Query buildQuery(Map<String, Object> o) {
        Criteria criteria = buildCriteria(o);
        return Query.query(criteria);
    }

    @Override
    public List<T> findAll(String collectionName) {
        try {
            return mongoTemplate.find(buildQuery(null), entityClass, collectionName);
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<T> findAll(String[] fields) {
        List<T> list;
        try {
            Query query = buildQuery(null);
            setIncludeFiled(fields, query);
            list = mongoTemplate.find(query, entityClass);
            return list;
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }

    private void setIncludeFiled(String[] fields, Query query) {
        Field field = query.fields();
        for (String fieldStr : fields) {
            field.include(fieldStr);
        }
    }

    @Override
    public List<T> findAll(String[] fields, String collectionName) {
        List<T> list;
        try {
            Query query = buildQuery(null);
            setIncludeFiled(fields, query);
            list = mongoTemplate.find(query, entityClass, collectionName);
            return list;
        } catch (Exception e) {
            logger.error("操作失败:{}", e.getMessage(), e);
            return null;
        }
    }
}
