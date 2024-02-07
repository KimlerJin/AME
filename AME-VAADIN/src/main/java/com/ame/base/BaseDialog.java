package com.ame.base;

import com.ame.utils.NotificationUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDialog extends BaseComponent implements IBaseDialog {

    private static final long serialVersionUID = 6496042430247994063L;
    /**
     *
     */
    protected static Logger logger = LoggerFactory.getLogger(BaseDialog.class);
    protected ConfirmResult result = new ConfirmResult();
    protected HorizontalLayout buttonLayout = new HorizontalLayout();
    protected Label lbCaption = new Label();
    protected Dialog dialog = new Dialog();
    protected Button btnOK = new Button();
    protected ShortcutRegistration cancelShortcutRegistration = null;
    protected Button btnCancel = new Button();
    private boolean isFromButton = false;
    private Boolean isShown = false;
    private boolean contentMode = false;
    private boolean isInitialized = false;
    private DialogCallBack callBack = null;
    private HorizontalLayout hlHeader;
    private String width = null;
    private String height = null;
    private Icon iconExpand = new Icon(VaadinIcon.PLUS);
    private Icon iconMinus = new Icon(VaadinIcon.MINUS);
    private Icon iconClose = new Icon(VaadinIcon.CLOSE_SMALL);

    public BaseDialog() {
        super();
    }

    public BaseDialog(boolean contentMode) {
        super();
        this.contentMode = contentMode;
    }

    protected void initUIData() {
    }

    @Override
    public void init() {
        isInitialized = true;
        isFromButton = false;
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setPadding(false);
        root.setSpacing(false);
        btnOK.setText("OK");
        btnOK.setWidth("80px");
        btnOK.setDisableOnClick(true);
        btnOK.addClickListener(event -> {
            onOKButtonClicked();
            btnOK.setEnabled(true);
        });
        btnOK.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        okShortcutRegistration = btnOK.addClickShortcut(Key.ENTER);
        btnCancel.setText("Cancel");
        btnCancel.setDisableOnClick(true);
        btnCancel.addClickListener(event -> {
            onCancelButtonClicked();
            btnCancel.setEnabled(true);
        });
        btnCancel.setWidth("80px");
        cancelShortcutRegistration = btnCancel.addClickShortcut(Key.ESCAPE);
        // Header
        hlHeader = new HorizontalLayout();
        hlHeader.setWidthFull();
        hlHeader.setPadding(true);
        hlHeader.getStyle().set("background", VaadinCommonConstant.DIALOG_HEADER_BG_COLOR);
        hlHeader.getStyle().set("padding", "15px 20px");
        hlHeader.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        lbCaption.setWidthFull();
        lbCaption.getElement().getStyle().set("font-size", "16px").set("font-weight", "600");
        iconExpand.getStyle().set("height", "16px").set("color", "#757575").set("float", "right");
        iconMinus.getStyle().set("height", "16px").set("color", "#757575").set("float", "right");
        iconExpand.addClickListener(event -> {
            dialog.getElement().getThemeList().add("sizefull");
            iconMinus.setVisible(true);
            iconExpand.setVisible(false);
        });

        iconMinus.addClickListener(event -> {
            dialog.getElement().getThemeList().remove("sizefull");
            if (width != null) {
                dialog.setWidth(width);
            }
            if (height != null) {
                dialog.setHeight(height);
            }
            iconExpand.setVisible(true);
            iconMinus.setVisible(false);
        });

        iconClose.addClickListener(event -> {
            isShown = false;
            if (!isFromButton) {
                result.setResult(ConfirmResult.Result.CANCEL);
                cancelButtonClicked();
                if (callBack != null) {
                    callBack.done(result);
                }
            }
            dialog.close();
        });
        iconClose.getStyle().set("height", "16px").set("color", "#757575").set("float", "right");
        hlHeader.add(lbCaption, iconExpand, iconMinus, iconClose);
        iconMinus.setVisible(false);
        // Content
        VerticalLayout dialogContent = new VerticalLayout();
        dialogContent.setSizeFull();
        dialogContent.getStyle().set("padding", "15px 20px");
        dialogContent.add(getDialogContent());
        dialogContent.getElement().getStyle().set("overflow", "auto");

        // Footer
        buttonLayout.setVisible(!contentMode);
        buttonLayout.setWidthFull();
        buttonLayout.add(btnCancel, btnOK);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);
        buttonLayout.setAlignItems(Alignment.END);
        buttonLayout.getStyle().set("padding", "5px 20px 10px");

        root.add(hlHeader, dialogContent, new Hr(), buttonLayout);
        root.expand(dialogContent);
        root.setHorizontalComponentAlignment(Alignment.START, hlHeader);
        root.setHorizontalComponentAlignment(Alignment.END, buttonLayout);
        root.setJustifyContentMode(JustifyContentMode.BETWEEN);
        dialog.add(root);
        btnOK.setId("btn_ok");
        btnCancel.setId("btn_cancel");
        initUIData();
    }

    protected abstract void okButtonClicked() throws Exception;

    @Override
    public boolean onOKButtonClicked() {
        boolean runResult = true;
        try {
            okButtonClicked();
            if (callBack != null) {
                result.setResult(ConfirmResult.Result.OK);
                callBack.done(result);
            }
            isFromButton = true;

            if (!contentMode) {
                dialog.close();
            }
        } catch (Exception e) {
            if (!(e instanceof ValidationException)) {
                isFromButton = false;

                NotificationUtils.notificationError(e.getMessage());

                if (callBack != null) {
                    result.setResult(ConfirmResult.Result.ERROR);
                    callBack.done(result);
                }
                runResult = false;
            }
        }
        return runResult;
    }

    protected abstract void cancelButtonClicked();

    @Override
    public void onCancelButtonClicked() {
        cancelButtonClicked();
        isFromButton = true;
        if (callBack != null) {
            result.setResult(ConfirmResult.Result.CANCEL);
            callBack.done(result);
        }
        if (!contentMode) {
            dialog.close();
        }
    }

    protected abstract Component getDialogContent();

    public void showDialog(String Caption, String width, String height, boolean resizable, boolean draggable,
                           DialogCallBack callBack) {
        this.width = width;
        this.height = height;
        showDialog(Caption, width, height, resizable, draggable, true, callBack);
    }

    public void showDialog(String Caption, String width, String height, boolean resizable, boolean draggable, boolean modal,
                           DialogCallBack callBack) {

        this.width = width;
        this.height = height;
        iconExpand.setVisible(resizable);
        if (!isInitialized) {
            init();
            isInitialized = true;
        }

        lbCaption.setText(Caption);
        this.callBack = callBack;
        buttonLayout.setVisible(!contentMode);
        if (height != null) {
            dialog.setHeight(height);
        }
        dialog.setWidth(width);
        dialog.setResizable(resizable);
        dialog.setDraggable(draggable);
        dialog.setCloseOnOutsideClick(false);
        dialog.setModal(modal);
        if (draggable) {
            dialog.setModal(true);
            hlHeader.getElement().getClassList().add("draggable");
        }
        dialog.addDialogCloseActionListener(event -> {
            isShown = false;
            if (!isFromButton) {
                result.setResult(ConfirmResult.Result.CANCEL);
                cancelButtonClicked();
                if (callBack != null) {
                    callBack.done(result);
                }
            }
            dialog.close();
        });
        isShown = true;
        dialog.open();
    }

    @Override
    public boolean isShown() {
        return isShown;
    }

    @Override
    public void setContentMode(boolean contentMode) {
        this.contentMode = contentMode;
    }

    @Override
    public void close() {
        this.dialog.close();
    }

    protected void unDisplayCancelButton() {
        btnCancel.setVisible(false);
    }

    protected void setOKButtonCaption(String caption) {
        btnOK.setText(caption);
    }

    protected void setOKButtonVisible(Boolean vist) {
        btnOK.setVisible(vist);
    }

    protected void setCancelButtonCaption(String caption) {
        btnCancel.setText(caption);
    }

    protected void setCancelButtonVisible(Boolean vist) {
        btnCancel.setVisible(vist);
    }

    protected void setIconCloseVisible(boolean visible) {
        iconClose.setVisible(visible);
    }
}
