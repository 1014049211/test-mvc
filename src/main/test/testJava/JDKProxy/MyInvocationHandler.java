package testJava.JDKProxy;

import org.springframework.lang.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Tips 实现 InvocationHandler 接口可以自定义代理增强器, 此处模拟 Spring 的切面定义几个方法
 */
public class MyInvocationHandler implements InvocationHandler {

    /**
     * 要进行代理的目标对象
     */
    private Object target;

    /**
     * 构造
     *
     * @param target 目标对象
     */
    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 生产代理对象
     *
     * @return 代理对象
     */
    public Object getProxy() {
        // 通过当前使用的类加载器、目标对象的接口类型和当前代理增强器来生成一个代理对象
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                this.target.getClass().getInterfaces(), this);
    }

    /**
     * 代理对象对于原始对象的调用
     *
     * @param proxy  代理类代理的真实代理对象 com.sun.proxy.$Proxy0 TODO 有时间好好研究一下
     * @param method 目标对象的方法
     * @param args   目标对象的参数
     */
    @Override
    @Nullable
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 调用前置方法
        this.before();

        // 执行目标对象的方法
        Object result;
        try {
            // 得到返回值
            result = method.invoke(this.target, args);
        } catch (Throwable throwable) {
            // 如果发生了异常则调用异常处理方法
            this.afterThrowing(throwable);
            // 处理后继续抛出
            throw throwable;
        }

        // 调用处理返回值的方法
        this.afterReturning(result);

        // 调用后置方法
        this.after();

        // 将返回值返回
        return result;
    }

    /**
     * 前置方法
     */
    public void before() {
        System.out.println("这是前置方法");
    }

    /**
     * 后置方法
     */
    public void after() {
        System.out.println("这是后置方法");
    }

    /**
     * 处理返回值的方法
     *
     * @param result 返回值
     */
    public void afterReturning(@Nullable Object result) {
        System.out.println("这是返回值处理方法得到的返回值: " + result);
    }

    /**
     * 处理异常的方法
     *
     * @param throwable 异常
     */
    public void afterThrowing(Throwable throwable) {
        System.out.println("这是异常处理方法得到的异常: " + throwable.getMessage());
    }
}
