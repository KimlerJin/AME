package com.ame.base;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * 菜单设置中icon combobox 的项目 render
 */
public class IconItemRender extends Composite<HorizontalLayout> {

    public IconItemRender(String  imagePath) {
        HorizontalLayout vlInfo = new HorizontalLayout();
        Label label = new Label(imagePath);
        Avatar icon = new Avatar();
        icon.setImage(imagePath);
        vlInfo.add(icon);
        vlInfo.add(label);
        vlInfo.setAlignItems(FlexComponent.Alignment.CENTER);
        getContent().add(vlInfo);
    }
}
