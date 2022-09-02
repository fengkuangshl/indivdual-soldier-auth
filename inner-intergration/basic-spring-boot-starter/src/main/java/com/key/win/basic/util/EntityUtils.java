package com.key.win.base.util;

import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.basic.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class EntityUtils {

    private static Logger logger = LoggerFactory.getLogger(EntityUtils.class);

    /**
     * @param object
     * @return
     * @description: 实体类转Map
     */
    public static Map<String, Object> entityToMap(Object object) {
        return JsonUtils.toObjectNoException(JsonUtils.toJsonNoException(object), Map.class);
    }

    public static Map<String, Object> entityToMap_IgnoreCommonFieldAndPropertyIsNull(Object object) {
        Map<String, Object> map = JsonUtils.toObjectNoException(JsonUtils.toJsonNoException(object), Map.class);
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (key.equals(IndividualSoldierAuthConstantUtils.MODEL_ID) ||
                    key.equals(IndividualSoldierAuthConstantUtils.MODEL_CREATE_DATE) ||
                    key.equals(IndividualSoldierAuthConstantUtils.MODEL_CREATE_USER_ID) ||
                    key.equals(IndividualSoldierAuthConstantUtils.MODEL_CREATE_USER_NAME) ||
                    key.equals(IndividualSoldierAuthConstantUtils.MODEL_UPDATE_DATE) ||
                    key.equals(IndividualSoldierAuthConstantUtils.MODEL_UPDATE_USER_ID) ||
                    key.equals(IndividualSoldierAuthConstantUtils.MODEL_UPDATE_USER_NAME) ||
                    value == null) {
                iterator.remove();
            }
        }
        return map;
    }

    /**
     * @param <T>
     * @param map    需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity 需要转化成的实体类
     * @return
     * @description: Map转实体类
     */
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;
        try {
            t = entity.newInstance();
            for (Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object != null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (Exception e) {
            logger.error("Map转实体出错:{}", e.getMessage(), e);
        }
        return t;
    }
}