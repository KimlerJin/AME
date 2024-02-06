package com.ame.cache;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractCache implements Cache {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCache.class);

    private CacheManager cacheManager = null;
    public static final String CACHE_PENETRATION = "cache.penetration";


    @Override
    public List<Optional<Object>> list(Set<Object> keys) {
        List<Optional<Object>> returnList = Lists.newArrayList();
        List<CacheValueWrapper> cacheValueWrappers = doList(keys);

        if (cacheValueWrappers != null) {
            cacheValueWrappers.stream().forEach(wrapper -> {

                if (wrapper == null) {
                    returnList.add(Optional.empty());
                } else {
                    Object object = null;
                    try {
                        object = wrapper.getObject();
                        returnList.add(Optional.ofNullable(object));
                    } catch (Exception e) {
                        returnList.add(Optional.empty());
                    }
                }
            });

        }
        return returnList;
    }

    protected abstract List<CacheValueWrapper> doList(Set<Object> keys);

    @Override
    public Optional<Object> get(Object key) {
        return get(key, Object.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Object key, Class<T> type) {
        Object returnValue = null;
        CacheValueWrapper cacheObject = doGet(key);
        if (cacheObject != null) {
            try {
                returnValue = cacheObject.getObject();
            } catch (Exception e) {
                LOGGER.warn("Cache object can not be deserialized, key is {}", key);
                evict(key);
            }
        }
        return Optional.ofNullable((T) returnValue);
    }

    protected abstract CacheValueWrapper doGet(Object key);

    @Override
    public void put(Object key, Object value, String... aliasNames) {
        CacheValueWrapper newCacheObject = new CacheValueWrapper(value);

        CacheValueWrapper oldCacheObject = doGet(key);
        if (oldCacheObject != null && oldCacheObject.getReferenceKeys() != null) {
            newCacheObject.addReferenceKeys(oldCacheObject.getReferenceKeys());
        }
        for (String aliasName : aliasNames) {
            if (!key.equals(aliasName)) {
                doPut(aliasName, newCacheObject.cloneNoReference());
                newCacheObject.addReferenceKeys(aliasNames);
            }
        }
        doPut(key, newCacheObject);
    }

    protected abstract CacheValueWrapper doPut(Object key, CacheValueWrapper cacheValueWrapper);

    @Override
    public void evictAndPut(Object key, Object value, String... aliasNames) {
        evict(key);
        CacheValueWrapper newCacheObject = new CacheValueWrapper(value);

        for (String aliasName : aliasNames) {
            if (!key.equals(aliasName)) {
                doPut(aliasName, newCacheObject.cloneNoReference());
                newCacheObject.addReferenceKeys(aliasName);
            }
        }
        doPut(key, newCacheObject);
    }

    @Override
    public void evict(Object key) {
        CacheValueWrapper cacheObject = doGet(key);
        if (cacheObject != null) {
            doRemove(key);
            if (cacheObject.getReferenceKeys() != null) {
                for (String referenceKey : cacheObject.getReferenceKeys()) {
                    doRemove(referenceKey);
                }
            }
        }
        evictPenetrationCache(key);
    }

    protected abstract void doRemove(Object key);

    public void evictPenetrationCache(Object key) {
        if (cacheManager != null) {
            Cache cache = cacheManager.getOrCreateCache(CACHE_PENETRATION, 20);
            cache.clear();
        }
    }

    @Override
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
