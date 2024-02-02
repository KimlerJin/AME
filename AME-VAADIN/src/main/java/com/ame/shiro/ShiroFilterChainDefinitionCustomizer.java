package com.ame.shiro;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;

@FunctionalInterface
public interface ShiroFilterChainDefinitionCustomizer {

    void config(DefaultShiroFilterChainDefinition shiroFilterChainDefinition);
}
