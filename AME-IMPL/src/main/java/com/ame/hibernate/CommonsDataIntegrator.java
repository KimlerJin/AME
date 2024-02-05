package com.ame.hibernate;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;

public class CommonsDataIntegrator extends AbstractIntegrator {

    @Override
    public void integrate(EventListenerRegistry eventListenerRegistry) {

        eventListenerRegistry.prependListeners(EventType.MERGE, new EntityPreMergedListener());
        eventListenerRegistry.prependListeners(EventType.SAVE, new OpertorGroupEntityPreSavedListener());
    }

}
