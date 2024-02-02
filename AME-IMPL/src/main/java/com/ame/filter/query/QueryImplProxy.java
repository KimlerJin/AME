package com.ame.filter.query;

import com.ame.core.exception.PlatformException;
import com.google.common.collect.Lists;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.Query;
import org.springframework.util.TypeUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tam_tang
 * @date 2022/6/17 11:20
 */
public class QueryImplProxy extends AbstractQueryProxy {

    private HqlCommand hqlCommand;

    private Query query;


    public QueryImplProxy(Session session, HqlCommand hqlCommand, boolean isCount) {
        String queryString = hqlCommand.getQueryString();
        String exeHql = queryString;
        if (isCount) {
            StringBuilder sb = new StringBuilder();
            int fromIndex = queryString.toUpperCase().indexOf("FROM");
            queryString = queryString.substring(fromIndex + "FROM".length());
            sb.append("SELECT COUNT(*) FROM ").append(queryString);
            exeHql = sb.toString();
        }
        Query query = session.createQuery(exeHql);
        hqlCommand.initParams(query);
        hqlCommand.initPageInfo(query);
        boolean cacheable = hqlCommand.isCacheable();
        query.setCacheable(cacheable);
        if (!cacheable) {
            query.setCacheMode(CacheMode.IGNORE);
        }
        this.hqlCommand = hqlCommand;
        this.query = query;
    }

    @Override
    public List list() {
        boolean cacheable = query.isCacheable();
        query.setCacheable(false);
            return query.list();


    }



    @Override
    public Object getSingleResult() {
        List list = this.list();
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PlatformException("query did not return a unique result:" + list.size());
        }
        return list.get(0);
    }

    @Override
    public Integer executeUpdate() {
        return query.executeUpdate();
    }






    private String generateCacheKey() {

        List<Object> toSortList = Lists.newArrayList();

        List<Object> params = hqlCommand.getParams();
        if (params != null) {
            params.forEach(obj -> {
                if (TypeUtils.isAssignable(Collection.class, obj.getClass())) {
                    toSortList.addAll((Collection) obj);
                } else if (TypeUtils.isAssignable(Object[].class, obj.getClass())) {
                    toSortList.addAll(Lists.newArrayList((Object[]) obj));
                } else {
                    toSortList.add(obj);
                }
            });
        }
        Map<String, Object> paramPairs = hqlCommand.getParamPairs();
        if (paramPairs != null) {
            paramPairs.values().forEach(obj -> {
                if (TypeUtils.isAssignable(Collection.class, obj.getClass())) {
                    toSortList.addAll((Collection) obj);
                } else if (TypeUtils.isAssignable(Object[].class, obj.getClass())) {
                    toSortList.addAll(Lists.newArrayList((Object[]) obj));
                } else {
                    toSortList.add(obj);
                }
            });
        }

        if (toSortList.isEmpty()) {
            return "EMPTY_PARAM";
        }

        toSortList.sort(Comparator.comparing(Object::toString));
        return toSortList.stream().map(Object::toString).collect(Collectors.joining("#"));
    }


}

