package com.ame.views.node;

import com.ame.base.*;
import com.ame.entity.MenuNodeEntity;
import com.ame.utils.NotificationUtils;
import com.ame.views.MainLayout;
import com.google.common.collect.Lists;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.dnd.EffectAllowed;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Route(value = "MenuNodeSettingView", layout = MainLayout.class)
public class MenuNodeSettingView extends BaseView implements IBaseView, ComponentEventListener<ClickEvent<Button>> {

    private static final long serialVersionUID = -451714841295318495L;
    private List<String> ignoreMenus = Lists.newArrayList("Runtime", "MobileRuntime", "Preview", "Mobile Preview", "Designer");

    HorizontalLayout hlPageHead = new HorizontalLayout();// 右边page Head
    @Autowired
    private AddMenuNodeDialog addMenuNodeDialog;
    @Autowired
    private MenuNodeBatchConfigDialog batchConfigDialog;
    @Autowired
    private MenuNodeSettingPresenter menuNodeSettingPresenter;

    private Button btnAddMenuGroup = new Button("Add Menu Group");

    private Button btnAddMenu = new Button("Add Menu");
    private Button btnDelete = new Button("Delete");
    private Button btnEdit = new Button("Edit");
    private Button btnRefresh = new Button("Refresh");
    private Button btnApply = new Button("Apply Changes");

    private Button btnConfigMenu = new Button("Set I18N and Permission");

    private Button btnConfigPermission = new Button("Permission Setting");

    private TreeGrid<MenuNodeEntity> menuTreeGrid = new TreeGrid();
    private TreeData<MenuNodeEntity> treeData = new TreeData<>();

    private Button[] btns = {btnAddMenuGroup, btnAddMenu, btnEdit, btnDelete, btnRefresh, btnConfigMenu, btnConfigPermission, btnApply};


    private Class<? extends RouterLayout> defaultLayoutClass;

    public MenuNodeSettingView() {
//        this.menuTypeEnum = getMenuType();
        this.defaultLayoutClass = getDefaultLayoutClass();
        btnAddMenuGroup.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAddMenu.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        btnConfigPermission.addThemeName(ThemeVariant.BUTTON_EDIT);
//        btnConfigMenu.addThemeName(ThemeVariant.BUTTON_EDIT);
//        btnDelete.addThemeName(ThemeVariant.BUTTON_DELETE);
//        btnEdit.addThemeName(ThemeVariant.BUTTON_EDIT);
        btnAddMenuGroup.setIcon(new Icon(VaadinIcon.PLUS));
        btnAddMenu.setIcon(new Icon(VaadinIcon.PLUS));
        btnConfigMenu.setIcon(new Icon(VaadinIcon.EDIT));
        btnDelete.setIcon(new Icon(VaadinIcon.TRASH));
        btnEdit.setIcon(new Icon(VaadinIcon.EDIT));
        btnRefresh.setIcon(new Icon(VaadinIcon.REFRESH));
        btnApply.setIcon(new Icon(VaadinIcon.CHECK));
        btnConfigPermission.setIcon(new Icon(VaadinIcon.EDIT));

        hlPageHead.setWidthFull();
        hlPageHead.setSpacing(false);
        hlPageHead.setMargin(false);
        hlPageHead.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        menuTreeGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        menuTreeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        menuTreeGrid.addSelectionListener(selectionEvent -> {
            setButtonStatus();
        });
        menuTreeGrid.addHierarchyColumn(menuNodeEntity -> {

            return menuNodeEntity.getName();
        }).setHeader("NAME");

        menuTreeGrid
                .addColumn(menuNode -> menuNode.getSequence()).setHeader("Order").setResizable(true).setAutoWidth(true);
        menuTreeGrid
                .addColumn(menuNode -> menuNode.getLayoutClass()).setHeader("Layout Class").setResizable(true);
        menuTreeGrid
                .addColumn(menuNode -> menuNode.getViewClass()).setHeader("View Class").setResizable(true);


        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidthFull();

        HorizontalLayout toolbox = new HorizontalLayout();
        toolbox.getStyle().set("margin-bottom", "10px");
        for (Button btn : btns) {
            toolbox.add(btn);
            btn.addClickListener(this);
            btn.setDisableOnClick(true);
        }

        hl.add(toolbox);

        menuTreeGrid.setSizeFull();
        root.add(hl);
        root.add(menuTreeGrid);
        root.expand(hl);
        this.setSizeFull();
        this.setCompositionRoot(root);
        setElementsId();
    }

    @Override
    protected void initView() {
        doInitView();
    }

    @Override
    protected void loadData() {
        menuTreeGrid.setDataProvider(new TreeDataProvider<>(treeData));
        refreshTreeGrid();
        setButtonStatus();
        doLoadData();
    }

    private void refreshSelectedItem(long selectedId) {
        for (MenuNodeEntity rootItem : menuTreeGrid.getTreeData().getRootItems()) {
            MenuNodeEntity item = getSelectedItem(rootItem, selectedId);
            if (item != null) {
                expandParent(item);
                menuTreeGrid.select(item);
            }
        }
    }

    private MenuNodeEntity getSelectedItem(MenuNodeEntity menuNode, long selectedId) {
        if (menuNode.getId() == selectedId) {
            return menuNode;
        }
        for (MenuNodeEntity node : treeData.getChildren(menuNode)) {
            MenuNodeEntity selectedItem = getSelectedItem(node, selectedId);
            if (Objects.isNull(selectedItem)) {
                continue;
            }
            return selectedItem;
        }
        return null;
    }

    private void expandParent(MenuNodeEntity menuNode) {
        if (menuNode != null) {
            menuTreeGrid.expand(menuNode);
            if (menuNode.getParentId() > 0) {
                expandParent(treeData.getParent(menuNode));
            }
        }
    }


    private void refreshTreeGrid() {
        List<MenuNodeEntity> roots = menuNodeSettingPresenter.listRootMenuNode()
                .stream().filter(e -> !ignoreMenus.contains(e.getName())).collect(Collectors.toList());
        treeData.clear();
        treeData.addRootItems(roots);
        for (MenuNodeEntity menuNode : roots) {
            setSubMenItemForTreeData(menuNode);
        }
        menuTreeGrid.deselectAll();
        menuTreeGrid.getDataProvider().refreshAll();
//        menuTreeGrid.expand();
    }

    private void makeDragable(Component com) {
        // 任意组件都可以被拖动
        DragSource<Component> componentDragSource = DragSource.create(com);
        componentDragSource.setEffectAllowed(EffectAllowed.COPY_MOVE);
    }

    private void makeDroppable(Component com) {
        // 任意组件都可以被拖入
        DropTarget<Component> verticalLayoutDropTarget = DropTarget.create(com);
        verticalLayoutDropTarget.addDropListener(event -> {
            Component dragSource = event.getDragSourceComponent().orElse(null);
            if (dragSource == null) {
                return;
            }
            Component dragComponent = event.getComponent();
            String itemId = dragSource.getId().get();
            String parentId = dragComponent.getId().get();
            if (parentId.equals(itemId)) {
                return;
            }
            menuNodeSettingPresenter.dropComponentUpdate(parentId, itemId);
            refreshTreeGrid();
        });
    }

    /**
     * 装treeData的数据
     *
     * @param rootMenu
     */
    private void setSubMenItemForTreeData(MenuNodeEntity rootMenu) {
        List<MenuNodeEntity> subItems = menuNodeSettingPresenter.getMenuSubItems(rootMenu.getId())
                .stream().filter(e -> !ignoreMenus.contains(e.getName())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(subItems)) {
            for (MenuNodeEntity menu : subItems) {
//                if (menu.getName().equals("工厂")) {
//                    continue;
//                }
                treeData.addItem(rootMenu, menu);
                setSubMenItemForTreeData(menu);
            }
        }
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

    private void setElementsId() {
        btnAddMenuGroup.setId("btn_add_menu_group");
        btnAddMenu.setId("btn_add_menu");
        btnConfigMenu.setId("btn_add_permission");
        btnDelete.setId("btn_menu_delete");
        btnRefresh.setId("btn_menu_refresh");
        btnEdit.setId("btn_edit");
        btnApply.setId("btn_apply");
    }

    private void setButtonStatus() {
        Set<MenuNodeEntity> selectMenus = menuTreeGrid.getSelectedItems();
        boolean enable = !CollectionUtils.isEmpty(selectMenus) && (selectMenus.size() <= 1 && !selectMenus.iterator().next().isSystem());
        btnAddMenuGroup.setEnabled(true);
        btnConfigMenu.setEnabled(true);
        btnConfigPermission.setEnabled(true);
        btnAddMenu.setEnabled(true);
        btnEdit.setEnabled(!selectMenus.isEmpty() && selectMenus.size() == 1);
        btnDelete.setEnabled(enable);
    }


    @Override
    public void onComponentEvent(ClickEvent<Button> event) {
        Button button = event.getSource();
        button.setEnabled(true);

        if (btnAddMenuGroup.equals(button)) {
            Set<MenuNodeEntity> selectedItems = menuTreeGrid.getSelectedItems();
            MenuNodeEntity parentMenu = null;
            if (!selectedItems.isEmpty()) {
                if (selectedItems.size() > 1) {
                    NotificationUtils.notificationWarning("只能选择一个菜单组");
                    return;
                }
                parentMenu = menuTreeGrid.getSelectedItems().iterator().next();
            }
            if (parentMenu != null && !parentMenu.isGroup()) {
                NotificationUtils.notificationWarning("只能选择菜单组,不能选择菜单页");
                return;
            }

            addMenuNodeDialog.setObject(null, parentMenu == null ? -1L : parentMenu.getId(),
                    defaultLayoutClass, true);
            addMenuNodeDialog.show(result -> {
                if (ConfirmResult.Result.OK.equals(result.getResult())) {
                    refreshTreeGrid();
                }
            });
        } else if (btnAddMenu.equals(button)) {
            Set<MenuNodeEntity> selectedItems = menuTreeGrid.getSelectedItems();
            MenuNodeEntity parentMenu = null;
            if (!selectedItems.isEmpty()) {
                if (selectedItems.size() > 1) {
                    NotificationUtils.notificationWarning("只能选择一个菜单组");
                    return;
                }
                parentMenu = menuTreeGrid.getSelectedItems().iterator().next();
            }
            if (parentMenu != null && !parentMenu.isGroup()) {
                NotificationUtils.notificationWarning("只能选择菜单组,不能选择菜单页");
                return;
            }

            addMenuNodeDialog.setObject(null, parentMenu == null ? -1L : parentMenu.getId(),
                    defaultLayoutClass, false);
            addMenuNodeDialog.show(result -> {
                if (ConfirmResult.Result.OK.equals(result.getResult())) {
                    refreshTreeGrid();
                    if (result.getObj() != null) {
                        refreshSelectedItem(((MenuNodeEntity) result.getObj()).getId());
                    }
                }
            });

        } else if (btnConfigMenu.equals(button)) {
            batchConfigDialog.setTreeData(treeData);
            batchConfigDialog.show(result -> {
                //不管有没有点击确认都要刷新，因为传入的treeData是内存对象会被更新掉的
                refreshTreeGrid();
            });
        } else if (btnConfigPermission.equals(button)) {
//            permissionBatchConfigDialog.setTreeData(treeData);
//            permissionBatchConfigDialog.show(result -> {
//                //不管有没有点击确认都要刷新，因为传入的treeData是内存对象会被更新掉的
//                refreshTreeGrid();
//            });
        } else if (btnDelete.equals(button)) {
            if (CollectionUtils.isEmpty(menuTreeGrid.getSelectedItems())) {
                NotificationUtils.notificationWarning("Please select one menu at least.");
            } else {
                ConfirmDialog.show(
                        "Are you sure to delete the selected item?",
                        result -> {
                            if (ConfirmResult.Result.OK.equals(result.getResult())) {
                                MenuNodeEntity menuNode = menuTreeGrid.getSelectedItems().iterator().next();
                                Long parentId = menuNode.getParentId();
                                List<MenuNodeEntity> deleteMenus = new ArrayList<>();
                                deleteMenus.add(menuNode);
                                deleteMenus.addAll(getSubItemFromTreeData(menuNode));
                                menuNodeSettingPresenter.deleteMenuNodes(deleteMenus);
                                refreshTreeGrid();
                                refreshSelectedItem(parentId);
                            }
                        });

            }
        } else if (btnRefresh.equals(button)) {
            refreshTreeGrid();
        } else if (btnEdit.equals(button)) {
            MenuNodeEntity selectMenu = menuTreeGrid.getSelectedItems().iterator().next();
            addMenuNodeDialog.setObject(selectMenu, -1L, null, false);
            addMenuNodeDialog.show(result -> {
                if (ConfirmResult.Result.OK.equals(result.getResult())) {
                    refreshTreeGrid();
                    refreshSelectedItem(selectMenu.getId());
                }
            });
        } else if (btnApply.equals(button)) {
//            MenuNodeApplyEventListener.MenuNodeApplyEvent menuNodeApplyEvent = new MenuNodeApplyEventListener.MenuNodeApplyEvent();
//            menuNodeApplyEvent.setType(menuTypeEnum);
//            eventBus.publish(menuNodeApplyEvent);
//            NotificationUtils
//                    .notificationSuccessful(I18NUtility.getValue("menu.apply.chang.success", "Apply Change Successfully"));
        }
    }


    @Override
    public String getPageTitle() {
        return doGetPageTitle();
    }


//    public abstract MenuTypeEnum getMenuType();

    public Class<? extends RouterLayout> getDefaultLayoutClass() {
        return MainLayout.class;
    }

    public String doGetPageTitle() {
        return "Menu Setting";
    }

    public void doLoadData() {

    }

    public void doInitView() {

    }
}
