package com.ame.dao;


import com.ame.filter.query.SqlCommand;
import com.ame.filter.sql.SimpleSqlQuery;
import com.ame.pagination.PageInfo;
import com.ame.pagination.PageModel;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class SqlDao extends Dao {

    private static final Logger log = LoggerFactory.getLogger(SqlDao.class);

    protected SqlCommand.Builder getSqlBuilder() {
        return new SqlCommand.Builder(getHibernateTool());
    }


    public List<?> listBySql(String sql) {
        return getSqlBuilder().setSql(sql).build().list();
    }

    public List<?> listBySql(String sql, List<Object> params) {
        return getSqlBuilder().setSql(sql).setParameters(params).build().list();
    }


    public List<?> listBySql(int startPosition, int maxCount, String sql, List<Object> params) {
        return getSqlBuilder().setStartPosition(startPosition).setMaxCount(maxCount).setSql(sql).setParameters(params)
                .build().list();
    }

    public List<?> listBySql(String sql, Map<String, Object> params) {
        return getSqlBuilder().setSql(sql).setParameters(params).build().list();
    }



    public List<?> listBySql(String sql, Map<String, Object> params, int startPosition, int maxCount) {
        return getSqlBuilder().setStartPosition(startPosition).setMaxCount(maxCount).setSql(sql).setParameters(params)
                .build().list();
    }


    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> listMapBySql(String sql, Map<String, Object> params, int startPosition,
                                                  int maxCount) {
        return getHibernateTool().execute(session -> {
            NativeQuery nativeQuery = session.createNativeQuery(sql);
            nativeQuery.setFirstResult(startPosition);
            nativeQuery.setMaxResults(maxCount);
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            if (params != null && !params.isEmpty()) {
                params.forEach(nativeQuery::setParameter);
            }
            return nativeQuery.list();
        });
    }



    public PageModel<?> pagingBySql(String sql, String sqlOfCount, Map<String, Object> params, PageInfo pageInfo) {
        List<?> records = Collections.emptyList();
        Integer totalCount;
        totalCount = countBySql(sqlOfCount, params);
        if (totalCount > 0) {
            records = listBySql(sql, params, pageInfo.getStartPosition(), pageInfo.getPageSize());
        }
        PageModel<?> pageModel =
                new PageModel<>(totalCount, pageInfo.getPageSize(), pageInfo.getCurrentPage(), records);
        return pageModel;
    }


    public PageModel<Map<String, Object>> pagingMapBySql(String sql, String sqlOfCount, Map<String, Object> params,
                                                         PageInfo pageInfo) {
        List<Map<String, Object>> records = Collections.emptyList();
        int totalCount = countBySql(sqlOfCount, params);
        if (totalCount > 0) {
            records = listMapBySql(sql, params, pageInfo.getStartPosition(), pageInfo.getPageSize());
        }
        PageModel<Map<String, Object>> pageModel =
                new PageModel<>(totalCount, pageInfo.getPageSize(), pageInfo.getCurrentPage(), records);
        return pageModel;
    }





    public int countBySql(String sql, Map<String, Object> params) {
        return getSqlBuilder().setSql(sql).setParameters(params).build().count();
    }


    public int countBySql(String sql, List<Object> params) {
        return getSqlBuilder().setSql(sql).setParameters(params).build().count();
    }


    public int executeSql(String sql) {
        return getSqlBuilder().setSql(sql).build().executeUpdate();
    }



    public int executeSql(String sql, Map<String, Object> params) {
        return getSqlBuilder().setSql(sql).setParameters(params).build().executeUpdate();
    }






    public List<?> listBySqlQuery(SimpleSqlQuery sqlQuery) {
        return getSqlBuilder().setMaxCount(sqlQuery.getMaxResult()).setStartPosition(sqlQuery.getStartPosition())
                .setSql(sqlQuery.generateSql()).setParameters(sqlQuery.getParameters()).build().list();
    }

    public List<?> listIdsBySqlQuery(SimpleSqlQuery sqlQuery) {
        return getSqlBuilder().setMaxCount(sqlQuery.getMaxResult()).setStartPosition(sqlQuery.getStartPosition())
                .setSql(sqlQuery.generateSqlIdOnly()).setParameters(sqlQuery.getParameters()).build().list();
    }

    public int countBySqlQuery(SimpleSqlQuery sqlQuery) {
        return getSqlBuilder().setSql(sqlQuery.generateSqlNotOrderBy()).setParameters(sqlQuery.getParameters()).build()
                .count();
    }
}
