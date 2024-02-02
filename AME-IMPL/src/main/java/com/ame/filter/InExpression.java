package com.ame.filter;

import java.util.ArrayList;
import java.util.List;

public class InExpression extends Criterion {

    private static final long serialVersionUID = 5918736174071220026L;

    private Property property;
    private boolean notIn = false;
    private List<?> objects;

    public InExpression(Property property, List<?> objects) {
        this.property = property;
        this.objects = objects;
    }

    public InExpression(Property property, List<?> objects, boolean notIn) {
        this.property = property;
        this.objects = objects;
        this.notIn = notIn;
    }

    @Override
    public String toHqlString(EntityQuery query) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(property.toHqlString(query));
        if (notIn) {
            stringBuilder.append(" not ");
        }
        stringBuilder.append(" in ( ?").append(query.parameterPosition++).append(")");
        return stringBuilder.toString();
    }

    @Override
    public List<Object> getParameters() {
        // if (getEntityQuery().isUsingExtensionTable()) {
        // return (List<Object>)objects;
        // } else {
        List<Object> paramters = new ArrayList<>(1);
        paramters.add(objects);
        return paramters;
        // }
    }

    @Override
    public boolean hasCfField() {
        return property.isCustomizedField();
    }

}
