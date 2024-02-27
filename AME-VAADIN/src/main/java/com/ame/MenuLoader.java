package com.ame;

import com.ame.views.MainLayout;
import com.ame.views.node.MenuNodeSettingView;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

//@Component
public class MenuLoader implements VaadinServiceInitListener {

    RouteConfiguration routeConfiguration;

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        loadMenu();
    }

    public void loadMenu() {
        routeConfiguration = RouteConfiguration.forApplicationScope();
        routeConfiguration.setRoute("menuNodeSettingView", MenuNodeSettingView.class, MainLayout.class);

    }
}