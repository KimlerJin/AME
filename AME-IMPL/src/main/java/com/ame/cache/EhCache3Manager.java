package com.ame.cache;

import com.google.common.collect.Sets;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EhCache3Manager implements CacheManager {

    private Map<String, CacheConfiguration<Object, CacheValueWrapper>> cacheConcurrentHashMap = new ConcurrentHashMap<>();

    private org.ehcache.CacheManager cacheManager;
    private CacheConfiguration<Object, CacheValueWrapper> defaultCacheConfiguration;
    private Ehcache3Config ehcache3Config;
    private Set<String> cacheNameSet = Sets.newHashSet();

    public EhCache3Manager(org.ehcache.CacheManager cacheManager,
                           CacheConfiguration<Object, CacheValueWrapper> defaultCacheConfiguration,
                           Ehcache3Config ehcache3Config) {
        this.cacheManager = cacheManager;
        this.defaultCacheConfiguration = defaultCacheConfiguration;
        this.ehcache3Config = ehcache3Config;
    }

    @Override
    public Cache getOrCreateCache(String name) {
        return getOrCreateCache(name, getCacheConfigurationByName(name));
    }

    private Cache getOrCreateCache(String name, Object config) {
        String cacheName = CacheUtils.generateInternalCacheName(name);
        org.ehcache.Cache<Object, CacheValueWrapper> ehcache3Cache =
                cacheManager.getCache(cacheName, Object.class, CacheValueWrapper.class);
        if (ehcache3Cache == null) {
            synchronized (this) {
                ehcache3Cache = cacheManager.getCache(cacheName, Object.class, CacheValueWrapper.class);
                if (ehcache3Cache == null) {
                    ehcache3Cache =
                            cacheManager.createCache(cacheName,
                                    (CacheConfiguration<Object, CacheValueWrapper>) config);
                    cacheNameSet.add(name);
                }
            }
        }
        Cache cache = new EhCache3Cache(ehcache3Cache, name);
        ((EhCache3Cache) cache).setCacheManager(this);
        return cache;
    }

    @Override
    public Cache getOrCreateCache(String name, long expireSeconds) {
        if (expireSeconds < 0) {
            return getOrCreateCache(name);
        }
        CacheConfiguration<Object,
                CacheValueWrapper> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class, CacheValueWrapper.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(ehcache3Config.getCacheHeapSize(), MemoryUnit.MB)
                                .offheap(ehcache3Config.getCacheOffHeapSize(), MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(expireSeconds))).build();
        return getOrCreateCache(name, cacheConfiguration);
    }

    @Override
    public void clearCache(String name) {
        org.ehcache.Cache<Object, CacheValueWrapper> ehcache3Cache =
                cacheManager.getCache(CacheUtils.generateInternalCacheName(name), Object.class, CacheValueWrapper.class);
        if (ehcache3Cache != null) {
            ehcache3Cache.clear();
        }
    }


    @Override
    public boolean checkExist(String name) {
        org.ehcache.Cache<Object, CacheValueWrapper> ehcache3Cache =
                cacheManager.getCache(name, Object.class, CacheValueWrapper.class);
        return ehcache3Cache != null;
    }

    @Override
    public void clearAll() {
        //todo
        cacheNameSet.forEach(this::clearCache);

    }


    /**
     * 根据cache name获取定制的配置属性
     *
     * @param cacheName
     * @return
     */
    private CacheConfiguration<Object, CacheValueWrapper> getCacheConfigurationByName(String cacheName) {
        int index = cacheName.indexOf(CacheUtils.SEPARATOR_HASHTAG);
        if (index > 0) {
            cacheName = cacheName.substring(index + 1);
        }
        //找Map是否存在
        CacheConfiguration<Object, CacheValueWrapper> cacheConfiguration = null;
        cacheConfiguration = cacheConcurrentHashMap.get(cacheName);
        if (cacheConfiguration != null) {
            return cacheConfiguration;
        }

        //是否定制， 否则返回默认
        Ehcache3Config.CacheConfig configBean = ehcache3Config.getCacheConfig().get(cacheName);
        if (configBean == null) {
            return defaultCacheConfiguration;
        }
        Integer heapSize = configBean.getCacheHeapSize();
        Integer offHeapSize = configBean.getCacheOffHeapSize();
        Integer timeToLive = configBean.getTimeToLive();
        if (heapSize == null || offHeapSize == null || timeToLive == null) {
            return defaultCacheConfiguration;
        }

        //返回定制， 放入Map
        cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class, CacheValueWrapper.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(heapSize, MemoryUnit.MB).offheap(offHeapSize, MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(timeToLive))).build();
        cacheConcurrentHashMap.put(cacheName, cacheConfiguration);
        return cacheConfiguration;
    }
}
