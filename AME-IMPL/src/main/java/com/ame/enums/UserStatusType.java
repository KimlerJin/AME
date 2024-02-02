package com.ame.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;

public enum UserStatusType  {

    ACTIVE("Active", "common.active"), INACTIVE("Inactive", "Common.Invalid"), LOCKED("Locked", "common.locked");

    private String name;
    private String nameKey;

    private UserStatusType(String name, String nameKey) {
        this.name = name;
        this.nameKey = nameKey;
    }

    public static UserStatusType getValue(String name) {
        for (UserStatusType c : UserStatusType.values()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public static String[] getValues() {
        List<String> values = new ArrayList<String>();
        for (UserStatusType c : UserStatusType.values()) {
            values.add(c.getName());
        }
        return (String[])values.toArray(new String[] {});
    }

    @JsonCreator
    @JsonValue
    public String getName() {
        return name;
    }

    public String getNameKey() {
        return nameKey;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getNameI18NKey() {
        return nameKey;
    }
}
