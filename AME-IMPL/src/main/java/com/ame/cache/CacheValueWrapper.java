package com.ame.cache;

import com.ame.util.SerializationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CacheValueWrapper implements Serializable {

    protected static final Logger log = LoggerFactory.getLogger(CacheValueWrapper.class);
    private static final long serialVersionUID = -4682260953449420028L;
    private byte[] bytes;
    private Set<String> referenceKeys = new HashSet<>();

    public CacheValueWrapper(Object object) {
        if (object != null) {
            bytes = SerializationUtil.jdkSerialize(new ValueHolder(object));
        }
    }

    private CacheValueWrapper(byte[] bytes) {
        this.bytes = bytes;
    }

    public Object getObject() {
        if (bytes == null) {
            return null;
        }
        return SerializationUtil.jdkDeserialize(bytes, ValueHolder.class).getObject();
    }

    public void addReferenceKeys(String... cacheKeys) {
        referenceKeys.addAll(Arrays.asList(cacheKeys));
    }

    public void addReferenceKeys(Set<String> cacheKeys) {
        referenceKeys.addAll(cacheKeys);
    }

    public Set<String> getReferenceKeys() {
        return referenceKeys;
    }

    public CacheValueWrapper cloneNoReference() {
        return new CacheValueWrapper(bytes);
    }


    static class ValueHolder implements Serializable {

        private static final long serialVersionUID = -9162773016863268641L;
        private Object object;

        public ValueHolder(Object object) {
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }
}
