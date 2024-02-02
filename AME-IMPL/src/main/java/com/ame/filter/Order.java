package com.ame.filter;

public class Order implements Expression {

    private static final long serialVersionUID = 2734858703627367324L;

    private boolean ascending;
    private Property property;

    public Order(Property property, boolean ascending) {
        this.property = property;
        this.ascending = ascending;
    }

    public static Order asc(String propertyName, boolean isCfField) {
        return new Order(new Property(propertyName, isCfField), true);
    }

    public static Order desc(String propertyName, boolean isCfField) {
        return new Order(new Property(propertyName, isCfField), false);
    }

    @Override
    public String toHqlString(EntityQuery query) {
        return property.toHqlString(query) + (ascending ? " asc" : " desc");
    }

    @Override
    public boolean hasCfField() {
        return property.hasCfField();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order) {
            return ((Order)obj).property.equals(this.property);
        }

        return false;
    }
}
