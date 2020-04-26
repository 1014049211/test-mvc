package com.dx.test.framework.base.constant;

import org.springframework.lang.Nullable;

/**
 * ajax 请求的返回码枚举类
 */
public enum ReturnCodeEnum {

    /*
     * Tips 枚举类的优缺点
     *  优点: 枚举类相对于只是 String 类型的常量, 有更多的可操作性, 可以有自己的属性和方法
     *      而且枚举类型安全, 所有可能的值都已罗列, 无需额外的数据校验, 直接 == 判断就可以, 还支持 switch
     *  缺点: 扩展与维护困难, 分布式的系统中各个项目的枚举类可能会因为版本迭代而不统一, 如果是第三方项目返回枚举就更糟糕了
     *  总结: 枚举适合作为参数, 定义接口接收的数据范围, 枚举不适合作为返回值, 因为接收方可能会因为枚举类型的缺失而报错
     */

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

    @Override
    public String toString() {
        return this.getName();
    }
}
