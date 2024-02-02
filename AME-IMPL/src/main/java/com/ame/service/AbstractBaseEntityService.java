package com.ame.service;


import com.ame.dao.BaseEntityDao;
import com.ame.entity.IBaseEntity;
import com.ame.filter.EntityFilter;
import com.ame.pagination.PageInfo;
import com.ame.pagination.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractBaseEntityService<T extends IBaseEntity>  implements IBaseEntityService<T> {


    private static final long serialVersionUID = 4012982770515308167L;

    protected static Logger log = LoggerFactory.getLogger(AbstractBaseEntityService.class);


    protected Class<T> entityClass;

    @SuppressWarnings(value = "unchecked")
    public AbstractBaseEntityService() {
        ResolvableType resolvableType = ResolvableType.forClass(this.getClass()).as(AbstractBaseEntityService.class);
        entityClass = (Class<T>)resolvableType.resolveGenerics()[0];
    }

    @Override
    public int countByFilter(EntityFilter entityFilter) {
        return getDao().countByFilter(entityFilter);
    }

    @Override
    public EntityFilter createFilter() {
        return new EntityFilter(entityClass);
    }

    @Override
    @Transactional
    public void delete(T t) {
        getDao().delete(t);
    }

    @Override
    @Transactional
    public void delete(List<T> entities) {
        getDao().delete(entities);

    }

    @Override
    @Transactional
    public void deleteById(long id) {
        getDao().deleteById(id);
    }

    @Override
    @Transactional
    public void   deleteByIds(List<Long> objIds) {
        getDao().deleteByIds(objIds);
    }

    @Override
    public T getByFilter(EntityFilter entityFilter) {
        return getDao().getByFilter(entityFilter);
    }

    @Override
    @Transactional
    public void deleteByFilter(EntityFilter entityFilter) {
        List<Long> ts = getDao().deleteByFilter(entityFilter);
    }

    @Override
    public T getById(long id) {
        return getDao().getById(id);
    }

    @Override
    public T getByIdAndEvict(long id) {
        return getDao().getByIdAndEvict(id);
    }

    @Override
    public List<T> list(int startPosition, int maxCount) {
        return getDao().list(startPosition, maxCount);
    }

    @Override
    public List<T> listByIds(List<Long> ids) {
        return getDao().listByIds(ids);
    }

    @Override
    public List<T> listByFilter(EntityFilter entityFilter) {
        return getDao().listByFilter(entityFilter);
    }




    /**
     * 分页查询所有
     *
     * @return
     */
    @Override
    public PageModel<T> paging(PageInfo pageInfo) {
        return getDao().paging(pageInfo);
    }

    /**
     * 分页条件查询
     *
     * @param entityFilter
     * @return
     */
    @Override
    public PageModel<T> pagingByFilter(EntityFilter entityFilter) {
        return getDao().pagingByFilter(entityFilter);
    }

    @Override
    @Transactional
    public T save(T entity) {
        T result = getDao().save(entity);
        return result;

    }

    @Override
    @Transactional
    public void saveAll(List<T> entities) {
        getDao().saveAll(entities);

    }

    @Override
    public void clearCacheManually() {

    }

    protected abstract BaseEntityDao<T> getDao();


}
