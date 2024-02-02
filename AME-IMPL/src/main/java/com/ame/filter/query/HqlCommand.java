package com.ame.filter.query;

import com.ame.hibernate.HibernateTool;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HqlCommand extends AbstractCommandSupport {

    private boolean cacheable;


    private HqlCommand(HibernateTool hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @SuppressWarnings("rawtypes")
    private QueryImplProxy getQuery(Session session, boolean isCount) {
//        String exeHql = queryString;
//        if (isCount) {
//            StringBuilder sb = new StringBuilder();
//            int fromIndex = queryString.toUpperCase().indexOf("FROM");
//            queryString = queryString.substring(fromIndex + "FROM".length());
//            sb.append("SELECT COUNT(*) FROM ").append(queryString);
//            exeHql = sb.toString();
//        }
//        Query query = session.createQuery(exeHql);
//        initParams(query);
//        initPageInfo(query);
//        query.setCacheable(cacheable);
//        if (!cacheable) {
//            query.setCacheMode(CacheMode.IGNORE);
//        }
        return new QueryImplProxy(session, this, isCount);
    }

    @SuppressWarnings("rawtypes")
    public <T> List<T> list() {
        List execute = hibernateTemplate.execute(session -> getQuery(session, false).list());
        return execute;
    }

    @SuppressWarnings("unchecked")
    public <T> T getSingleResult() {
        T t = hibernateTemplate.execute(session -> {
            List<?> list = getQuery(session, false).list();
            return list.isEmpty() ? null : (T) list.get(0);
        });
        return t;
    }

    public int count() {
        return hibernateTemplate.execute(session -> ((Number) getQuery(session, true).getSingleResult())).intValue();
    }

    public int executeUpdate() {
        return hibernateTemplate.execute(session -> getQuery(session, false).executeUpdate());
    }

    public static class Builder {
        HqlCommand hqlCommand;

        public Builder(HibernateTool hibernateTool) {
            hqlCommand = new HqlCommand(hibernateTool);
        }

        public Builder setHql(String hql) {
            hqlCommand.queryString = hql;
            return this;
        }

        public Builder setStartPosition(int startPosition) {
            hqlCommand.startPosition = startPosition;
            return this;
        }

        public Builder setMaxCount(int maxCount) {
            hqlCommand.maxCount = maxCount;
            return this;
        }

        public Builder setCacheable(boolean cacheable) {
            hqlCommand.cacheable = cacheable;
            return this;
        }

        public Builder setParameters(List<Object> params) {
            hqlCommand.params = params;
            return this;
        }

        public Builder setParameters(Map<String, Object> paramPairs) {
            hqlCommand.paramPairs = paramPairs;
            return this;
        }

        public Builder setParameter(String key, Object value) {
            if (hqlCommand.paramPairs == null) {
                hqlCommand.paramPairs = new HashMap<>();
            }
            hqlCommand.paramPairs.put(key, value);
            return this;
        }

        public Builder setParameter(Object param) {
            if (hqlCommand.params == null) {
                hqlCommand.params = new ArrayList<>();
            }
            hqlCommand.params.add(param);
            return this;
        }

        public HqlCommand build() {
            return hqlCommand;
        }

    }

    public boolean isCacheable() {
        return cacheable;
    }
}