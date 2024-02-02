package com.ame.dao;


import com.ame.constant.CommonErrorCode;
import com.ame.core.RequestInfo;
import com.ame.core.exception.PlatformException;
import com.ame.entity.IBaseEntity;
import com.ame.filter.EntityFilter;
import com.ame.pagination.PageInfo;
import com.ame.pagination.PageModel;
import com.ame.util.DataAccessExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseEntityDao<T extends IBaseEntity> extends EntityDao<T> {

    protected static final Logger log = LoggerFactory.getLogger(BaseEntityDao.class);

    @Autowired
    protected BaseDao baseDao;

    /**
     * 删除对象
     * <p>
     * 如果对应的Entity上面加有Annotation @Where,则是逻辑删除，不会从数据库删除数据，仅仅加入标志位， 如果没有，那将谁是物理删除
     * </p>
     * <p>
     * 删除时会做关联引用检查，如果已经被引用，则不会被删除
     * </p>
     *
     * @param entityObject
     */
    @Override
    public void delete(T entityObject) {
        this.delete(entityObject, null);
    }

    /**
     * 删除对象
     * <p>
     * 如果对应的Entity上面加有Annotation @Where,则是逻辑删除，不会从数据库删除数据，仅仅加入标志位， 如果没有，那将谁是物理删除
     * </p>
     * <p>
     * 删除时会做关联引用检查，如果已经被引用，则不会被删除
     * </p>
     *
     * @param entityId
     */
    @Override
    public void deleteById(long entityId) {
        if (isVersionControl) {
            T byId = getById(entityId);
            if (byId != null) {
                delete(getById(entityId));
            } else {
                throw new PlatformException(CommonErrorCode.OBJECT_UPDATED_OR_DELETED_BY_ANOTHER,
                        "The data was updated or deleted by another user");
            }
        } else {
            List<Long> ids = new ArrayList<>();
            ids.add(entityId);
            this.deleteByIds(ids);
        }
    }

    /**
     * 删除对象
     * <p>
     * 如果对应的Entity上面加有Annotation @Where,则是逻辑删除，不会从数据库删除数据，仅仅加入标志位， 如果没有，那将谁是物理删除
     * </p>
     * <p>
     * 删除时会做关联引用检查，如果已经被引用，则不会被删除
     * </p>
     *
     * @param entityObject
     */
    public void delete(T entityObject, String reason) {
        try {
//            getHibernateTemplate().flush();
            if (isLogicDelete) {
                RequestInfo current = RequestInfo.current();
                if (current == null) {
                    RequestInfo newA = new RequestInfo();
                    newA.setUserId(-1);
                    newA.setUserZoneId(ZoneId.systemDefault());
                    newA.setUserLocal(Locale.getDefault());
                    newA.setRequestZonedDateTime(ZonedDateTime.now());
                    newA.setUserName("Anonymous");
                    newA.setIgnoreDataPermission(true);
                    RequestInfo.set(newA);
                }
                if (!StringUtils.isEmpty(reason)) {
                    entityObject.setDescription(reason);
                }
                entityObject.setDeleted(true);
                entityObject.setDeleteIp(RequestInfo.current().getUserIpAddress());
                entityObject.setDeleteTime(RequestInfo.current().getRequestZonedDateTime());
                entityObject.setDeleteUserId(RequestInfo.current().getUserId());
                entityObject.setDeleteUserName(RequestInfo.current().getUserName());
                entityObject.setDeleteUserFullName(
                        (RequestInfo.current().getUserFirstName() == null ? " " : RequestInfo.current().getUserFirstName())
                                + "" + (RequestInfo.current().getUserLastName() == null ? " "
                                : RequestInfo.current().getUserLastName()));
                baseDao.merge(entityObject);
                getHibernateTool().flush();
            } else {

                baseDao.delete(entityObject);
                getHibernateTool().flush();
            }
        } catch (DataAccessException e) {
            DataAccessExceptionUtils.processDataAccessException(e);
        }
//        auditDao.log(entityClass, entityObject.getId(), CommonConstants.OPERATION_DELETE, null);
    }

    public void delete(List<T> entities) {
        if (isVersionControl) {
            if (entities == null || entities.size() == 0) {
                return;
            }
            boolean hasMore = true;
            while (hasMore) {
                List<T> subEntities = null;
                if (entities.size() > 1000) {
                    subEntities = entities.subList(0, 1000);
                    entities = entities.subList(1000, entities.size());
                } else {
                    subEntities = entities;
                    hasMore = false;
                }
                List<Long> subIds = subEntities.stream().map(T::getId).collect(Collectors.toList());
                try {
                    for (T entityObject : subEntities) {
                        if (isLogicDelete) {
                            entityObject.setDeleted(true);
                            entityObject.setDeleteIp(RequestInfo.current().getUserIpAddress());
                            entityObject.setDeleteTime(RequestInfo.current().getRequestZonedDateTime());
                            entityObject.setDeleteUserId(RequestInfo.current().getUserId());
                            entityObject.setDeleteUserName(RequestInfo.current().getUserName());
                            entityObject.setDeleteUserFullName((RequestInfo.current().getUserFirstName() == null ? " "
                                    : RequestInfo.current().getUserFirstName()) + ""
                                    + (RequestInfo.current().getUserLastName() == null ? " "
                                    : RequestInfo.current().getUserLastName()));
                            baseDao.merge(entityObject);
                            getHibernateTool().flush();
                        } else {
                            baseDao.delete(entityObject);
                            getHibernateTool().flush();
                        }
                    }

                } catch (DataAccessException e) {
                    DataAccessExceptionUtils.processDataAccessException(e);
                }
//                auditDao.log(entityClass, subIds, CommonConstants.OPERATION_DELETE, null);
            }
        } else {
            List<Long> collect = entities.stream().map(T::getId).collect(Collectors.toList());
            deleteByIds(collect);
        }

    }

    /**
     * 根据传入ID批量删除对象，通过构建SQL一次执行
     * <p>
     * 如果对应的Entity上面加有Annotation @Where,则是逻辑删除，不会从数据库删除数据，仅仅加入标志位， 如果没有，那将谁是物理删除
     * </p>
     * <p>
     * 删除时会做关联引用检查，如果已经被引用，则不会被删除
     * </p>
     *
     * @param entityIds
     */
    @Override
    public void deleteByIds(List<Long> entityIds) {
        if (isVersionControl) {
            List<T> entities = listByIds(entityIds);
            if (entities.size() == entityIds.size()) {
                delete(entities);
            } else {
                throw new PlatformException(CommonErrorCode.OBJECT_UPDATED_OR_DELETED_BY_ANOTHER,
                        "The data was updated or deleted by another user");
            }
        } else {
            if (entityIds == null || entityIds.size() == 0) {
                return;
            }
            boolean hasMore = true;
            while (hasMore) {
                List<Long> subIds = null;
                if (entityIds.size() > 1000) {
                    subIds = entityIds.subList(0, 1000);
                    entityIds = entityIds.subList(1000, entityIds.size());
                } else {
                    subIds = entityIds;
                    hasMore = false;
                }


                if (isLogicDelete) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (RequestInfo.current() == null) {
                        RequestInfo newA = new RequestInfo();
                        newA.setUserId(-1);
                        newA.setUserZoneId(ZoneId.systemDefault());
                        newA.setUserLocal(Locale.getDefault());
                        newA.setRequestZonedDateTime(ZonedDateTime.now());
                        newA.setUserName("anonymous");
                        RequestInfo.set(newA);
                    }
                    String fullName = (RequestInfo.current().getUserFirstName() == null ? " "
                            : RequestInfo.current().getUserFirstName()) + ""
                            + (RequestInfo.current().getUserLastName() == null ? " "
                            : RequestInfo.current().getUserLastName());
                    stringBuilder.append("update " + entityClass.getName()).append(" set DELETED=id,")
                            .append("deleteIp =:deleteIp,").append("deleteUserId =:deleteUserId,")
                            .append("deleteUserName = :deleteUserName,").append("deleteTime =:deleteTime,")
                            .append("deleteUserFullName =:deleteUserFullName").append(" where id in(:idList )");

                    Map<String, Object> params = new HashMap<>();
                    params.put("idList", subIds);
                    params.put("deleteIp", RequestInfo.current().getUserIpAddress());
                    params.put("deleteUserId", RequestInfo.current().getUserId());
                    params.put("deleteUserName", RequestInfo.current().getUserName());
                    params.put("deleteTime", RequestInfo.current().getRequestZonedDateTime());
                    params.put("deleteUserFullName", fullName);
                    baseDao.getHqlBuilder().setHql(stringBuilder.toString()).setParameters(params).build()
                            .executeUpdate();
                    getHibernateTool().flush();
                } else {
                    super.deleteByIds(subIds);
                    getHibernateTool().flush();
                }

            }
        }
    }

    public T getByIdAndEvict(long id) {
        T byId = super.getById(id);
        if (byId != null) {
            this.evict(byId);
        }
        return byId;
    }

    protected T getByJpql(String jpql) {
        return baseDao.getByJpql(jpql, entityClass);
    }

    protected T getByJpql(String jpql, Map<String, Object> params) {
        return baseDao.getByJpql(jpql, params, entityClass);
    }

    @SuppressWarnings("hiding")
    public <T> T getByFilter(EntityFilter dataFilter) {
        dataFilter.setMaxResult(1);
        List<T> list = listByFilter(dataFilter);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Long> deleteByFilter(EntityFilter entityFilter) {
        if (isVersionControl) {
            List<T> objects = listByFilter(entityFilter);
            delete(objects);
            return objects.stream().map(T::getId).collect(Collectors.toList());
        } else {
            List<Long> list = listIdsByFilter(entityFilter);
            deleteByIds(list);
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Long> listIdsByFilter(EntityFilter entityFilter) {
        return baseDao.listIdsByEntityQuery(entityFilter.getEntityQuery());
    }

    public List<T> list(int startPosition, int maxCount) {
        return baseDao.list(entityClass, startPosition, maxCount);
    }

    public List<T> listByIds(List<Long> ids) {
        return baseDao.listByIds(ids, entityClass);
    }

    @SuppressWarnings("hiding")
    public <T> List<T> listByFilter(EntityFilter dataFilter) {
        return baseDao.listByEntityQuery(dataFilter.getEntityQuery());
    }

    /**
     * 分页条件查询
     *
     * @param dataFilter
     * @param <T>
     * @return
     */
    @SuppressWarnings("hiding")
    public <T> PageModel<T> pagingByFilter(EntityFilter dataFilter) {
        return baseDao.pagingByEntityQuery(dataFilter.getEntityQuery());
    }

    /**
     * 分页查询所有
     *
     * @param pageInfo
     * @return
     */
    public PageModel<T> paging(PageInfo pageInfo) {
        return baseDao.paging(entityClass, pageInfo);
    }

    public int countByFilter(EntityFilter dataFilter) {
        return baseDao.countByEntityQuery(dataFilter.getEntityQuery());
    }

    protected List<T> listByJPQuery(String jpql, Map<String, Object> params) {
        return baseDao.listByJpql(jpql, params, entityClass);
    }

    protected List<T> listByJPQuery(String jpql) {
        return baseDao.listByJpql(jpql, entityClass);
    }

    public void flush() {
        getHibernateTool().flush();
    }

    public void evict(T t) {
        getHibernateTool().evict(t);
    }

    public T create() {
        T entityObj = null;
        try {
            entityObj = entityClass.newInstance();
            // entityObj.setPlatformId(PlatformEnvironment.getServerInfo().getPlatformId());
        } catch (Exception e) {
            log.error("can not create a object", e);
        }
        return entityObj;
    }

    public EntityFilter createFilter() {
        return new EntityFilter(entityClass);
    }


    // List<?> listBySql(String sql, Map<String, Object> params){
    //
    // }

}
