package com.ame.base;


import com.ame.entity.MenuNodeEntity;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Author:Tracy Date:2021/4/6 Description:
 */
@SpringComponent
@Scope("prototype")
public class MenuNodeBatchConfigDialog extends BaseDialog {

    private static final long serialVersionUID = -4137142866923332952L;

    @Autowired
    private MenuConfigDialogPresenter presenter;

    private TreeGrid<MenuNodeEntity> menuTreeGrid = new TreeGrid();
    private TreeData<MenuNodeEntity> treeData = new TreeData<>();


    @Override
    protected void okButtonClicked() throws Exception {
        List<MenuNodeEntity> roots = treeData.getRootItems();
        Set<MenuNodeEntity> results = new HashSet<>();
        results.addAll(roots);
        for (MenuNodeEntity menuNode : roots) {
            results.addAll(getSubItemFromTreeData(menuNode));
        }
    }

    @Override
    protected void cancelButtonClicked() {

    }

    @Override
    protected Component getDialogContent() {
        // menuTreeGrid.setSizeFull();
        menuTreeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        menuTreeGrid.addHierarchyColumn(MenuNodeEntity::getName).setHeader( "Name");
        menuTreeGrid.addComponentColumn(item -> {
            TextField textField = new TextField();
            textField.setWidthFull();
            textField.setValue(item.getDescription() == null ? "" : item.getDescription());
            textField.addValueChangeListener(event -> {
                if (event.isFromClient()) {
                    item.setDescription(event.getValue());
                }
            });
            return textField;
        }).setHeader( "Description");

        return menuTreeGrid;
    }



    @Override
    protected void initUIData() {

    }

    public void setTreeData(TreeData<MenuNodeEntity> treeData) {
        this.treeData = treeData;
        menuTreeGrid.setDataProvider(new TreeDataProvider<>(treeData));
    }

    /**
     * 找到treegrid里的所有subitem， 封装成list
     *
     * @param menuNode
     * @return
     */
    private List<MenuNodeEntity> getSubItemFromTreeData(MenuNodeEntity menuNode) {
        List<MenuNodeEntity> results = new ArrayList<>();
        List<MenuNodeEntity> childMenus = treeData.getChildren(menuNode);
        if (CollectionUtils.isNotEmpty(childMenus)) {
            results.addAll(childMenus);
            for (MenuNodeEntity node : childMenus) {
                results.addAll(getSubItemFromTreeData(node));
            }
        }
        return results;
    }

    @Override
    public void show(DialogCallBack callBack) {
        String caption = "Set Batch";
        showDialog(caption, "90%", "100%", true, true, callBack);
    }
}
