package com.ame.base;

public interface IBaseDialog {

    void show(DialogCallBack callBack);

    boolean isShown();

    void onCancelButtonClicked();

    boolean onOKButtonClicked();

    void setContentMode(boolean contentMode);

    void close();
}