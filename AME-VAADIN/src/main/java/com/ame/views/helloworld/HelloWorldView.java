package com.ame.views.helloworld;


import com.ame.base.BaseView;
import com.ame.views.MainLayout;
import com.ame.websocket.WebSocketServer;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.IOException;

@Route(value = "hello", layout = MainLayout.class)
public class HelloWorldView extends BaseView {

    private TextField name;
    private Button sayHello;



    public HelloWorldView() {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            try {
                WebSocketServer.BroadCastInfo("hello world");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        sayHello.addClickShortcut(Key.ENTER);


    }

    @Override
    protected void initView() {
        VerticalLayout vRoot = new VerticalLayout();
        vRoot.add(name, sayHello);
        setCompositionRoot(vRoot);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public String getPageTitle() {
        return "Hello World";
    }
}
