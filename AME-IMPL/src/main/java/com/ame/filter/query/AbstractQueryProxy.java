package com.ame.filter.query;

import com.ame.spring.BeanManager;
import com.google.common.base.Strings;

import java.util.List;

/**
 * @author tam_tang
 * @date 2022/6/17 11:20
 */
public abstract class AbstractQueryProxy {

    protected static boolean SECOND_LEVEL_CACHE = false;
    protected static boolean QUERY_CACHE = false;

    static {
        String queryCacheProperty = BeanManager.getEnvironment().getProperty("meper.hibernate.properties.hibernate.cache.use_query_cache");
        QUERY_CACHE = !Strings.isNullOrEmpty(queryCacheProperty) && Boolean.parseBoolean(queryCacheProperty);
        String secondCacheProperty = BeanManager.getEnvironment().getProperty("meper.hibernate.properties.hibernate.cache.use_second_level_cache");
        SECOND_LEVEL_CACHE = !Strings.isNullOrEmpty(secondCacheProperty) && Boolean.parseBoolean(secondCacheProperty);
    }

    public abstract List list();

    public abstract Object getSingleResult();

    public abstract Integer executeUpdate();


}

