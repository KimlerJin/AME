package com.ame.views.upload;


import com.ame.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Upload")
@Route(value = "upload", layout = MainLayout.class)
public class UploadTestView  extends HorizontalLayout {

    public UploadTestView(){
        Upload upload = new Upload();
        MultiFileBuffer multiFileBuffer = new MultiFileBuffer();
        upload.setReceiver(multiFileBuffer);
        Button button = new Button("Test");
        button.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                multiFileBuffer.getFiles();
            }
        });




        add(upload,button);
    }
}
