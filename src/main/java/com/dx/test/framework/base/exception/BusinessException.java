package com.dx.test.framework.base.exception;

/**
 * 业务类异常
 * Tips 继承自 RuntimeException 的异常, 在 throw 时, 所在方法既不需要 try-catch 也不用声明异常
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
