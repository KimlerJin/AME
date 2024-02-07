package com.ame.entity;


import com.ame.annotation.Description;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Description("菜单维护表")
@Entity
@Table(name = "SYS_MENU_NODE")
@Cacheable
public class MenuNodeEntity extends BaseEntity {

    public static final String NAME = "name";
    public static final String NODE_PATH = "path";
    public static final String PARENT_ID = "parentId";
    public static final String VIEW_CLASS = "viewClass";
    public static final String SEQUENCE = "sequence";
    public static final String TYPE = "type";
    public static final String IS_GROUP = "isGroup";
    public static final String PAGE_ID = "pageId";

    private static final long serialVersionUID = -1729425971496069663L;

    @Description("菜单父节点Path")
    @Column(name = "PARENT_ID")
    private Long parentId;

    @Description("菜单")
    @Column(name = "PATH")
    private String path;

    @Description("菜单")
    @Column(name = "NAME")
    private String name;

    @Description("对象I18NText中的ID")
    @Column(name = "NAME_I18N_ID")
    private Long nameI18nTextId;

    @Description("iconPath")
    @Column(name = "ICON_PATH")
    private String iconPath;

    @Description("描述对应的对象I18NText中的textName")
    @Column(name = "DES_I18N_ID")
    private Long desI18nTextId;

    @Description("backgroundPath")
    @Column(name = "BACKGROUND_PATH")
    private String backgroundPath;

    @Description("排序")
    @Column(name = "SEQUENCE")
    private Integer sequence;

    @Description("是否显示")
    @Column(name = "IS_SHOW")
    private boolean show = true;

    @Description("配置页面的ID")
    @Column(name = "PAGE_ID")
    private Long pageId;

    @Description("代码写好的PageView页面viewClass对应的Class全名")
    @Column(name = "VIEW_CLASS")
    private String viewClass;

    @Column(name = "LAYOUT_CLASS")
    private String layoutClass;

    @Description("是否系统定义")
    @Column(name = "IS_SYSTEM")
    private boolean isSystem = false;

    @Description("Permission 配置的名字")
    @Column(name = "PERMISSION")
    private String permission;


    @Description("是否菜单组")
    @Column(name = "IS_GROUP")
    private boolean isGroup;

    public Long getParentId() {
        return parentId == null ? -1 : parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLayoutClass() {
        return layoutClass;
    }

    public void setLayoutClass(String layoutClass) {
        this.layoutClass = layoutClass;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getNameI18nTextId() {
        return nameI18nTextId == null ? -1 : nameI18nTextId;
    }

    public void setNameI18nTextId(Long nameI18nTextId) {
        this.nameI18nTextId = nameI18nTextId;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Long getDesI18nTextId() {
        return desI18nTextId == null ? -1 : desI18nTextId;
    }

    public void setDesI18nTextId(Long desI18nTextId) {
        this.desI18nTextId = desI18nTextId;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public Long getPageId() {
        return pageId == null ? -1 : pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getViewClass() {
        return viewClass;
    }

    public void setViewClass(String viewClass) {
        this.viewClass = viewClass;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean system) {
        this.isSystem = system;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permissionConfigName) {
        this.permission = permissionConfigName;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean group) {
        isGroup = group;
    }
}
