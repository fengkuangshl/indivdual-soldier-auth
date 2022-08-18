package com.key.win.basic.enumjson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TextureEnumSerializerCode extends JsonSerializer<Object> {
    protected static Logger logger = LoggerFactory.getLogger(TextureEnumSerializerCode.class);

    @Override
    public void serialize(Object value, JsonGenerator generator, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        if (value == null) {
            return;
        }
        try {
            generator.writeStartObject();
            generator.writeFieldName("stringValue");
            generator.writeString(value.toString());
            generator.writeFieldName("text");
            generator.writeString(BeanUtils.getProperty(value, "text"));
            //新加数据格式化code
            generator.writeFieldName("code");
            generator.writeString(BeanUtils.getProperty(value, "code"));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        generator.writeEndObject();
    }
}