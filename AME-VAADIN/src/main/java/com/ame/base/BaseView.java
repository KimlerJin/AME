package com.ame.base;

import com.ame.utils.NotificationUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseView extends BaseComponent implements IBaseView, ErrorHandler {
    protected static final Logger logger = LoggerFactory.getLogger(BaseView.class);

    protected String path;

    public BaseView() {
    }

    public void init() {
        UI.getCurrent().getSession().setErrorHandler(this);
        initView();
        loadData();
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        path = event.getLocation().getPath();

    }


    /**
     * 初始化页面布局,添加页面元素
     */
    protected abstract void initView();

    /**
     * 页面初始化后，进行数据的加载，该方法在页面的生命周期内只会调用一次，当页面发生切换，再进入时，该方法不会被调用。
     * <p>
     * 在Vaadin14中，页面切换后，会自动恢复页面的数据状态（如选中某行数据），不需要重新加载数据。
     * <tr>
     * 如果有数据需要在每次进入页面时进行加载，需要重写beforeEnter方法
     * </tr>
     * <tr>
     * 如果有参数需要获取，并加载数据，则需要实现HasUrlParameter接口，在setParameter中进行数据加载
     * </tr>
     * </p>
     */
    protected abstract void loadData();

    /**
     * 对外添加可读的
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

    }

    @Override
    public void error(ErrorEvent event) {
        logger.error(event.getThrowable().getMessage(),event.getThrowable());
        NotificationUtils.notificationException(event.getThrowable());
    }

}
