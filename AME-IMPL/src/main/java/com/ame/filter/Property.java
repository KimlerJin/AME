package com.ame.filter;

public class Property implements Expression {

    private static final long serialVersionUID = 2060507399708586586L;

    private String name;

    private boolean isCustomizedField;

    public Property(String name) {
        this(name, false);
    }

    public Property(String name, boolean isCustomizedField) {
        this.name = name;
        this.isCustomizedField = isCustomizedField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCustomizedField() {
        return isCustomizedField;
    }

    public void setCustomizedField(boolean isCustomizedField) {
        this.isCustomizedField = isCustomizedField;
    }

    @Override
    public String toHqlString(EntityQuery query) {
        return (isCustomizedField ? query.extensionAlies : query.rootAlies) + "." + name;
    }

    @Override
    public boolean hasCfField() {
        return isCustomizedField;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Property) {
            return ((Property)obj).getName().equals(this.name);
        }

        return false;
    }
}
