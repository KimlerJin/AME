package com.ame.dao;


import com.ame.entity.BaseEntity;
import com.ame.filter.EntityFilter;
import com.ame.filter.EntityQuery;
import com.ame.filter.query.HqlCommand;
import com.ame.pagination.PageInfo;
import com.ame.pagination.PageModel;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class BaseDao extends SqlDao {

    public <T> void save(T entityObject) {
        getHibernateTool().save(entityObject);
    }

    public <T> List<T> listByEntityQuery(EntityQuery entityQuery) {
        if (entityQuery.getPageInfo() != null) {
            return getHqlBuilder().setHql(entityQuery.generateHql(true)).setParameters(entityQuery.getParameters())
                    .setStartPosition(entityQuery.getPageInfo().getStartPosition())
                    .setMaxCount(entityQuery.getPageInfo().getPageSize()).setCacheable(entityQuery.isCacheable()).build()
                    .list();
        } else {
            return getHqlBuilder().setHql(entityQuery.generateHql(true)).setParameters(entityQuery.getParameters())
                    .setCacheable(entityQuery.isCacheable()).build().list();
        }
    }


    /**
     * 更新对象，会将扩展属性一并更新
     *
     * @param entityObject
     * @param <T>
     * @return
     */
    public <T> T merge(T entityObject) {

        entityObject = getHibernateTool().merge(entityObject);
        return entityObject;
    }

    /**
     * 删除对象
     * <p>
     * 物理删除, 扩展属性一并删除
     * @param <T>
     */
    public <T> void delete(T entity) {
        getHibernateTool().delete(entity);

    }

    /**
     * 根据传入的ID删除对象
     * <p>
     * 物理删除
     *
     * @param entityClass
     * @param <T>
     */
    public <T> void deleteById(long entityId, Class<T> entityClass) {
        getHqlBuilder().setHql("delete FROM " + entityClass.getName() + " o where o.id = " + entityId).build()
                .executeUpdate();
    }

    /**
     * 根据传入的ID批量删除
     * <p>
     * 会以SQL的方式一次调用删除
     *
     * @param entityIds
     * @param entityClass
     * @param <T>
     */
    public <T> void deleteByIds(List<Long> entityIds, Class<T> entityClass) {
        if (entityIds != null && entityIds.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("delete FROM ").append(entityClass.getName()).append(" o where o.id in(");
            for (int i = 0; i < entityIds.size(); i++) {
                stringBuilder.append(entityIds.get(i));
                if (i < entityIds.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(")");
            executeJpql(stringBuilder.toString());

        }
    }

    public <T> void evict(T t) {
        getHibernateTool().evict(t);

    }

    @SuppressWarnings("unchecked")
    public <T> T getById(long id, Class<T> entityClass) {
        T t = getHibernateTool().get(entityClass, id);
        return t;
    }

    public <T> List<T> listByIds(List<Long> ids, Class<T> entityClass) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        EntityFilter entityFilter = new EntityFilter(entityClass);
        entityFilter.fieldIn(BaseEntity.ID, ids);
        return this.listByEntityQuery(entityFilter.getEntityQuery());
    }

    protected List<Long> listIdsByEntityQuery(EntityQuery entityQuery) {
        String generateHql = entityQuery.generateHql(false);
        // if (generateHql.startsWith("select")) {
        int from = generateHql.indexOf("from");
        generateHql = "SELECT d_root.id  " + generateHql.substring(from);
        // }
        if (entityQuery.getPageInfo() != null) {
            return getHqlBuilder().setHql(generateHql).setParameters(entityQuery.getParameters())
                    .setStartPosition(entityQuery.getPageInfo().getStartPosition())
                    .setMaxCount(entityQuery.getPageInfo().getPageSize()).setCacheable(entityQuery.isCacheable()).build()
                    .list();

        } else {
            return getHqlBuilder().setHql(generateHql).setParameters(entityQuery.getParameters())
                    .setCacheable(entityQuery.isCacheable()).build().list();

        }
    }


    public <E> List<E> listByJpql(String jpql, Class<E> entityClass) {
        return listByJpql(jpql, (List<Object>) null, entityClass, 0, 0);
    }

    public <E> List<E> listByJpql(String jpql, Class<E> entityClass, boolean cacheable) {
        return listByJpql(jpql, (List<Object>) null, entityClass, cacheable);
    }

    public <E> List<E> listByJpql(String jpql, Map<String, Object> params, Class<E> entityClass) {
        return listByJpql(jpql, params, entityClass, 0, 0);
    }

    public <E> List<E> listByJpql(String jpql, Map<String, Object> params, Class<E> entityClass, boolean cacheable) {
        return listByJpql(jpql, params, entityClass, 0, 0, cacheable);
    }

    public <E> List<E> listByJpql(String jpql, Map<String, Object> params, Class<E> entityClass, int startPosition,
                                  int maxCount) {
        return listByJpql(jpql, params, entityClass, startPosition, maxCount, false);
    }

    public <E> List<E> listByJpql(String jpql, List<Object> params, Class<E> entityClass, boolean cacheable) {
        return listByJpql(jpql, params, entityClass, 0, 0, cacheable);
    }

    public <E> List<E> listByJpql(String jpql, List<Object> params, Class<E> entityClass) {
        return listByJpql(jpql, params, entityClass, 0, 0);
    }

    public <E> List<E> listByJpql(String jpql, List<Object> params, Class<E> entityClass, int startPosition,
                                  int maxCount) {
        return listByJpql(jpql, params, entityClass, startPosition, maxCount, false);
    }

    public <E> List<E> listByJpql(String jpql, Map<String, Object> params, Class<E> entityClass, int startPosition,
                                  int maxCount, boolean cacheable) {
        return getHqlBuilder().setCacheable(cacheable).setHql(jpql).setParameters(params)
                .setStartPosition(startPosition).setMaxCount(maxCount).build().list();
    }

    public <E> List<E> listByJpql(String jpql, List<Object> params, Class<E> entityClass, int startPosition,
                                  int maxCount, boolean cacheable) {
        return getHqlBuilder().setCacheable(cacheable).setHql(jpql).setParameters(params)
                .setStartPosition(startPosition).setMaxCount(maxCount).build().list();
    }

    public <E> E getByJpql(String jpql, Class<E> entityClass) {
        List<E> list = listByJpql(jpql, (List<Object>) null, entityClass);
        return list.isEmpty() ? null : list.get(0);
    }

    public <E> E getByJpql(String jpql, Map<String, Object> params, Class<E> entityClass) {
        List<E> list = listByJpql(jpql, params, entityClass);
        return list.isEmpty() ? null : list.get(0);
    }

    public <E> E getByJpql(String jpql, Map<String, Object> params, Class<E> entityClass, boolean cacheable) {
        List<E> list = listByJpql(jpql, params, entityClass, cacheable);
        return list.isEmpty() ? null : list.get(0);
    }

    public <E> E getByJpql(String jpql, List<Object> params, Class<E> entityClass) {
        List<E> list = listByJpql(jpql, params, entityClass);
        return list.isEmpty() ? null : list.get(0);
    }

    public <E> E getByJpql(String jpql, List<Object> params, Class<E> entityClass, boolean cacheable) {
        List<E> list = listByJpql(jpql, params, entityClass, cacheable);
        return list.isEmpty() ? null : list.get(0);
    }

    public <T> List<T> list(Class<T> entityClass, int startPosition, int maxCount) {
        return list(entityClass, false, startPosition, maxCount);
    }

    public <T> List<T> list(Class<T> entityClass, boolean isCacheable, int startPosition, int maxCount) {
        StringBuilder hqlStrBuilder = new StringBuilder();
        hqlStrBuilder.append("Select o FROM ").append(entityClass.getName()).append(" o ");

        hqlStrBuilder.append(" order by o.id desc");


        return getHqlBuilder().setHql(hqlStrBuilder.toString()).setStartPosition(startPosition).setMaxCount(maxCount)
                .setCacheable(isCacheable).build().list();
    }

    /**
     * 分页查询全部
     *
     * @param entityClass
     * @param <T>
     * @return
     */
    public <T> PageModel<T> paging(Class<T> entityClass, PageInfo pageInfo) {
        List<T> records = Collections.emptyList();
        Integer totalCount;
        EntityQuery entityQuery = new EntityQuery(entityClass);
        totalCount = countByEntityQuery(entityQuery);
        if (totalCount > 0) {
            records = list(entityClass, false, pageInfo.getStartPosition(),
                    pageInfo.getPageSize());
        }
        PageModel<T> pageModel =
                new PageModel<>(totalCount, pageInfo.getPageSize(), pageInfo.getCurrentPage(), records);
        return pageModel;
    }

    /**
     * 分页条件查询
     *
     * @param entityQuery
     * @param <T>
     * @return
     */
    public <T> PageModel<T> pagingByEntityQuery(EntityQuery entityQuery) {
        List<T> records = Collections.emptyList();
        Integer totalCount = countByEntityQuery(entityQuery);
        Integer currentPage = entityQuery.getPageInfo().getCurrentPage();
        Integer pageSize = entityQuery.getPageInfo().getPageSize();
        if (totalCount > 0) {
            records = listByEntityQuery(entityQuery);
        }
        return new PageModel<>(totalCount, pageSize, currentPage, records);
    }

    public int countByEntityQuery(EntityQuery entityQuery) {
        return getHqlBuilder().setHql(entityQuery.generateHqlCount()).setParameters(entityQuery.getParameters())
                .setCacheable(entityQuery.isCacheable()).build().count();
    }

    public int countByJpql(String hql, Map<String, Object> params) {
        return getHqlBuilder().setHql(hql).setHql(hql).setParameters(params).build().count();
    }

    public int countByJpql(String hql, List<Object> params) {
        return getHqlBuilder().setHql(hql).setParameters(params).build().count();
    }

    public int executeJpql(String jpql) {
        return getHqlBuilder().setHql(jpql).build().executeUpdate();
    }

    public int executeJpql(String jpql, Map<String, Object> params) {
        return getHqlBuilder().setHql(jpql).setParameters(params).build().executeUpdate();
    }


    public HqlCommand.Builder getHqlBuilder() {
        return new HqlCommand.Builder(getHibernateTool());
    }
}
