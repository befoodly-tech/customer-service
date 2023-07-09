package com.befoodly.be.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@NoArgsConstructor
public class JacksonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.findAndRegisterModules();
    }

    public static String objectToString(Object o) {
        try {

            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException var2) {
            throw new RuntimeException("object to string conversion failed", var2);
        }
    }

    public static <T> T stringToObject(String s, Class<T> klazz) {
        try {
            return objectMapper.readValue(s, klazz);
        } catch (JsonProcessingException var3) {
            throw new RuntimeException("string to object conversion failed", var3);
        }
    }

    public static <T> List<T> stringToListObject(String s, Class<T> klazz) {
        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, klazz);
            return (List)objectMapper.readValue(s, listType);
        } catch (JsonProcessingException var4) {
            throw new RuntimeException("string to list object conversion failed", var4);
        }
    }

}
