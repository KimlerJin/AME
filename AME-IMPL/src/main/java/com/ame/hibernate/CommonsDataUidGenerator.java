package com.ame.hibernate;


import com.ame.spring.BeanManager;
import com.ame.uidgenerator.impl.CachedUidGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CommonsDataUidGenerator implements IdentifierGenerator {

    private CachedUidGenerator cachedUidGenerator;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (cachedUidGenerator == null) {
            cachedUidGenerator = BeanManager.getService(CachedUidGenerator.class);
        }
        return cachedUidGenerator.getUID();
    }

}
