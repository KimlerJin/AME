package com.ame;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.TimeZone;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Push
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        SpringApplication.run(Application.class, args);
    }


//    @EventListener(ApplicationReadyEvent.class)
//    public void doSomethingAfterStartup() {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    }

}
