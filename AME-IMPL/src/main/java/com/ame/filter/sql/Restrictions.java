package com.ame.filter.sql;

import java.util.List;

public class Restrictions {

    public static Criterion equals(String propertyName, Object value) {
        return value == null ? isNull(propertyName) : new SimpleExpression(propertyName, value, "=");
    }

    public static Criterion notEquals(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<>");
    }

    public static Criterion less(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<");
    }

    public static Criterion lessEquals(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<=");
    }

    public static Criterion greator(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">");
    }

    public static Criterion greatorEquals(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">=");
    }

    public static Criterion equalsProperty(String propertyName, String otherPropertyName) {
        return new PropertyExpression(propertyName, otherPropertyName, "=");
    }

    public static Criterion compareProperty(String propertyName, String operation, String otherPropertyName) {
        return new PropertyExpression(propertyName, otherPropertyName, operation);
    }

    public static Criterion isNull(String propertyName) {
        return new NullExpression(propertyName, true);
    }

    public static Criterion isNotNull(String propertyName) {
        return new NullExpression(propertyName, false);
    }

    public static Criterion like(String propertyName, String value) {
        return new SimpleExpression(propertyName, MatchMode.ANYWHERE.toMatchString(value), " like ");
    }

    public static Criterion like(String propertyName, String value, MatchMode matchMode) {
        return new SimpleExpression(propertyName, matchMode.toMatchString(value), " like ");
    }

    public static Criterion in(String propertyName, List<?> values) {
        return new InExpression(propertyName, values);
    }

    public static Criterion notIn(String propertyName, List<?> values) {
        return new InExpression(propertyName, values, true);
    }

    public static Criterion or(Criterion... criterions) {
        return new Junction(Junction.Nature.OR, criterions);
    }

    public static Criterion and(Criterion... criterions) {
        return new Junction(Junction.Nature.AND, criterions);
    }

}
