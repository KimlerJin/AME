package com.ame.filter.query;

import com.ame.hibernate.HibernateTool;
import org.hibernate.query.Query;
import org.springframework.util.TypeUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract class AbstractCommandSupport {

    String queryString;
    List<Object> params;
    Map<String, Object> paramPairs;
    int startPosition;
    int maxCount;
    HibernateTool hibernateTemplate;

    @SuppressWarnings({"rawtypes", "deprecation"})
   public void initParams(Query query) {
        if (paramPairs != null && !paramPairs.isEmpty()) {
            Set<Map.Entry<String, Object>> entrySet = paramPairs.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                if (entry.getValue() != null && TypeUtils.isAssignable(Collection.class,entry.getValue().getClass())) {
                    query.setParameterList(entry.getKey(), (Collection)entry.getValue());
                } else if (entry.getValue() != null
                    && TypeUtils.isAssignable( Object[].class,entry.getValue().getClass())) {
                    query.setParameterList(entry.getKey(), (Object[])entry.getValue());
                } else {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
        } else if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                Object value = params.get(i);
                if (value != null && TypeUtils.isAssignable(Collection.class,value.getClass() )) {
                    query.setParameterList(i + 1, (Collection)value);
                } else if (value != null && TypeUtils.isAssignable( Object[].class,value.getClass())) {
                    query.setParameterList(i + 1, (Object[])value);
                } else {
                    query.setParameter(i + 1, value);
                }

            }
        }
    }

    @SuppressWarnings("rawtypes")
    public  void initPageInfo(Query query) {
        if (maxCount > 0 && startPosition >= 0) {
            query.setMaxResults(maxCount);
            query.setFirstResult(startPosition);
        }
    }

    public String getQueryString() {
        return queryString;
    }

    public List<Object> getParams() {
        return params;
    }

    public Map<String, Object> getParamPairs() {
        return paramPairs;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public HibernateTool getHibernateTemplate() {
        return hibernateTemplate;
    }
}
