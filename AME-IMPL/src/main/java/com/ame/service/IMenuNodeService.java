package com.ame.service;


import com.ame.entity.MenuNodeEntity;

import java.util.List;

public interface IMenuNodeService extends IBaseEntityService<MenuNodeEntity> {

    List<MenuNodeEntity> listSubMenuNodesByParentId(Long parentId);

    MenuNodeEntity getMenuNodeByPath(String path);

    MenuNodeEntity getMenuNodeByViewClass(String viewClass);


    List<MenuNodeEntity> listGroup(Boolean isGroup);
}
