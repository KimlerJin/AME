package com.ame.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ame.cache")
public class CacheProperties {

    /**
     * 单个缓存失效时间（hours）,默认24
     *
     * @return
     */
    private int timeToLive = 24;

    private String provider = "ehcache3";

    private String prefix;

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
