package com.ame.cache;

import java.util.HashMap;
import java.util.Map;

public class Ehcache3Config {


    /**
     * 单个缓存本地heap大小(M)
     *
     * @return
     */
    private int cacheHeapSize = 1;

    /**
     * 单个缓存本地offHeap大小(M)
     *
     * @return
     */
    private int cacheOffHeapSize = 2;

    /**
     * 根据cache name定制
     *
     * @return
     */
    private Map<String, CacheConfig> cacheConfig = new HashMap<>();

    public int getCacheHeapSize() {
        return cacheHeapSize;
    }

    public void setCacheHeapSize(int cacheHeapSize) {
        this.cacheHeapSize = cacheHeapSize;
    }

    public int getCacheOffHeapSize() {
        return cacheOffHeapSize;
    }

    public void setCacheOffHeapSize(int cacheOffHeapSize) {
        this.cacheOffHeapSize = cacheOffHeapSize;
    }

    public Map<String, CacheConfig> getCacheConfig() {
        return cacheConfig;
    }

    public void setCacheConfig(Map<String, CacheConfig> cacheConfig) {
        this.cacheConfig = cacheConfig;
    }


    public static class Node {
        private String host;
        private int port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public static class CacheConfig {

        /**
         * 单个缓存本地heap大小(M)
         *
         * @return
         */
        private Integer cacheHeapSize;

        /**
         * 单个缓存本地offHeap大小(M)
         *
         * @return
         */
        private Integer cacheOffHeapSize;

        /**
         * 单个缓存失效时间（hours）
         *
         * @return
         */
        private Integer timeToLive;

        public Integer getCacheHeapSize() {
            return cacheHeapSize;
        }

        public void setCacheHeapSize(Integer cacheHeapSize) {
            this.cacheHeapSize = cacheHeapSize;
        }

        public Integer getCacheOffHeapSize() {
            return cacheOffHeapSize;
        }

        public void setCacheOffHeapSize(Integer cacheOffHeapSize) {
            this.cacheOffHeapSize = cacheOffHeapSize;
        }

        public Integer getTimeToLive() {
            return timeToLive;
        }

        public void setTimeToLive(Integer timeToLive) {
            this.timeToLive = timeToLive;
        }
    }

}
