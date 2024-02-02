package com.ame.filter.query;

import com.ame.hibernate.HibernateTool;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.NativeQuery;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlCommand extends AbstractCommandSupport {

    private List<Class<?>> affectedEntityClasss;

    private SqlCommand(HibernateTool hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @SuppressWarnings("rawtypes")
    private NativeQuery getNativeQuery(Session session, boolean isCount) {
        String exeSql = queryString;
        if (isCount) {
            SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) hibernateTemplate.getSessionFactory();
            JdbcServices jdbcServices = sessionFactory.getJdbcServices();
            //处理sql server不能在子查询中使用 order by 等语句,拼接上 TOP 100 PERCENT
            if (jdbcServices.getJdbcEnvironment().getDialect().toString().contains("SQLServer")) {
                StringBuilder sbExeSql = new StringBuilder(exeSql);
                String distinctKeywords = "DISTINCT";
                String selectKeywords = "SELECT";
                if (sbExeSql.toString().toUpperCase().contains(distinctKeywords)) {
                    sbExeSql.insert(sbExeSql.toString().toUpperCase().indexOf(distinctKeywords) + distinctKeywords.length(), " TOP 100 PERCENT ");
                } else {
                    sbExeSql.insert(sbExeSql.toString().toUpperCase().indexOf(selectKeywords) + selectKeywords.length(), " TOP 100 PERCENT ");
                }
                exeSql = sbExeSql.toString();
            }
            exeSql = "SELECT COUNT(*) FROM (" + exeSql + ") t";
        }
        NativeQuery nativeQuery = session.createNativeQuery(exeSql);
        initParams(nativeQuery);
        initPageInfo(nativeQuery);
        return nativeQuery;
    }

    public List<?> list() {
        return hibernateTemplate.execute(session -> getNativeQuery(session, false).list());
    }

    public Object getSingleResult() {
        return hibernateTemplate.execute(session -> {
            List<?> list = getNativeQuery(session, false).list();
            return list.isEmpty() ? null : list.get(0);
        });
    }

    public int count() {
        return hibernateTemplate.execute(session -> ((Number) getNativeQuery(session, true).getSingleResult()))
                .intValue();
    }

    @SuppressWarnings("rawtypes")
    public int executeUpdate() {
        return hibernateTemplate.execute(session -> {
            NativeQuery nativeQuery = getNativeQuery(session, false);
            if (affectedEntityClasss != null && !affectedEntityClasss.isEmpty()) {
                affectedEntityClasss.forEach(nativeQuery::addSynchronizedEntityClass);
            } else {
                nativeQuery.addSynchronizedQuerySpace("");
            }
            return nativeQuery.executeUpdate();
        });
    }

    public static class Builder {
        SqlCommand sqlQuery;

        public Builder(HibernateTool hibernateTemplate) {
            sqlQuery = new SqlCommand(hibernateTemplate);
        }

        public Builder setSql(String sql) {
            sqlQuery.queryString = sql;
            return this;
        }

        public Builder setStartPosition(int startPosition) {
            sqlQuery.startPosition = startPosition;
            return this;
        }

        public Builder setMaxCount(int maxCount) {
            sqlQuery.maxCount = maxCount;
            return this;
        }

        public Builder setParameters(List<Object> params) {
            sqlQuery.params = params;
            return this;
        }

        public Builder setParameters(Map<String, Object> paramPairs) {
            sqlQuery.paramPairs = paramPairs;
            return this;
        }

        public Builder setParameter(String key, Object value) {
            if (sqlQuery.paramPairs == null) {
                sqlQuery.paramPairs = new HashMap<>();
            }
            sqlQuery.paramPairs.put(key, value);
            return this;
        }

        public Builder setParameter(Object param) {
            if (sqlQuery.params == null) {
                sqlQuery.params = new ArrayList<>();
            }
            sqlQuery.params.add(param);
            return this;
        }

        public Builder addAffectedEntityClass(Class<?> entityClass) {
            if (sqlQuery.affectedEntityClasss == null) {
                sqlQuery.affectedEntityClasss = new ArrayList<>();
            }
            sqlQuery.affectedEntityClasss.add(entityClass);
            return this;
        }

        public SqlCommand build() {
            return sqlQuery;
        }

    }

}
