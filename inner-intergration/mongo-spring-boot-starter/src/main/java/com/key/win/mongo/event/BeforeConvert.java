package com.key.win.mongo.event;

import cn.hutool.core.util.ReflectUtil;
import com.key.win.common.auth.*;
import com.key.win.basic.util.IndivdualSoldierAuthConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

public class BeforeConvert implements BeforeConvertCallback<Object> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object onBeforeConvert(Object o, String s) {
        logger.info("before convert callback");

        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(o.getClass());
        String userName = AuthenticationUtil.getUserName();
        Long userId = AuthenticationUtil.getUserId();
        Date now = new Date();

        if (fieldMap.containsKey(IndivdualSoldierAuthConstantUtils.MODEL_ID)) {
            Field id = fieldMap.get(IndivdualSoldierAuthConstantUtils.MODEL_ID);

            if (id == null || StringUtils.isBlank((String) ReflectUtil.getFieldValue(o, id))) {
                //没有id时为新增
                //创建时间
                if (fieldMap.containsKey(IndivdualSoldierAuthConstantUtils.MODEL_CREATE_DATE)) {
                    ReflectUtil.setFieldValue(o, IndivdualSoldierAuthConstantUtils.MODEL_CREATE_DATE, now);
                }
                //创建者
                if (fieldMap.containsKey(IndivdualSoldierAuthConstantUtils.MODEL_CREATE_USER_NAME) && StringUtils.isNotBlank(userName)) {
                    ReflectUtil.setFieldValue(o, IndivdualSoldierAuthConstantUtils.MODEL_CREATE_USER_NAME, userName);
                }
                if (fieldMap.containsKey(IndivdualSoldierAuthConstantUtils.MODEL_CREATE_USER_ID) && userId != null) {
                    ReflectUtil.setFieldValue(o, IndivdualSoldierAuthConstantUtils.MODEL_CREATE_USER_ID, userId);
                }
            }
        }
        //更新日期
        if (fieldMap.containsKey(IndivdualSoldierAuthConstantUtils.MODEL_UPDATE_DATE)) {
            ReflectUtil.setFieldValue(o, IndivdualSoldierAuthConstantUtils.MODEL_UPDATE_DATE, now);
        }
        //更新者
        if (fieldMap.containsKey(IndivdualSoldierAuthConstantUtils.MODEL_UPDATE_USER_NAME) && StringUtils.isNotBlank(userName)) {
            ReflectUtil.setFieldValue(o, IndivdualSoldierAuthConstantUtils.MODEL_UPDATE_USER_NAME, userName);
        }
        if (fieldMap.containsKey(IndivdualSoldierAuthConstantUtils.MODEL_UPDATE_USER_ID) && userId != null) {
            ReflectUtil.setFieldValue(o, IndivdualSoldierAuthConstantUtils.MODEL_UPDATE_USER_ID, userId);
        }
        return o;
    }
}
