package com.ame.cache;

import com.ame.util.SerializationUtil;
import org.ehcache.spi.serialization.Serializer;
import org.ehcache.spi.serialization.SerializerException;

import java.nio.ByteBuffer;
import java.util.Objects;

public class CacheValueWrapperSerializer implements Serializer<CacheValueWrapper> {

    public CacheValueWrapperSerializer(ClassLoader classLoader) {
    }

    @Override
    public ByteBuffer serialize(CacheValueWrapper object) throws SerializerException {
        return ByteBuffer.wrap(SerializationUtil.serialize(object));
    }

    @Override
    public CacheValueWrapper read(ByteBuffer binary) throws ClassNotFoundException, SerializerException {
        byte[] bytes = new byte[binary.remaining()];
        binary.get(bytes, 0, bytes.length);
        return SerializationUtil.deserialize(bytes, CacheValueWrapper.class);
    }

    @Override
    public boolean equals(CacheValueWrapper object, ByteBuffer binary)
            throws ClassNotFoundException, SerializerException {
        return Objects.equals(object, read(binary));
    }
}
