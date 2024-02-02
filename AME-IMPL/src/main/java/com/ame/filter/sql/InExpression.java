package com.ame.filter.sql;

import java.util.ArrayList;
import java.util.List;

public class InExpression implements Criterion {

    private static final long serialVersionUID = 5918736174071220026L;

    private String propertyName;
    private boolean notIn = false;
    private List<?> objects;

    public InExpression(String propertyName, List<?> objects) {
        this.propertyName = propertyName;
        this.objects = objects;
    }

    public InExpression(String propertyName, List<?> objects, boolean notIn) {
        this.propertyName = propertyName;
        this.objects = objects;
        this.notIn = notIn;
    }

    @Override
    public String toSqlString(SimpleSqlQuery simpleSqlQuery) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(simpleSqlQuery.addAliesIfNotExist(propertyName));
        if (notIn) {
            stringBuilder.append(" not ");
        }
        stringBuilder.append(" in (");
        for (int i = 0; i < objects.size(); i++) {
            stringBuilder.append(" ?").append(simpleSqlQuery.parameterPosition++).append(" ");
            if (i < objects.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public List<Object> getParameters() {
        List<Object> paramters = new ArrayList<>(objects.size());
        paramters.addAll(objects);
        return paramters;
    }

}
