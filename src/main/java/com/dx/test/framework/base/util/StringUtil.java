package com.dx.test.framework.base.util;

import org.springframework.util.StringUtils;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str 支持 Object 类型的字符串
     * @return str 不是 String 类型 || str 为 null || str 为 ""
     */
    public static boolean isEmpty(Object str) {
        // Tips 虽然此处只是调用了 Spring 的方法, 但是意义在于统一工具类,
        //  所有对字符串的工具方法都存放在同一个地方, 方便查找和维护
        return StringUtils.isEmpty(str);
    }

    /**
     * 将异常中的信息转换为字符串
     *
     * @param throwable 异常
     * @return 堆栈信息字符串
     */
    public static String printException(Throwable throwable) {
        if (throwable == null) {
            return "";
        }

        StringBuilder exception = new StringBuilder();
        // 提示信息
        exception.append(throwable.getMessage()).append("\r");
        // 堆栈信息
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            exception.append(stackTraceElement.toString()).append("\r");
        }

        // 如果存在 cause, 递归
        if (throwable.getCause() != null) {
            exception.append(StringUtil.printException(throwable.getCause()));
        }

        return exception.toString();
    }

}
