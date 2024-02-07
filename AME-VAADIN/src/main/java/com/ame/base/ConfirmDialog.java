package com.ame.base;


import com.ame.utils.NotificationUtils;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConfirmDialog extends BaseComponent {

    /**
     *
     */
    private static final long serialVersionUID = 7258320217829312645L;

    public static String WIDTH = "400px";
    public static String HEIGHT = "400px";

    private DialogCallBack dialogCallBack = null;

    private ConfirmDialog(String message) {
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();

        HorizontalLayout hlHeader = new HorizontalLayout();
        hlHeader.setWidth("100%");
        hlHeader.setPadding(true);
        Icon icon = new Icon(VaadinIcon.QUESTION_CIRCLE_O);
        icon.setColor("red");
        icon.setSize("20px");
        Label title = new Label( "Confirm");
        title.getStyle().set("font-weight", "600").set("font-size", "16px");
        hlHeader.add(icon, title);

        VerticalLayout dialogContent = new VerticalLayout();
        dialogContent.setSizeFull();
        dialogContent.add(new Label(message));

        Button btnOK = new Button("OK");
        Button btnCancel = new Button("Cancel");

        HorizontalLayout saveLayout = new HorizontalLayout();
        saveLayout.setWidth("100%");
        saveLayout.setPadding(false);
        saveLayout.add(btnCancel, btnOK);
        saveLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        saveLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        saveLayout.setAlignItems(FlexComponent.Alignment.END);

        btnOK.setDisableOnClick(true);
        btnOK.addClickListener(event -> {
            try {
                if (dialogCallBack != null) {
                    dialogCallBack.done(new ConfirmResult(ConfirmResult.Result.OK));
                }
                ((Dialog) getParent().get()).close();
            } catch (Exception e) {

                    NotificationUtils.notificationError(e.getMessage());
                if (dialogCallBack != null) {
                    dialogCallBack.done(new ConfirmResult(ConfirmResult.Result.ERROR));
                }
            } finally {
                btnOK.setEnabled(true);
            }
        });

        btnOK.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnOK.addClickShortcut(Key.ENTER);

        btnCancel.addClickListener(event -> {
            if (dialogCallBack != null) {
                dialogCallBack.done(new ConfirmResult(ConfirmResult.Result.CANCEL));
            }
            ((Dialog) getParent().get()).close();
        });

        btnCancel.addClickShortcut(Key.ESCAPE);
        root.add(hlHeader, dialogContent, saveLayout);
        root.expand(dialogContent);
        btnCancel.setId("btn_cancel");
        btnOK.setId("btn_ok");
        setCompositionRoot(root);
        this.setSizeFull();
    }

    public static void show(String message, DialogCallBack callBack) {
        ConfirmDialog dlg = new ConfirmDialog(message);
        dlg.addCallBackListener(callBack);
        Dialog dialog = new Dialog();
        dialog.setWidth(WIDTH);
        dialog.removeAll();
        dialog.add(dlg);
        dialog.open();
    }

    public void addCallBackListener(DialogCallBack callBack) {
        this.dialogCallBack = callBack;
    }
}
