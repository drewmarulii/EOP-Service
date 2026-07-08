package com.eop.baseservice.common.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;

@Converter
public class JsonToMapConverter implements AttributeConverter<Map<String, Object>, JsonNode> {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public JsonNode convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return objectMapper.valueToTree(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting map to JSON", e);
        }
    }


    @Override
    public Map<String, Object> convertToEntityAttribute(JsonNode dbData) {
        try {
            return objectMapper.convertValue(
                    dbData,
                    new TypeReference<Map<String, Object>>() {}
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to map", e);
        }
    }
}