package com.key.win.mongo.service;

import java.io.Serializable;
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
public interface IMongoService<T> {

    /**
     * 增
     */
    boolean save(T entity);

    boolean save(T entity, String collectionName);

    boolean saveList(List<T> list);

    boolean saveList(List<T> list, String collectionName);

    /**
     * 删
     */
    boolean removeById(Serializable id) ;

    boolean removeById(T entity);

    boolean removeById(T entity, String collectionName);


    boolean logicDeleted(Serializable id);
    boolean logicDeleted(T entity);
    boolean logicDeleted(Iterable<? extends T> entities);
    boolean logicDeletedByIds(Iterable<? extends Serializable> ids);

    /**
     * 改,都是通过id
     *
     * @param map 要修改的键名
     *            相当于mysql中  update key1,key2,key3 set value1,value2,value3 where id=#{id} from collectionName
     */
    boolean updateById(Map<String, Object> map, Serializable id);

    boolean updateById(Map<String, Object> map, Serializable id, String collectionName);

    /**
     * 查
     *
     * @finById 就是根据id查询单条记录
     * @findByMap 相当于条件查询, 查询出符合条件的所有内容
     * @findAll 查询表中所有记录
     * @fields 表示需要查询的字段---参考mysql中 select key1,key2,key3 from collectionName
     */
    Object findById(Serializable id);

    Object findById(Serializable id, String collectionName);

    Object findById(Serializable id, String[] fields);

    Object findById(Serializable id, String[] fields, String collectionName);

    List<T> findByMap(Map<String, Object> map);

    List<T> findByMap(Map<String, Object> map, String collectionName);

    List<T> findByMap(Map<String, Object> map, String[] fields);

    List<T> findByMap(Map<String, Object> map, String[] fields, String collectionName);

    List<T> findAll();

    List<T> findAll(String collectionName);

    List<T> findAll(String[] fields);

    List<T> findAll(String[] fields, String collectionName);
}

