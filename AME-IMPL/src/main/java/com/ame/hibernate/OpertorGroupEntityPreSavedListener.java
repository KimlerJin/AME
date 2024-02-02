package com.ame.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;

public class OpertorGroupEntityPreSavedListener implements SaveOrUpdateEventListener {

    /**
     *
     */
    private static final long serialVersionUID = 9020505831995204315L;

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
//        Object entity = event.getObject();
//        try {
//            Class<?> clazz = Class.forName(entity.getClass().getName());
//            for (Class<?> anInterface : clazz.getInterfaces()) {
//                if (anInterface.equals(IHasOperatorGroup.class)) {
//                    BaseEntity baseEntity = (BaseEntity)entity;
//                    RequestInfo current = RequestInfo.current();
//                    try {
//                        if (baseEntity.isNew()) {
//                            Method method = clazz.getMethod("setCreateOperatorGroupId", long.class);
//                            method.invoke(entity, current == null ? 0L : current.getOperatorGroupId());
//                        }
//                        Method method = clazz.getMethod("setLastModifyOperatorGroupId", long.class);
//                        method.invoke(entity, current == null ? 0L : current.getOperatorGroupId());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        ArrayList<IHasOperatorGroup> groups =
//            new ArrayList<>(BeanManager.beansOfTypeIncludingAncestors(IHasOperatorGroup.class).values());
//        for (IHasOperatorGroup group : groups) {
//            if (entity.equals(group)) {
//
//            }
//        }

    }

}
