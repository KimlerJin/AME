package com.ame.enums;


/**
 * 用户登录类型
 *
 * @author andrew_he
 * @date 2019/08/30
 */
public enum AuthType {
    /**
     * 
     */
    SYSTEM("SYSTEM", "system.login"),

    LDAP("LDAP", "ldap.login"),

    CAS("CAS", "cas.login"),

    EMAIL("EMAIL","email.login"),

    ACCESS_TOKEN("ACCESS_TOKEN", "access.token.login");

    private String name;

    private String nameKey;

    AuthType(String name, String nameKey) {
        this.name = name;
        this.nameKey = nameKey;
    }

    public static AuthType getValue(String name) {
        for (AuthType type : AuthType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getNameI18NKey() {
        return nameKey;
    }
}
