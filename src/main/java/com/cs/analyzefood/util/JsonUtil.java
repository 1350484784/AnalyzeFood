package com.cs.analyzefood.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonUtil {

    public static <T> T fromJson(InputStream is, Class<T> valueClass) {
        JavaType valueType = TypeFactory.defaultInstance().constructType(valueClass);
        return fromJson(is, valueType);
    }

    public static <T> T fromJson(String jsonStr, Class<T> valueClass) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(jsonStr.getBytes(StandardCharsets.UTF_8))) {
            return fromJson(is, valueClass);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(InputStream is, TypeReference<T> typeRef) {
        JavaType valueType = TypeFactory.defaultInstance().constructType(typeRef);
        return fromJson(is, valueType);
    }

    public static <T> T fromJson(String jsonStr, TypeReference<T> typeRef) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(jsonStr.getBytes(StandardCharsets.UTF_8))) {
            return fromJson(is, typeRef);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T fromJson(InputStream is, JavaType valueType) {
        ObjectMapper objectMapper = getMapperInstance();
        try {
            return objectMapper.readValue(is, valueType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void toJson(Object obj, OutputStream os) {
        ObjectMapper objectMapper = getMapperInstance();
        try {
            objectMapper.writeValue(os, obj);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object obj) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            toJson(obj, os);

            return new String(os.toByteArray(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    public static JsonNode getJson(String str) {
        try {
            return getMapperInstance().readTree(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper getMapperInstance() {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        return objectMapper;
    }
}

