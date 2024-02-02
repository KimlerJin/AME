package com.ame.filter.sql;

import java.io.Serializable;

public class Order implements Serializable {

    private static final long serialVersionUID = 2734858703627367324L;

    private boolean ascending;
    private String propertyName;

    public Order(String propertyName, boolean ascending) {
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    public static Order asc(String propertyName) {
        return new Order(propertyName, true);
    }

    public static Order desc(String propertyName) {
        return new Order(propertyName, false);
    }

    public String toSqlString(SimpleSqlQuery simpleSqlQuery) {
        return simpleSqlQuery.addAliesIfNotExist(propertyName) + (ascending ? " asc" : " desc");
    }

}
