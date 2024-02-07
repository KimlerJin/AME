package com.ame.base;


import com.ame.entity.MenuNodeEntity;
import com.ame.service.IMenuNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 */
@Component
@Scope("prototype")
public class MenuNodeSettingPresenter extends BasePresenter {

    @Autowired
    private IMenuNodeService menuNodeService;


    public List<MenuNodeEntity> listRootMenuNode() {
        return menuNodeService.listRootMenuNode();
    }


    public List<MenuNodeEntity> getMenuSubItems(long parentId) {
        List<MenuNodeEntity> rootNodes = menuNodeService.listSubMenuNodesByParentId(parentId);
        return rootNodes == null ? new ArrayList<>() : rootNodes;
    }

    public void deleteMenuNodes(List<MenuNodeEntity> menuNodes) {
        menuNodeService.deleteByIds(menuNodes.stream().map(MenuNodeEntity::getId).collect(Collectors.toList()));
    }

    public void dropComponentUpdate(String parentId, String itemId) {
        MenuNodeEntity menuParent = menuNodeService.getById(Long.parseLong(parentId));
        MenuNodeEntity menuNode = menuNodeService.getById(Long.parseLong(itemId));
        menuNode.setParentId(menuParent.getId());
        menuNodeService.save(menuNode);
    }



}
