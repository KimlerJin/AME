package com.ame.service;



import com.ame.entity.IBaseEntity;
import com.ame.filter.EntityFilter;
import com.ame.pagination.PageInfo;
import com.ame.pagination.PageModel;

import java.util.Collection;
import java.util.List;

public interface IBaseEntityService<T extends IBaseEntity>  {

    int countByFilter(EntityFilter entityFilter);

    EntityFilter createFilter();

    /**
     * 如果删除有额外的逻辑需要处理，需要重写delete方法，则直接重写deleteByIds()方法
     * 
     * @param t
     */
    void delete(T t);

    /**
     * 如果删除有额外的逻辑需要处理，需要重写delete方法，则直接重写deleteByIds()方法
     * 
     * @param entityIds
     */
    void delete(List<T> entityIds);

    /**
     * 如果删除有额外的逻辑需要处理，需要重写deleteById方法，则直接重写deleteByIds()方法
     * 
     * @param entityId
     */
    void deleteById(long entityId);

    void deleteByIds(List<Long> entityIds);

    void deleteByFilter(EntityFilter entityFilter);

    T getByFilter(EntityFilter entityFilter);

    T getById(long id);

    /**
     * 获取最新的对象，并移出缓存，变为游离状态
     *
     * @param id
     * @return
     */
    T getByIdAndEvict(long id);

    List<T> listByIds(List<Long> ids);

    List<T> list(int startPosition, int maxCount);

    List<T> listByFilter(EntityFilter entityFilter);


    /**
     * 分页查询所有
     *
     * @param pageInfo
     * @return
     */
    PageModel<T> paging(PageInfo pageInfo);

    /**
     * 分页条件查询
     *
     * @param entityFilter
     * @return
     */
    PageModel<T> pagingByFilter(EntityFilter entityFilter);

    T save(T entity);

    void saveAll(List<T> entities);

    void clearCacheManually();
}
