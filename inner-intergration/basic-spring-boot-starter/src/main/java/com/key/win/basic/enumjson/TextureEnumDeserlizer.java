package com.key.win.basic.enumjson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;


public class TextureEnumDeserlizer extends JsonDeserializer<Object> implements ContextualDeserializer {

    private JsonEnum target;

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String enumName = node.get("stringValue").asText();
        return target.selectEnumByName(enumName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<Object> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        Class<Object> clazz = (Class<Object>) property.getType().getRawClass(); //getContextualType().getRawClass();
        target = (JsonEnum) clazz.getEnumConstants()[0];
        return this;
    }

}
