package com.ame.views.user;

import com.ame.base.BaseDialog;
import com.ame.base.DialogCallBack;
import com.ame.entity.UserEntity;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.context.annotation.Scope;


@org.springframework.stereotype.Component
@Scope("prototype")
public class UserDialog extends BaseDialog implements IUserDialog {


    private Binder<UserEntity> binder = new Binder<>();

    @Override
    protected void okButtonClicked() throws Exception {

    }

    @Override
    protected void cancelButtonClicked() {

    }

    @Override
    protected Component getDialogContent() {
        VerticalLayout verticalLayout = new VerticalLayout();
        FormLayout formLayout = new FormLayout();
        TextField tfUserName = new TextField("用户名");
        PasswordField passwordField = new PasswordField("密码");


        return verticalLayout;
    }

    @Override
    public void show(DialogCallBack callBack) {

    }

    @Override
    public void setUser(UserEntity user) {

    }
}
