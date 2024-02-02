package com.ame.filter.sql;

public interface Criteria {

    public Criteria createAlias(String tableName, String alias);

    public Criteria addQueryColumn(QueryColumn queryColumn);

    public Criteria add(Criterion criterion);

    public Criteria addOrder(Order order);

    public void setMaxResult(int maxResult);

    public void setStartPosition(int startPosition);

    Criteria resetOrder();

}
