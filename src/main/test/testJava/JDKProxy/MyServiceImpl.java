package testJava.JDKProxy;

/**
 * 接口实现类, 作为代理的目标对象
 */
public class MyServiceImpl implements MyService {
    @Override
    public void doSomething() {
        System.out.println("做一些事...");
    }
}
