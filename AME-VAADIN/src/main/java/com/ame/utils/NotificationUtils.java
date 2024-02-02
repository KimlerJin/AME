package com.ame.utils;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationUtils.class);

    private static final String SMALL_FONT_SIZE = "<br/><i><font size=\"2\">{0}</font></i>";

    public static void generateNotification(NotificationVariant notificationVariant ,String message){
        Notification notification = new Notification();
        notification.addThemeVariants(notificationVariant);
        Div text = new Div(new Text(message));
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("关闭");
        closeButton.addClickListener(event -> {
            notification.close();
        });
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }

    public static void notificationError(String message) {
        generateNotification(NotificationVariant.LUMO_ERROR,message);

    }

    public static void notificationException(Throwable e) {
        notificationError(e.getMessage());
    }

    public static void notificationWarning(String message) {
        generateNotification(NotificationVariant.LUMO_WARNING,message);
    }

    public static void notificationInfo(String message) {
        Icon icon = VaadinIcon.CHECK_CIRCLE.create();
        Notification notification = new Notification();
        Div info = new Div(new Text(message));
        HorizontalLayout layout = new HorizontalLayout(icon, info);
        layout.getStyle().set("font-weight", "700").set("color", "var(--lumo-primary-text-color)").set("font-size", "1.15rem");
        layout.setPadding(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        notification.add(layout);
        notification.open();
        notification.setDuration(6000);
    }

    public static void notificationInfo(String message, int duration, Notification.Position position) {
        Notification.show(message, duration, position);
    }

    public static void notificationSuccessful(String message) {
        Notification.show(message);
    }

    public static void notificationSuccessful(String message, int duration, Notification.Position position) {
        Notification.show(message, duration, position);
    }

}
