package com.ame.cache;

import com.ame.annotation.AMECache;
import com.ame.annotation.CacheDelete;
import com.ame.entity.IData;
import com.ame.util.AnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class CacheProcessor {

    protected static final Logger log = LoggerFactory.getLogger(CacheProcessor.class);

    private final DefaultCachedExpressionEvaluator evaluator = new DefaultCachedExpressionEvaluator();
    private CacheManager cacheManager;

    public CacheProcessor(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    @SuppressWarnings("ConstantConditions")
    public Object processCacheAdd(Class<?> targetClass, Method method, Object target, Object[] args, String cacheName, Invocation invocation) throws Throwable {
        AMECache cacheAdd = AnnotationUtils.findAnnotation(method, AMECache.class);
        EvaluationContext evaluationContext =
                evaluator.createEvaluationContext(method, args, target, targetClass, null);
        String cacheKey =
                evaluator.key(cacheAdd.cacheKey(), new AnnotatedElementKey(method, targetClass), evaluationContext)
                        .toString();

        Cache cache = cacheManager.getOrCreateCache(cacheName);
        Object result = cache.get(cacheKey).orElse(null);
        if (result == null) {
            Cache cachePenetration = cacheManager.getOrCreateCache(AbstractCache.CACHE_PENETRATION, 20);
            if (cachePenetration.get(cacheName + cacheKey).isPresent()) {// 防止缓存穿透
                return null;
            }
            result = invocation.proceed();
            if (result == null) {// 防止缓存穿透
                cachePenetration.put(cacheName + cacheKey, "NULL");
            } else if (result instanceof IData) {
                String idCacheKey = CacheUtils.generateId(((IData) result).getId());
                if (idCacheKey.equals(cacheKey)) {
                    cache.put(idCacheKey, result);
                } else {
                    cache.put(idCacheKey, result, cacheKey);
                }
            } else if (result instanceof List || result instanceof Map) {
                // 如果缓存list或者map对象，那么将不会做list或者map内的关联对象检查，用户需要在外部自己维护集合内部有可能的部分对象更新问题。
                cache.put(cacheKey, result);
            }
        }
        return result;
    }

    @SuppressWarnings("ConstantConditions")
    public Object processCacheDelete(Class<?> targetClass, Method method, Object target, Object[] args, String cacheName, Invocation invocation) throws Throwable {
        CacheDelete cacheDelete = AnnotationUtils.findAnnotation(method, CacheDelete.class);
        EvaluationContext evaluationContext =
                evaluator.createEvaluationContext(method, args, target, targetClass, null);
        String cacheKey = evaluator
                .key(cacheDelete.key(), new AnnotatedElementKey(method, targetClass), evaluationContext)
                .toString();
        Cache cache = cacheManager.getOrCreateCache(cacheName);
        Object proceed = invocation.proceed();
        cache.evict(cacheKey);
        return proceed;

    }


    public interface Invocation {
        Object proceed() throws Throwable;
    }
}
