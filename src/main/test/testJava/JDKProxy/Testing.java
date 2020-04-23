package testJava.JDKProxy;

public class Testing {

    public static void main(String[] args) {

        // 目标对象的普通调用
        MyService myService = new MyServiceImpl();
        myService.doSomething();

        System.out.println("--------------- 这是一条分割线 ---------------");

        // 初始化代理增强器, 加载目标对象
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(myService);
        // 生成代理对象
        MyService myServiceProxy = (MyService) myInvocationHandler.getProxy();
        // 目标对象的代理调用
        myServiceProxy.doSomething();
    }

}
