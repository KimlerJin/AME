package com.ame.filter;

import java.util.Collections;
import java.util.List;

public class NullExpression extends Criterion {

    private static final long serialVersionUID = -8128455124491807765L;

    private final Property property;
    private final boolean isNull;

    public NullExpression(Property property, boolean isNull) {
        this.property = property;
        this.isNull = isNull;
    }

    @Override
    public String toHqlString(EntityQuery query) {
        if (isNull) {
            return property.toHqlString(query) + " is null";
        } else {
            return property.toHqlString(query) + " is not null";
        }
    }

    @Override
    public List<Object> getParameters() {
        return Collections.emptyList();
    }

    @Override
    public boolean hasCfField() {
        return property.hasCfField();
    }

}
