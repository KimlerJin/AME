package com.ame.views.login;

import com.ame.base.BaseView;
import com.ame.util.AnnotationUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

@Route(value = "login")
public class LoginView extends BaseView {

    private TextField username = new TextField();

    private PasswordField password = new PasswordField();

    private Button btnLogin = new Button("登录");


    @Override
    protected void initView() {
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(username, "用户名");
        formLayout.addFormItem(password, "密码");
        formLayout.add(username, password);
        HorizontalLayout hLogin = new HorizontalLayout();
        hLogin.add(btnLogin);

        btnLogin.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken authenticationToken = new UsernamePasswordToken();
            authenticationToken.setUsername(username.getValue());
            authenticationToken.setPassword(password.getValue().toCharArray());
            subject.login(authenticationToken);
            UI.getCurrent().getPage().reload();
        });


        formLayout.add(hLogin);
        formLayout.setSizeFull();
        setCompositionRoot(formLayout);

    }


    @Override
    protected void loadData() {

    }

    @Override
    public String getPageTitle() {
        return "登录";
    }
}
