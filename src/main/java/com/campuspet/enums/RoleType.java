package com.campuspet.enums;

public enum RoleType {
    USER("普通用户"),
    VOLUNTEER("志愿者"),
    ADMIN("管理员");

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static RoleType fromDisplayName(String displayName) {
        for (RoleType value : values()) {
            if (value.getDisplayName().equals(displayName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("未知角色：" + displayName);
    }
}
