package com.ame.base;

import com.ame.utils.NotificationUtils;

import java.io.Serializable;

public abstract class BasePresenter<T extends IBaseView> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8807686733730393219L;

    private T view;

    public T getView(){
        return view;
    }

    public void setView(T view){
        this.view = view;
    }

    /**
     * 用<br/>
     * <code>NotificationUtils.notificationException(e);</code><br/>
     * 代替
     *
     * @param e
     */
    @Deprecated
    public void handlingException(Throwable e){
        NotificationUtils.notificationException(e);
    }
}
