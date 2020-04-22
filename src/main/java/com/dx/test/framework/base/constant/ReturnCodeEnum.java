package com.dx.test.framework.base.constant;

import org.springframework.lang.Nullable;

/**
 * ajax 请求的返回码枚举类
 */
public enum ReturnCodeEnum {

    FAIL("操作失败!", "0"),
    SUCCESS("操作成功!", "1");

    /**
     * 含义
     */
    private String name;

    /**
     * 码值
     */
    private String code;

    ReturnCodeEnum(String name, String code) {
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
    public static ReturnCodeEnum getEnumByCode(String code) {
        for (ReturnCodeEnum returnCodeEnum : ReturnCodeEnum.values()) {
            if (returnCodeEnum.getCode().equals(code)) {
                return returnCodeEnum;
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
}
