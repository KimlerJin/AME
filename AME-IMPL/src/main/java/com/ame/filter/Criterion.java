package com.ame.filter;


public abstract class Criterion implements Expression, Parameterized {

    private EntityQuery entityQuery;

    public EntityQuery getEntityQuery() {
        return entityQuery;
    }

    public void setEntityQuery(EntityQuery entityQuery) {
        this.entityQuery = entityQuery;
    }
}
