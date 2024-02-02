package com.ame.dao;


import com.ame.constant.CommonConstants;
import com.ame.entity.BaseEntity;
import com.ame.entity.ISetDataId;
import com.ame.util.DataAccessExceptionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Version;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.dao.DataAccessException;

import java.lang.reflect.Field;
import java.util.List;

public abstract class EntityDao<T extends ISetDataId> extends Dao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityDao.class);
    protected Class<T> entityClass;

    protected boolean isVersionControl = false;
    protected boolean isLogicDelete = false;

    @Autowired
    protected BaseDao baseDao;


    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    public EntityDao() {
        ResolvableType resolvableType = ResolvableType.forClass(this.getClass()).as(EntityDao.class);
        entityClass = (Class<T>)resolvableType.resolveGenerics()[0];
        for (Field declaredField : entityClass.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(Version.class)) {
                isVersionControl = true;
                break;
            }
        }
        Where annotation = entityClass.getAnnotation(Where.class);
        if (annotation != null && annotation.clause().equals("DELETED=0")) {
            isLogicDelete = true;
        }
    }

    public T save(T entity) {
        return this.save(entity, true);
    }

    public T save(T entityObject, boolean doFlush) {
        return this.save(entityObject, doFlush, true);
    }

    public T save(T entityObject, boolean doFlush, boolean isAudit) {
        long id = entityObject.getId();
        String operation = id < 1 ? CommonConstants.OPERATION_INSERT : CommonConstants.OPERATION_UPDATE;
        if (entityObject.getId() < 1) {
            baseDao.save(entityObject);
        } else {
            entityObject = baseDao.merge(entityObject);
        }
        if (doFlush) {
            try {
                getHibernateTool().flush();
            } catch (DataAccessException e) {
                if (id < 1) {
                    entityObject.setId(-1);

                }
                return DataAccessExceptionUtils.processDataAccessException(e);
            }
        }


        return entityObject;
    }

    public void saveAll(List<T> entitys) {
        try {
            for (T entityObject : entitys) {
                if (entityObject.getId() < 1) {
                    baseDao.save(entityObject);
                } else {
                    entityObject = baseDao.merge(entityObject);
                }
            }
            getHibernateTool().flush();
        } catch (DataAccessException e) {
            DataAccessExceptionUtils.processDataAccessException(e);
        }
    }

    public void delete(T entity) {
        baseDao.delete(entity);
    }

    public void deleteById(long entityId) {
        baseDao.deleteById(entityId, entityClass);
    }

    public void deleteByIds(List<Long> entityIds) {
        baseDao.deleteByIds(entityIds, entityClass);
    }

    public T getById(long id) {
        return baseDao.getById(id, entityClass);
    }

    public List<T> list() {
        return baseDao.list(entityClass, 0, Integer.MAX_VALUE);
    }

}
