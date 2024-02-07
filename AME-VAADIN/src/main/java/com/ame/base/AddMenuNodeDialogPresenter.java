package com.ame.base;

import com.ame.entity.MenuNodeEntity;
import com.ame.filter.EntityFilter;
import com.ame.service.IMenuNodeService;
import com.ame.spring.BeanManager;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 */
@Component
@Scope("prototype")
public class AddMenuNodeDialogPresenter extends BasePresenter {

    private static final long serialVersionUID = 7399141017583257129L;

    private MenuNodeEntity menuNode;

    @Autowired
    private IMenuNodeService menuNodeService;



    public MenuNodeEntity getObject() {
        return menuNode;
    }

    public void setObject(MenuNodeEntity menuNode) {
        this.menuNode = menuNode;
    }


    public void save(MenuNodeEntity menuNode) {
        Objects.requireNonNull(menuNode);
        menuNodeService.save(menuNode);
    }

    public boolean checkViewOrPageExist(MenuNodeEntity menuNode) {
        Objects.requireNonNull(menuNode);
        if ((menuNode.getPageId() == null || menuNode.getPageId() < 1) && StringUtils.isEmpty(menuNode.getViewClass())) {
            return false;
        }
        EntityFilter filter = menuNodeService.createFilter();
        if (menuNode.getPageId() != null && menuNode.getPageId() > 0L) {
            filter.fieldEqualTo(MenuNodeEntity.PAGE_ID, menuNode.getPageId());
        }
        if (menuNode.getViewClass() != null) {
            filter.fieldEqualTo(MenuNodeEntity.VIEW_CLASS, menuNode.getViewClass());
        }
        List<MenuNodeEntity> existList = menuNodeService.listByFilter(filter);
        if (!CollectionUtils.isEmpty(existList)) {
            if (menuNode.isNew()) {
                return true;
            }
            return existList.stream().noneMatch(item -> item.getId() == menuNode.getId());
        }
        return false;
    }


    public List<Class> getPageViews() {

        String[] beanNames;
        beanNames = BeanManager.getApplicationContext().getBeanNamesForType(BaseView.class);

        List<Class> results = new ArrayList<>();
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = BeanManager.beanDefinitionForBeanNameIncludingAncestors(beanName);
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> beanClass = null;
            try {
                beanClass = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (beanClass == null) {
                continue;
            }
            SpringComponent springComponent =
                    AnnotatedElementUtils.findMergedAnnotation(beanClass, SpringComponent.class);
            if (springComponent != null) {
                results.add(beanClass);
            }
        }
        return results;
    }
}
