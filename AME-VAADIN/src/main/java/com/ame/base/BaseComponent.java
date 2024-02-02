package com.ame.base;


import com.ame.utils.NotificationUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.Div;
import jakarta.annotation.PostConstruct;


public abstract class BaseComponent extends Composite<Div> implements HasSize, Notifiable {

    private static final long serialVersionUID = -8941328193577628241L;

    private Object data;

    @PostConstruct
    private void _init() {
        init();
    }

    public void setCompositionRoot(Component root) {
        getContent().setSizeFull();
        getContent().removeAll();
        getContent().add(root);
    }

    protected void init() {}

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public void notificationError(String localizationKey, String defaultText) {
        notificationError(localizationKey, defaultText, (Object[])null);
    }

    @Override
    public void notificationError(String localizationKey, String defaultText, Object... params) {
        NotificationUtils.notificationError(defaultText);
    }

    @Override
    public void notificationInfo(String localizationKey, String defaultText) {
        notificationInfo(localizationKey, defaultText, (Object[])null);
    }

    @Override
    public void notificationInfo(String localizationKey, String defaultText, Object... params) {
        NotificationUtils.notificationInfo(defaultText);
    }

    @Override
    public void notificationWarning(String localizationKey, String defaultText) {
        notificationWarning(localizationKey, defaultText, (Object[])null);
    }

    @Override
    public void notificationWarning(String localizationKey, String defaultText, Object... params) {
        NotificationUtils.notificationWarning(defaultText);
    }

    public void handlingException(Throwable e) {
        NotificationUtils.notificationException(e);
    }

    /**
     * 将页面渲染的操作放入后台运行，不会阻塞当前主线程的运行，常常用于页面分开渲染。
     * <tr>
     * 该方法支持嵌套，如果当前逻辑已经在后台运行，那么调用该方法将不会再次放入后台运行，而会立即执行
     *
     * @param loadData
     *            要被渲染的具体任务
     */
//    protected void pushToRenderQueue(ILoadData loadData) {
//        UIRender.addRenderItem(new UIRenderItem(UI.getCurrent(), this, loadData));
//    }

    /**
     * 将页面渲染的操作放入后台运行，不会阻塞当前主线程的运行，常常用于页面分开渲染。
     * <tr>
     * 该方法支持嵌套，如果当前逻辑已经在后台运行，那么调用该方法将不会再次放入后台运行，而会立即执行
     *
     * @param owner
     *            表示当前的数据加载时属于哪个组件的；相同Owner的，会保证按照顺序逐一执行。
     * @param loadData
     *            要被渲染的具体任务
     */
//    protected void pushToRenderQueue(Object owner, ILoadData loadData) {
//        UIRender.addRenderItem(new UIRenderItem(UI.getCurrent(), owner, loadData));
//    }
}