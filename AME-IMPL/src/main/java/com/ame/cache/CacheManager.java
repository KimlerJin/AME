package com.ame.cache;

public interface CacheManager {

    /**
     * 根据名称查询或者创建对应的缓存对象
     *
     * @param name
     * @return
     */
    Cache getOrCreateCache(String name);

    /**
     * 根据名称查询或者创建对应的缓存对象(带过期时间)
     *
     * @param name
     * @param expireSeconds
     *            <0 标示没有过期时间
     * @return
     */
    Cache getOrCreateCache(String name, long expireSeconds);

    /**
     * 清空缓存下的所有记录
     *
     * @param name
     */
    void clearCache(String name);


    /**
     * 检查是否存在
     *
     * @param name
     */
    boolean checkExist(String name);

    void clearAll();
}
