package com.ame.util;



import com.ame.core.exception.PlatformException;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper MAPPER = null;

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    static {
        MAPPER = new ObjectMapper();

        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.registerModule(new ParameterNamesModule());

        MAPPER.setTimeZone(TimeZone.getTimeZone("UTC"));

        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> String objectToJson(T t) {
        try {
            return MAPPER.writeValueAsString(t);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new PlatformException("Can not convert object to json!");
        }
    }


    public static <T> String objectToJson(T t, String... properties) {
        try {
            return MAPPER.writer((new SimpleFilterProvider()).addFilter(
                    AnnotationUtils.getValue(AnnotationUtils.findAnnotation(t.getClass(), JsonFilter.class)).toString(),
                    SimpleBeanPropertyFilter.filterOutAllExcept(properties))).writeValueAsString(t);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new PlatformException("Can not convert object to json!");
        }
    }

    public static <T> void objectToJson(OutputStream out, T t) {
        try {
            MAPPER.writeValue(out, t);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new PlatformException("Can not convert object to json!");
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }

        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new PlatformException("Can not convert json to object!");
        }
    }

    public static <T> T jsonToList(String json, Class<T> clazz) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{clazz});
            return MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new PlatformException("Can not convert json to object!");
        }
    }

    public static <T> T jsonToList(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new PlatformException("Can not convert json to object!");
        }
    }

    public static <T> String console(T t) {
        String json = "";
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new PlatformException("Can not convert json to object!");
        }
    }
}
