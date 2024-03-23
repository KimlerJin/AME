package com.ame.views.user;

import com.ame.base.BaseView;
import com.ame.base.ConfirmDialog;
import com.ame.base.ConfirmResult;
import com.ame.base.DialogCallBack;
import com.ame.core.RequestInfo;
import com.ame.entity.UserEntity;
import com.ame.service.IUserService;
import com.ame.views.MainLayout;
import com.google.common.collect.Lists;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "user", layout = MainLayout.class)
public class UserView extends BaseView {


    private Button btnAdd = new Button("新建");

    private Button btnEdit = new Button("编辑");

    private Button btnDelete = new Button("删除");

    private Grid<UserEntity> grid = new Grid<>();

    @Autowired
    private IUserService userService;

    private List<UserEntity> userEntityList = Lists.newArrayList();

    @Override
    protected void initView() {
        VerticalLayout vRoot = new VerticalLayout();
        HorizontalLayout hTool = new HorizontalLayout();
        hTool.add(btnAdd, btnEdit, btnDelete);
        vRoot.add(hTool);
        grid.setSizeFull();
        vRoot.add(grid);
        grid.addColumn(UserEntity::getUserName).setHeader("用户名");
        grid.addColumn(UserEntity::getStatus).setHeader("状态");
        grid.addColumn(UserEntity::getCreateUserName).setHeader("创建人");
        grid.addColumn(new ValueProvider<UserEntity, String>() {
            @Override
            public String apply(UserEntity userEntity) {
                ZonedDateTime createTime = userEntity.getCreateTime();
                String format = createTime.withZoneSameInstant(RequestInfo.current().getUserZoneId()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                String format = createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return format;
            }
        }).setHeader("创建时间");
        grid.addColumn(
                new ComponentRenderer<>(HorizontalLayout::new, (horizontalLayout, person) -> {
                    Button btnDelete = new Button();
                    btnDelete.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    btnDelete.addClickListener(e -> this.removeInvitation(person));
                    btnDelete.setIcon(new Icon(VaadinIcon.TRASH));

                    Button btnEdit = new Button();
                    btnEdit.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    btnEdit.addClickListener(e -> this.removeInvitation(person));
                    btnEdit.setIcon(new Icon(VaadinIcon.EDIT));
                    horizontalLayout.add(btnEdit);
                    horizontalLayout.add(btnDelete);


                })).setHeader("操作");
        vRoot.setSizeFull();
        setCompositionRoot(vRoot);
    }

    @Override
    protected void loadData() {
        userEntityList = userService.listByFilter(userService.createFilter());
        grid.setItems(userEntityList);
    }


    private void editUser(UserEntity user) {

    }

    private void removeInvitation(UserEntity user) {
        ConfirmDialog.show("确认删除？", new DialogCallBack() {
            @Override
            public void done(ConfirmResult result) {
                if (result.isOK()) {
                    userService.delete(user);
                    userEntityList.remove(user);
                    refreshGrid();
                }
            }
        });

    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

    @Override
    public String getPageTitle() {
        return "用户界面";
    }
}
