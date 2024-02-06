package com.ame.views.test;

import com.ame.entity.UserEntity;
import com.ame.service.IUserService;
import com.ame.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("UsernamePassword")
@Route(value = "usernamepassword", layout = MainLayout.class)
public class UsernamePasswordView extends VerticalLayout {


    private Button btn = new Button("注册");

    @Autowired
    private IUserService userService;

    public UsernamePasswordView() {
        TextField tfUsername = new TextField("用户名");

        TextField tfPassword = new TextField("密码");


        add(tfUsername, tfPassword, btn);

        btn.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            UserEntity user = userService.getByName(tfUsername.getValue());
            if (user != null) {
                user.setEmail("kimler_jin@amaxgs.com");
                userService.save(user);
            } else {
                user = new UserEntity();
                user.setUserName(tfUsername.getValue());
                user.setPassword(tfPassword.getValue());
                userService.save(user);
            }
        });

    }


}
