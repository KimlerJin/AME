package com.ame.filter;

import java.util.ArrayList;
import java.util.List;

public class SimpleExpression extends Criterion {

    private static final long serialVersionUID = -7829093865245411835L;
    private final Property property;
    private final Object value;
    private final String op;

    public SimpleExpression(Property property, Object value, String op) {
        this.property = property;
        this.value = value;
        this.op = op;
    }

    @Override
    public String toHqlString(EntityQuery query) {
        final StringBuilder fragment = new StringBuilder();
        fragment.append(property.toHqlString(query)).append(" ").append(op).append(" ?")
            .append(query.parameterPosition++);
        return fragment.toString();
    }

    // @Override
    // public String toSqlString(EntityQuery query) {
    // final StringBuilder fragment = new StringBuilder();
    // fragment.append(property.toSqlString(query)).append(" ").append(op).append(" ?")
    // .append(query.parameterPosition++);
    // return fragment.toString();
    // }

    @Override
    public List<Object> getParameters() {
        List<Object> paramters = new ArrayList<>(1);
        paramters.add(value);
        return paramters;
    }

    @Override
    public boolean hasCfField() {
        return property.isCustomizedField();
    }

}
