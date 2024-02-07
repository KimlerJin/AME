package com.ame.views;


import com.ame.core.RequestInfo;
import com.ame.views.about.AboutView;
import com.ame.views.helloworld.HelloWorldView;
import com.ame.views.index.IndexView;
import com.ame.views.node.MenuNodeSettingView;
import com.ame.views.test.UsernamePasswordView;
import com.ame.views.upload.UploadTestView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.apache.shiro.SecurityUtils;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");
        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Avatar avatar = new Avatar(RequestInfo.current().getUserName());
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        HorizontalLayout hOuter = new HorizontalLayout();
        hOuter.setWidthFull();
        hOuter.add(menuBar);
        hOuter.setPadding(true);
        hOuter.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        MenuItem menuItem = menuBar.addItem(avatar);
        SubMenu subMenu = menuItem.getSubMenu();
        subMenu.addItem("Profile");
        subMenu.addItem("Settings");
        subMenu.addItem("Help");
        subMenu.addItem("Sign out", (ComponentEventListener<ClickEvent<MenuItem>>) menuItemClickEvent -> {
            MenuItem source = menuItemClickEvent.getSource();
            if (source != null) {
                SecurityUtils.getSubject().logout();
            }

        });


        addToNavbar(true, toggle, viewTitle, hOuter);
    }

    private void addDrawerContent() {
        H1 appName = new H1("My App");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);
        Scroller scroller = new Scroller(createNavigation());
        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        SideNavItem helloWorld = new SideNavItem("Hello World");
        helloWorld.addItem(new SideNavItem("Upload", UploadTestView.class, LineAwesomeIcon.FILE.create()));
        nav.addItem(helloWorld);
        nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.FILE.create()));
        nav.addItem(new SideNavItem("UsernamePassword", UsernamePasswordView.class, LineAwesomeIcon.ADDRESS_BOOK.create()));
        nav.addItem(new SideNavItem("Index", IndexView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
        nav.addItem(new SideNavItem("MenuNodeSettingView", MenuNodeSettingView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
