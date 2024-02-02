package com.ame.filter;

import java.util.Collections;
import java.util.List;

public class PropertyExpression extends Criterion {
    private static final long serialVersionUID = -7829093865245411835L;
    private final Property property;
    private final Property otherProperty;
    private final String op;

    public PropertyExpression(Property property, Property otherProperty, String op) {
        this.property = property;
        this.otherProperty = otherProperty;
        this.op = op;
    }

    @Override
    public String toHqlString(EntityQuery query) {
        final StringBuilder fragment = new StringBuilder();
        fragment.append(property.toHqlString(query)).append(" ").append(op).append(" ")
            .append(otherProperty.toHqlString(query));
        return fragment.toString();
    }

    // @Override
    // public String toSqlString(EntityQuery query) {
    // final StringBuilder fragment = new StringBuilder();
    // fragment.append(property.toSqlString(query)).append(" ").append(op).append(" ")
    // .append(otherProperty.toSqlString(query));
    // return fragment.toString();
    // }

    @Override
    public List<Object> getParameters() {
        return Collections.emptyList();
    }

    @Override
    public boolean hasCfField() {
        return property.isCustomizedField() || otherProperty.isCustomizedField();
    }
}
