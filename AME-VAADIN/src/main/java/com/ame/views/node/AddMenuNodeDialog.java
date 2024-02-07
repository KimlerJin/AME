package com.ame.views.node;

import com.ame.base.*;
import com.ame.core.exception.PlatformException;
import com.ame.entity.MenuNodeEntity;
import com.ame.service.MenuNodeService;
import com.ame.spring.BeanManager;
import com.google.common.collect.Lists;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:Tracy Date:2021/4/2 Description:
 */
@SpringComponent
@Scope("prototype")
public class AddMenuNodeDialog extends BaseDialog {

    private static final long serialVersionUID = 4924234596278587170L;

    private TextField tfName = new TextField("名称");

    private TextField tfPath = new TextField("路径");

    private TextField tfCategory = new TextField("类型");

    private ComboBox<String> cbIconPath = new ComboBox("图标路径");

    private ComboBox<String> cbBackgroundPath = new ComboBox("背景图片");

    private ComboBox<String> cbViewClass = new ComboBox("View Class");

    private TextField cbLayoutClass = new TextField("Layout Class");

    private IntegerField tfOrder = new IntegerField("Order");


    private TextArea cADescription = new TextArea("Description");

    private Checkbox cbIsShow = new Checkbox("Is Show",true);

    private ComboBox<MenuNodeEntity> cbMenuNode = new ComboBox("Menu node");

    private List<String> ignoreMenus = Lists.newArrayList("Runtime", "MobileRuntime", "Preview", "Mobile Preview", "Designer");

    private Component[] fields = {tfName, tfPath, tfCategory, cbIconPath, cbBackgroundPath, cbViewClass, cbLayoutClass, tfOrder, cbMenuNode, cADescription, cbIsShow};

    private Binder<MenuNodeEntity> binder = new Binder<>();

    private MenuNodeEntity menuNode;


    @Autowired
    private AddMenuNodeDialogPresenter dialogPresenter;


    @Autowired
    private MenuNodeService menuNodeService;

    @Override
    protected void okButtonClicked() throws Exception {
        binder.writeBean(menuNode);
        if (menuNode.getPageId() > 0 && StringUtils.isNotEmpty(menuNode.getViewClass())) {
            throw new PlatformException("Design Page 和 View Class只能选一个");
        }
        boolean exist = dialogPresenter.checkViewOrPageExist(menuNode);
        if (exist) {
            throw new PlatformException("Design Page or View Class has been Used by other menu");
        }
        //强行加上这段校验，不知道为啥创建页面按钮权限的时候，默认直接拿的是release的content，所以这边就只能统一了

        dialogPresenter.save(menuNode);

        result.setResult(ConfirmResult.Result.OK);
        result.setObj(menuNode);

    }

    @Override
    protected void cancelButtonClicked() {

    }

    @Override
    protected Component getDialogContent() {
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("1em", 2));
        form.setSizeFull();
        for (Component field : fields) {
            if (field instanceof HasSize) {
                ((HasSize) field).setWidth("100%");
            }
            if (field.isVisible()) {
                form.add(field);
            }
        }
        form.setSizeFull();
        return form;
    }

    @Override
    protected void initUIData() {
        binder.forField(tfName).withValidator(new Validator<String>() {

            private static final long serialVersionUID = 3148886746678512412L;

            @Override
            public ValidationResult apply(String value, ValueContext context) {
                String tfValue = value == null ? "" : value;
                if (!tfValue.trim().isEmpty() && !tfValue.contains("-")) {
                    return ValidationResult.ok();
                } else {
                    return ValidationResult.error( "Required filed cannot contains '-'");
                }
            }
        }).asRequired("Required filed cannot be empty").bind(MenuNodeEntity::getName, MenuNodeEntity::setName);
        binder.forField(tfPath).withValidator(new RegexpValidator("路径只能是英文或数字", "^[a-zA-Z0-9]+")).asRequired("Required filed cannot be empty").bind(MenuNodeEntity::getPath, MenuNodeEntity::setPath);

        binder.bind(cbIconPath, MenuNodeEntity::getIconPath, MenuNodeEntity::setIconPath);
        binder.bind(cbBackgroundPath, MenuNodeEntity::getBackgroundPath, MenuNodeEntity::setBackgroundPath);
        binder.bind(cbViewClass, MenuNodeEntity::getViewClass, MenuNodeEntity::setViewClass);
        binder.bind(cbLayoutClass, MenuNodeEntity::getLayoutClass, MenuNodeEntity::setLayoutClass);
        binder.bind(tfOrder, MenuNodeEntity::getSequence, MenuNodeEntity::setSequence);
        binder.bind(cbIsShow, MenuNodeEntity::isShow, MenuNodeEntity::setShow);
        binder.bind(cADescription, MenuNodeEntity::getDescription, MenuNodeEntity::setDescription);

        // 设置图标和背景图片的combobox

//        cbIconPath.setRenderer(new ComponentRenderer<IconItemRender, String>() {
//            @Override
//            public IconItemRender createComponent(String item) {
//                return new IconItemRender(item);
//            }
//        });
        cbIconPath.setItems();
//        cbIconPath.setItemLabelGenerator(Object::toString);
        cbIconPath.getStyle().set("--vaadin-combo-box-overlay-width", "450px");
        cbIconPath.setClearButtonVisible(true);


        cbBackgroundPath.setItems();
//        cbBackgroundPath.setItemLabelGenerator(Object::toString);
//        cbBackgroundPath.setRenderer(new ComponentRenderer<BGPicItemRender, String>() {
//            @Override
//            public BGPicItemRender createComponent(String item) {
//                return new BGPicItemRender(item);
//            }
//        });
        cbBackgroundPath.getStyle().set("--vaadin-combo-box-overlay-width", "400px");
        cbBackgroundPath.setClearButtonVisible(true);



        cbViewClass.setClearButtonVisible(true);
        cbViewClass.getStyle().set("--vaadin-combo-box-overlay-width", "800px");
        cbMenuNode.addValueChangeListener(event -> {
            MenuNodeEntity value = event.getValue();
            if (event.isFromClient()) {
                if (value != null) {
                    menuNode.setParentId(value.getId());
                } else {
                    //如果没选则默认是最高级根节点
                    menuNode.setParentId(-1L);
                }
            }
        });
    }

    public void setObject(MenuNodeEntity menuNode, long parentId, Class layoutClass,  boolean isGroup) {
        tfPath.setReadOnly(false);
        if (menuNode != null) {
            menuNode = BeanManager.getService(MenuNodeService.class).getById(menuNode.getId());

        }
        if (menuNode == null) {
            menuNode = new MenuNodeEntity();
            menuNode.setLayoutClass(layoutClass == null ? null : layoutClass.getName());
            menuNode.setParentId(parentId);
            menuNode.setIsGroup(isGroup);
        }
        cbViewClass.setItems(dialogPresenter.getPageViews().stream().map(Class::getName).collect(Collectors.toList()));
        //找到所有的菜单节点
        List<MenuNodeEntity> menuNodes = menuNodeService.listGroup(true).stream().filter(e -> !ignoreMenus.contains(e.getName())).collect(Collectors.toList());
        List<MenuNodeEntity> menuNodeList = splicingMenuNodename(menuNodes);
        cbMenuNode.setItems(menuNodeList);
        cbMenuNode.setItemLabelGenerator(MenuNodeEntity::getPath);
        MenuNodeEntity cbmenuNode = new MenuNodeEntity();
        BeanUtils.copyProperties(menuNode, cbmenuNode, "path", "name");
        cbmenuNode.setName("");
        cbmenuNode.setPath("");
        judgeParent(cbmenuNode, menuNodeList, null);
        cbMenuNode.setValue(cbmenuNode);
        this.menuNode = menuNode;
    }

    //拼接MenuNode的名字到path字段
    private List<MenuNodeEntity> splicingMenuNodename(List<MenuNodeEntity> menuNodes) {
        for (MenuNodeEntity menuNode : menuNodes) {
            if (menuNode.getParentId() > 1L) {
                menuNode.setPath(null);
            } else {
                menuNode.setPath(menuNode.getName());
            }
            judgeParent(menuNode, menuNodes, null);
        }
        return menuNodes;
    }

    private MenuNodeEntity judgeParent(MenuNodeEntity menuNode, List<MenuNodeEntity> menuNodes, MenuNodeEntity parentMenuNode) {
        if (menuNode.getParentId() > 1L) {
            MenuNodeEntity node;
            if (parentMenuNode == null) {
                node = menuNodes.stream().filter(m -> m.getId() == menuNode.getParentId()).findFirst().get();
            } else {
                MenuNodeEntity finalParentMenuNode = parentMenuNode;
                node = menuNodes.stream().filter(m -> m.getId() == finalParentMenuNode.getParentId()).findFirst().get();
            }
            parentMenuNode = node;
            if (StringUtils.isNotEmpty(menuNode.getPath())) {
                menuNode.setPath(node.getName() + "/" + menuNode.getPath());
            } else {
                menuNode.setPath(node.getName() + "/" + menuNode.getName());
            }
            if (node.getParentId() > 1L) {
                judgeParent(menuNode, menuNodes, parentMenuNode);
            }
        }
        return menuNode;
    }

    @Override
    public void show(DialogCallBack callBack) {
        // 菜单组没有icon 菜单没有背景图
        cbBackgroundPath.setVisible(menuNode.getParentId() == -1);
        // 根据不同端 设置背景图片和icon
        String[] iconItems = {};
        String[] backgroundItems = {};

        cbIconPath.setItems(iconItems);
        cbIconPath.setItemLabelGenerator(Object::toString);
        cbBackgroundPath.setItems(backgroundItems);
        cbBackgroundPath.setItemLabelGenerator(Object::toString);

        binder.readBean(menuNode);
        cbIsShow.setVisible(!menuNode.isGroup());


        String menuOrGroup =  "Menu Group";
        if (!menuNode.isGroup()) {
            menuOrGroup =  "Menu";
        }
        String captionName = "";
        if (menuNode == null || menuNode.getId() < 1) {
            captionName = "New";
        } else {
            captionName =  "Edit";
        }
        super.showDialog(captionName, VaadinCommonConstant.LARGE_DIALOG_WIDTH, null, false, true, callBack);
    }
}
