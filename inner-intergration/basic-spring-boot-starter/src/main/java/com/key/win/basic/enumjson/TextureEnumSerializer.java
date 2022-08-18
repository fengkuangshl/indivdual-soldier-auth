package com.key.win.basic.enumjson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TextureEnumSerializer extends JsonSerializer<Object> {
	protected static Logger	logger	= LoggerFactory.getLogger(TextureEnumSerializer.class);

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


		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		generator.writeEndObject();
	}
}