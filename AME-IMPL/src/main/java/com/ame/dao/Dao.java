package com.ame.dao;

import com.ame.hibernate.HibernateTool;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Dao {


    private HibernateTool hibernateTool;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public void initSessionFactory() {
        this.hibernateTool = new HibernateTool(entityManager);
    }

    public final HibernateTool getHibernateTool() {
        return currentHibernateTool();
    }

    public final HibernateTool currentHibernateTool() {

        return hibernateTool;
    }

}
