package com.ame.filter.sql;

import java.util.ArrayList;
import java.util.List;

public class SimpleExpression implements Criterion {

    private static final long serialVersionUID = -7829093865245411835L;
    private final String propertyName;
    private final Object value;
    private final String op;

    protected SimpleExpression(String propertyName, Object value, String op) {
        this.propertyName = propertyName;
        this.value = value;
        this.op = op;
    }

    @Override
    public String toSqlString(SimpleSqlQuery simpleSqlQuery) {
        final StringBuilder fragment = new StringBuilder();
        fragment.append(simpleSqlQuery.addAliesIfNotExist(propertyName)).append(" ").append(op).append(" ?")
            .append(simpleSqlQuery.parameterPosition++);
        return fragment.toString();
    }

    @Override
    public List<Object> getParameters() {
        List<Object> paramters = new ArrayList<>(1);
        paramters.add(value);
        return paramters;
    }

}
