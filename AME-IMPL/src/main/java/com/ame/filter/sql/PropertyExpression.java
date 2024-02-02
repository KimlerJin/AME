package com.ame.filter.sql;

import java.util.Collections;
import java.util.List;

public class PropertyExpression implements Criterion {
    private static final long serialVersionUID = -7829093865245411835L;
    private final String propertyName;
    private final String otherPropertyName;
    private final String op;

    protected PropertyExpression(String propertyName, String otherPropertyName, String op) {
        this.propertyName = propertyName;
        this.otherPropertyName = otherPropertyName;
        this.op = op;
    }

    @Override
    public String toSqlString(SimpleSqlQuery simpleSqlQuery) {
        final StringBuilder fragment = new StringBuilder();
        fragment.append(simpleSqlQuery.addAliesIfNotExist(propertyName)).append(" ").append(op).append(" ")
            .append(simpleSqlQuery.addAliesIfNotExist(otherPropertyName));
        return fragment.toString();
    }

    @Override
    public List<Object> getParameters() {
        return Collections.emptyList();
    }
}
