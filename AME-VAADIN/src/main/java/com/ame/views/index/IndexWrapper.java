package com.ame.views.index;

import com.ame.base.BaseView;
import com.ame.spring.BeanManager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;


@Route("")
public class IndexWrapper extends BaseView {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

     UI.getCurrent().getPage().setLocation(BeanManager.getIndex());

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public String getPageTitle() {
        return null;
    }
}
