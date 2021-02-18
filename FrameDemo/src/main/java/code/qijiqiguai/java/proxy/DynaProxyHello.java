package code.qijiqiguai.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wq
 */
public class DynaProxyHello implements InvocationHandler {
    private Object delegate;

    public Object bind(Object delegate) {
        this.delegate = delegate;
        return Proxy.newProxyInstance(
                this.delegate.getClass().getClassLoader(), this.delegate
                        .getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result = null;
        try {
            System.out.println("问候之前的日志记录...");
            // JVM通过这条语句执行原来的方法(反射机制)
            result = method.invoke(this.delegate, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    static public void main(String[] arg) {
        //DynaProxyHello的作用就是实现InvocationHandler接口实现代理功能，并在DynaProxyHello方法的invoke中去实现自己需要的业务功能
        DynaProxyHello helloproxy = new DynaProxyHello();
        HelloImpl hello = new HelloImpl();
        //将hello设置进 代理类中，实现在此作用域中Helloimplements实例hello的所有调用都会经过DynaProxyHello代理转发
        IHello ihello = (IHello) helloproxy.bind(hello);
        ihello.sayHello("Jerry");
    }
}
