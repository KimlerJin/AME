package com.ame.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EhCache3Cache extends AbstractCache implements Cache {

    private final static Logger LOGGER = LoggerFactory.getLogger(EhCache3Cache.class);
    private org.ehcache.Cache<Object, CacheValueWrapper> cache;
    private String name;

    public EhCache3Cache(org.ehcache.Cache<Object, CacheValueWrapper> cache, String name) {
        this.cache = cache;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected List<CacheValueWrapper> doList(Set<Object> keys) {
        return keys.stream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public Optional<Object> get(Object key) {
        return get(key, Object.class);
    }


    @Override
    protected CacheValueWrapper doGet(Object key) {
        try {
            return cache.get(key);
        } catch (Exception ex) {
            LOGGER.warn("cache get exception, key:" + key, ex);
            return null;
        }
    }


    @Override
    protected CacheValueWrapper doPut(Object key, CacheValueWrapper cacheValueWrapper) {
        try {
            cache.put(key, cacheValueWrapper);
        } catch (Exception ex) {
            LOGGER.warn("cache put exception, key:" + key, ex);
        }
        return cacheValueWrapper;
    }


    @Override
    protected void doRemove(Object key) {
        try {
            cache.remove(key);
        } catch (Exception ex) {
            LOGGER.warn("cache remove exception, key:" + key, ex);
        }
    }

    @Override
    public void clear() {
        try {
            cache.clear();
        } catch (Exception ex) {
            LOGGER.warn("cache clear exception", ex);
        }
    }
}
