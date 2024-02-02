package com.ame.filter.sql;


import com.ame.pagination.PageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimpleSqlQuery implements Criteria, Serializable {

    private static final long serialVersionUID = -3014099545412401812L;
    private static String DEFAULT_ROOT_ALIAS = "d_root";

    private List<QueryColumn> queryColumns = new ArrayList<>();
    private List<String> otherTables = new ArrayList<>();
    private List<Criterion> criterions = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private String tableName = null;
    private String rootAlies = null;
    protected int parameterPosition = 1;
    private int maxResult;
    private int startPosition;
    private PageInfo pageInfo;

    public SimpleSqlQuery() {}

    public SimpleSqlQuery(String tableName) {
        this.tableName = tableName;
        rootAlies = DEFAULT_ROOT_ALIAS;
    }

    public SimpleSqlQuery(String tableName, String rootAlies) {
        this.tableName = tableName;
        this.rootAlies = rootAlies;
    }

    @Override
    public Criteria createAlias(String tableName, String alias) {
        otherTables.add(tableName + " " + alias);
        return this;
    }

    @Override
    public Criteria addQueryColumn(QueryColumn queryColumn) {
        queryColumns.add(queryColumn);
        return this;
    }

    @Override
    public Criteria add(Criterion criterion) {
        criterions.add(criterion);
        return this;
    }

    @Override
    public Criteria addOrder(Order order) {
        orders.add(order);
        return this;
    }

    @Override
    public Criteria resetOrder() {
        orders.clear();
        return this;
    }

    public String generateSql() {
        parameterPosition = 1;
        StringBuilder sql = new StringBuilder();
        sql.append(generateQueryColumnsStatement()).append(generateFromStatement()).append(generateWhereStatement())
            .append(generateOrderStatement());
        return sql.toString();
    }

    public String generateSqlIdOnly() {
        parameterPosition = 1;
        StringBuilder sql = new StringBuilder();
        sql.append(generateQueryIdsStatement()).append(generateFromStatement()).append(generateWhereStatement())
            .append(generateOrderStatement());
        return sql.toString();
    }

    public String generateSqlNotOrderBy() {
        parameterPosition = 1;
        StringBuilder sql = new StringBuilder();
        sql.append(generateQueryColumnsStatement()).append(generateFromStatement()).append(generateWhereStatement());
        return sql.toString();
    }

    public String generateSqlCount() {
        parameterPosition = 1;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) ").append(generateFromStatement()).append(generateWhereStatement());
        return sql.toString();
    }

    public List<Object> getParameters() {
        List<Object> objects = new ArrayList<>();
        for (Criterion criterion : criterions) {
            objects.addAll(criterion.getParameters());
        }
        return objects;
    }

    private String generateQueryColumnsStatement() {
        StringBuilder queryColumnsStatement = new StringBuilder(" select ");
        if (queryColumns.size() > 0) {
            for (int i = 0; i < queryColumns.size(); i++) {
                queryColumnsStatement.append(queryColumns.get(i).toSqlString(this));
                if (i < queryColumns.size() - 1) {
                    queryColumnsStatement.append(" , ");
                }
            }
        } else {
            queryColumnsStatement.append(rootAlies).append(".* ");
        }
        return queryColumnsStatement.toString();
    }

    private String generateQueryIdsStatement() {
        StringBuilder queryColumnsStatement = new StringBuilder(" select ");
        queryColumnsStatement.append(rootAlies).append(".INSTANCE_ID ");
        return queryColumnsStatement.toString();
    }

    private String generateFromStatement() {
        StringBuilder tableStatement = new StringBuilder(" from ");
        tableStatement.append(tableName).append(" ").append(rootAlies);
        for (String otherTableName : otherTables) {
            tableStatement.append(", ").append(otherTableName);
        }
        return tableStatement.toString();
    }

    private String generateWhereStatement() {
        StringBuilder whereStatement = new StringBuilder(" where 1=1 ");
        for (Criterion criterion : criterions) {
            whereStatement.append(" and ").append(criterion.toSqlString(this));
        }
        return whereStatement.toString();
    }

    private String generateOrderStatement() {
        if (orders.size() > 0) {
            StringBuilder orderStatement = new StringBuilder(" order by ");
            for (int i = 0; i < orders.size(); i++) {
                orderStatement.append(orders.get(i).toSqlString(this));
                if (i < orders.size() - 1) {
                    orderStatement.append(", ");
                }
            }
            return orderStatement.toString();
        }
        return "";
    }

    public String addAliesIfNotExist(String propertyName) {
        if (propertyName.indexOf(".") < 1) {
            return rootAlies + "." + propertyName;
        }
        return propertyName;
    }

    @Override
    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public int getStartPosition() {
        return startPosition;
    }

    @Override
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public List<QueryColumn> getQueryColumns() {
        return queryColumns;
    }

    public String getTableName() {
        return tableName;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
