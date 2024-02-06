package com.ame.cache;

import com.ame.annotation.AMECache;
import com.ame.service.AbstractBaseEntityService;
import com.ame.util.AnnotationUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Method;

public class HandlerCacheAddInterceptor implements MethodInterceptor {

    private CacheProcessor cacheProcessor;

    public HandlerCacheAddInterceptor(CacheProcessor cacheProcessor) {
        this.cacheProcessor = cacheProcessor;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] args = invocation.getArguments();
        Object target = invocation.getThis();
        Method method = invocation.getMethod();
        Class<?> targetClass = target.getClass();
        Method annotatedMethod = AnnotationUtils.findAnnotatedMethod(method, targetClass, AMECache.class);
        Class<?> entityClass =
                ResolvableType.forClass(targetClass).as(AbstractBaseEntityService.class).resolveGeneric();
        return cacheProcessor.processCacheAdd(targetClass, annotatedMethod, target, args,
                CacheUtils.generateCacheName("", entityClass.getName()), invocation::proceed);

    }
}
