package TestDemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//代理类，可以由这里获取到这个类的接口代理实例对象，反射
public class ProxyStudent implements InvocationHandler{
    private Object target;

    //传入一个对象获取它的接口代理实例
    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before .....");
        Object obj = method.invoke(target,args);
        System.out.println("after .....");

        return obj;
    }

}
