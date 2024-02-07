package com.ame.service;


import com.ame.constant.CommonErrorCode;
import com.ame.core.exception.PlatformException;
import com.ame.dao.BaseEntityDao;
import com.ame.dao.MenuNodeDao;
import com.ame.entity.MenuNodeEntity;
import com.ame.filter.EntityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 */
@Service
public class MenuNodeService extends AbstractBaseEntityService<MenuNodeEntity> implements IMenuNodeService {

    @Autowired
    private MenuNodeDao menuNodeDao;

    @Override
    protected BaseEntityDao<MenuNodeEntity> getDao() {
        return menuNodeDao;
    }

    @Override
    public List<MenuNodeEntity> listSubMenuNodesByParentId(Long parentId) {
        EntityFilter entityFilter = new EntityFilter(MenuNodeEntity.class);
        if (parentId == null || parentId < 1) {
            entityFilter.fieldLessThan(MenuNodeEntity.PARENT_ID, 1L);
        } else {
            entityFilter.fieldEqualTo(MenuNodeEntity.PARENT_ID, parentId);
        }
        entityFilter.orderBy(MenuNodeEntity.SEQUENCE, false);
        return menuNodeDao.listByFilter(entityFilter);
    }

    @Override
    public MenuNodeEntity getMenuNodeByPath(String path) {
        EntityFilter entityFilter = new EntityFilter(MenuNodeEntity.class);
        entityFilter.fieldEqualTo(MenuNodeEntity.NODE_PATH, path);
        return menuNodeDao.getByFilter(entityFilter);
    }

    @Override
    public MenuNodeEntity getMenuNodeByViewClass(String viewClass) {
        EntityFilter entityFilter = new EntityFilter(MenuNodeEntity.class);
        entityFilter.fieldEqualTo(MenuNodeEntity.VIEW_CLASS, viewClass);
        return menuNodeDao.getByFilter(entityFilter);
    }

    @Override
    public void saveAll(List<MenuNodeEntity> entities) {
        for (MenuNodeEntity nodeEntity : entities) {
            save(nodeEntity);
        }
    }

    /**
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public MenuNodeEntity save(MenuNodeEntity entity) {
        if (entity.isNew()) {
            List<MenuNodeEntity> menuNodeEntities = listSubMenuNodesByParentId(entity.getParentId());
            menuNodeEntities.forEach(existNode -> {
                if (existNode.getPath().equals(entity.getPath())) {
                    throw new PlatformException(CommonErrorCode.OBJECT_EXISTS_OR_BE_REFERENCED,
                            "Please check if the object exists or referenced by another object!");
                }
                //todo：这段逻辑有点奇怪，为什么不能强制一样，导致jbf导入会报错，先拿掉
//                Long pageId = entity.getPageId();
//                if (pageId > 0L && existNode.getPageId().equals(pageId)) {
//                    throw new PlatformException(CommonErrorCode.OBJECT_EXISTS_OR_BE_REFERENCED,
//                            "Please check if the object exists or referenced by another object!");
//                }
//                String viewClass = entity.getViewClass();
//                if (!Strings.isNullOrEmpty(entity.getViewClass()) && viewClass.equals(existNode.getViewClass())) {
//                    throw new PlatformException(CommonErrorCode.OBJECT_EXISTS_OR_BE_REFERENCED,
//                            "Please check if the object exists or referenced by another object!");
//                }
            });
        }
        return super.save(entity);
    }


//    @Override
//    public List<MenuNodeEntity> listRootMenuNode(MenuTypeEnum menuTypeEnum) {
//        EntityFilter filter = createFilter();
//        filter.fieldLessThan(MenuNodeEntity.PARENT_ID, 1L);
//        if (menuTypeEnum != null) {
//            filter.fieldEqualTo(MenuNodeEntity.TYPE, menuTypeEnum);
//        }
//        return listByFilter(filter);
//    }
//
//    @Override
//    public List<MenuNodeEntity> listByType(MenuTypeEnum type) {
//        EntityFilter filter = createFilter();
//        filter.fieldEqualTo(MenuNodeEntity.TYPE, type);
//        return listByFilter(filter);
//    }

    @Override
    public List<MenuNodeEntity> listGroup(Boolean isGroup) {
        EntityFilter filter = createFilter();
//        filter.fieldEqualTo(MenuNodeEntity.TYPE, type);
        filter.fieldEqualTo(MenuNodeEntity.IS_GROUP, isGroup);
        return listByFilter(filter);
    }
}
