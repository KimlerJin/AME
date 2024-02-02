package com.ame.filter.sql;

import java.util.Collections;
import java.util.List;

public class NullExpression implements Criterion {

    private static final long serialVersionUID = -8128455124491807765L;

    private final String propertyName;
    private final boolean isNull;

    public NullExpression(String propertyName, boolean isNull) {
        this.propertyName = propertyName;
        this.isNull = isNull;
    }

    @Override
    public String toSqlString(SimpleSqlQuery simpleSqlQuery) {
        if (isNull) {
            return simpleSqlQuery.addAliesIfNotExist(propertyName) + " is null";
        } else {
            return simpleSqlQuery.addAliesIfNotExist(propertyName) + " is not null";
        }
    }

    @Override
    public List<Object> getParameters() {
        return Collections.emptyList();
    }

}
