package com.ame.cache;

public class CacheUtils {

    public static String CACHE_NAME_PREFIX = "lf:";
    public static String SEPARATOR_HASHTAG = "#";
    public static String DEFAULT_PREFIX = "T#";

    public static String generateId(long id) {
        return "id=" + id;
    }

    public static String generateCacheName(String groupName, String cacheName) {
        return groupName + SEPARATOR_HASHTAG +DEFAULT_PREFIX +  cacheName;
    }

    public static String generateInternalCacheName(String name) {
        if (name.startsWith(CACHE_NAME_PREFIX)) {
            return name;
        }
        return CACHE_NAME_PREFIX + name;
    }

}
