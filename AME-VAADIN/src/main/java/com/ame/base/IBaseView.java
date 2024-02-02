package com.ame.base;

import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.HasDynamicTitle;

public interface IBaseView extends HasDynamicTitle, BeforeLeaveObserver, BeforeEnterObserver, AfterNavigationObserver{
}
