package com.ame.hibernate;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public abstract class AbstractIntegrator implements Integrator {
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
        SessionFactoryServiceRegistry serviceRegistry) {
        EventListenerRegistry eventListenerRegistry = sessionFactory.unwrap(SessionFactoryImplementor.class)
            .getServiceRegistry().getService(EventListenerRegistry.class);
        integrate(eventListenerRegistry);
    }

    public abstract void integrate(EventListenerRegistry eventListenerRegistry);

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

    }
}
