package com.ame.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ame.ehcache3")
public class Ehcache3Properties extends Ehcache3Config {

}
