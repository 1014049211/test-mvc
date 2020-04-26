package com.dx.test.framework.base.constant;

import org.springframework.lang.Nullable;

/**
 * 性别枚举类
 */
public enum GenderEnum {

    FEMALE("女", "0"),
    MALE("男", "1"),
    SHE_MALE("人妖", "2");

    /**
     * 含义
     */
    private String name;

    /**
     * 码值
     */
    private String code;

    GenderEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * 根据码值获得枚举
     *
     * @param code 码值
     * @return ReturnCodeEnum, 找不到时返回 null
     */
    @Nullable
    public static GenderEnum getEnumByCode(String code) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getCode().equals(code)) {
                return genderEnum;
            }
        }
        return null;
    }

    /**
     * 根据含义获得枚举
     *
     * @param name 含义
     * @return ReturnCodeEnum, 找不到时返回 null
     */
    @Nullable
    public static GenderEnum getEnumByName(String name) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getName().equals(name)) {
                return genderEnum;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
