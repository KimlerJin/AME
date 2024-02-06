package com.ame.cache;

import org.ehcache.impl.serialization.PlainJavaSerializer;

public class ObjectSerializer extends PlainJavaSerializer<Object> {


    public ObjectSerializer(ClassLoader classLoader) {
        super(classLoader);
    }
}
