package com.ame.cache;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

public class AMECacheMethodMatcherPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor implements ClassFilter {

    AMECacheMethodMatcherPointcutAdvisor(CacheProcessor cacheProcessor) {
        super(new HandlerCacheAddInterceptor(cacheProcessor));
    }


    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return false;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return false;
    }
}
