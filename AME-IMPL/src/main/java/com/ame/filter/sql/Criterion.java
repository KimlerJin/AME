package com.ame.filter.sql;

import java.io.Serializable;
import java.util.List;

public interface Criterion extends Serializable {

    public String toSqlString(SimpleSqlQuery simpleSqlQuery);

    public List<Object> getParameters();

}
