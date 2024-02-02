package com.ame.filter;

import com.ame.filter.sql.SimpleSqlQuery;

import java.io.Serializable;

public class QueryColumn implements Serializable {

    private static final long serialVersionUID = 4296393771240294164L;

    private String columnName;
    private String columnAlias;

    public QueryColumn(String columnName) {
        this.columnName = columnName;
        String[] strs = columnName.split("\\.");
        if (strs.length == 1) {
            this.columnAlias = columnName;
        }
        if (strs.length == 2) {
            this.columnAlias = strs[1];
        }

    }

    public QueryColumn(String columnName, String columnAlias) {
        this.columnName = columnName;
        this.columnAlias = columnAlias;
    }

    public String toSqlString(SimpleSqlQuery simpleSqlQuery) {
        return simpleSqlQuery.addAliesIfNotExist(columnName) + " " + columnAlias;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnAlias() {
        return columnAlias;
    }

    public void setColumnAlias(String columnAlias) {
        this.columnAlias = columnAlias;
    }

}
