package com.ame.filter;



import com.ame.util.AMECollectionUtils;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

public class HqlRestrictions {

    public static Criterion equals(String propertyName, boolean isCfField, Object value) {
        return value == null ? isNull(propertyName, isCfField)
                : new SimpleExpression(new Property(propertyName, isCfField), value, "=");
    }

    public static Criterion notEquals(String propertyName, boolean isCfField, Object value) {
        return new SimpleExpression(new Property(propertyName, isCfField), value, "<>");
    }

    public static Criterion less(String propertyName, boolean isCfField, Object value) {
        return new SimpleExpression(new Property(propertyName, isCfField), value, "<");
    }

    public static Criterion lessEquals(String propertyName, boolean isCfField, Object value) {
        return new SimpleExpression(new Property(propertyName, isCfField), value, "<=");
    }

    public static Criterion greator(String propertyName, boolean isCfField, Object value) {
        return new SimpleExpression(new Property(propertyName, isCfField), value, ">");
    }

    public static Criterion greatorEquals(String propertyName, boolean isCfField, Object value) {
        return new SimpleExpression(new Property(propertyName, isCfField), value, ">=");
    }

    public static Criterion equalsProperty(String propertyName, boolean isCfField, String otherPropertyName,
                                           boolean otherIsCfField) {
        return new PropertyExpression(new Property(propertyName, isCfField),
                new Property(otherPropertyName, otherIsCfField), "=");
    }

    public static Criterion compareProperty(String propertyName, boolean isCfField, String operation,
                                            String otherPropertyName, boolean otherIsCfField) {
        return new PropertyExpression(new Property(propertyName, isCfField),
                new Property(otherPropertyName, otherIsCfField), operation);
    }

    public static Criterion isNull(String propertyName, boolean isCfField) {
        return new NullExpression(new Property(propertyName, isCfField), true);
    }

    public static Criterion isNotNull(String propertyName, boolean isCfField) {
        return new NullExpression(new Property(propertyName, isCfField), false);
    }

    public static Criterion like(String propertyName, boolean isCfField, String value) {
        return new SimpleExpression(new Property(propertyName, isCfField), MatchMode.ANYWHERE.toMatchString(value),
                " like ");
    }

    public static Criterion like(String propertyName, boolean isCfField, String value, MatchMode matchMode) {
        return new SimpleExpression(new Property(propertyName, isCfField), matchMode.toMatchString(value), " like ");
    }

    public static Criterion in(String propertyName, boolean isCfField, List<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            if (values.size() > 1000) {
                return splitCriterion(propertyName, values, isCfField, false);
            } else {
                return new InExpression(new Property(propertyName, isCfField), values);
            }
        } else {
            return new OneEqualsTwo();
        }


    }

    public static Criterion notIn(String propertyName, boolean isCfField, List<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            if (values.size() > 1000) {
                return splitCriterion(propertyName, values, isCfField, true);
            } else {
                return new InExpression(new Property(propertyName, isCfField), values, true);
            }
        } else {
            return new OneEqualsOne();
        }
    }

    public static Criterion or(Criterion... criterions) {
        return new Junction(Junction.Nature.OR, criterions);
    }

    public static Criterion and(Criterion... criterions) {
        return new Junction(Junction.Nature.AND, criterions);
    }


    /**
     * oracle in 查询不能超过1000条， 所以分开查
     */
    private static Criterion splitCriterion(String propertyName, List<?> values, boolean isCfField, boolean notIn) {
        values.sort(Comparator.comparing(Object::toString));
        List<List> subList = AMECollectionUtils.splitList(values, 1000);
        Criterion[] subCriterias = new Criterion[subList.size()];
        for (int i = 0; i < subList.size(); i++) {
            subCriterias[i] = new InExpression(new Property(propertyName, isCfField), subList.get(i), notIn);
        }
        if (subCriterias.length == 1) {
            return subCriterias[0];
        }
        return HqlRestrictions.or(subCriterias);
    }

}
