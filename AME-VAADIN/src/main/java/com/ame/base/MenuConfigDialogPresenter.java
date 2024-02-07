package com.ame.base;


import com.ame.entity.MenuNodeEntity;
import com.ame.service.IMenuNodeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

/**
 * Author:Tracy Date:2021/4/6 Description:
 */
@Component
@Scope("prototype")
public class MenuConfigDialogPresenter extends BasePresenter {

    private static final long serialVersionUID = 2143931633365152714L;

    @Autowired
    private IMenuNodeService menuNodeService;




    public void updateMenuBatch(Set<MenuNodeEntity> menuNodes) {
        if (CollectionUtils.isEmpty(menuNodes)) {
            return;
        }
        menuNodeService.saveAll(new ArrayList<>(menuNodes));

    }



}
