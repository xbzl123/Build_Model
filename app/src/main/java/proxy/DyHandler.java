package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DyHandler implements InvocationHandler {
    Object target;

    public DyHandler(Object target){
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //before
        if(method.isAnnotationPresent(Before.class)){
            Before bAnnotation = method.getAnnotation(Before.class);
            Class bClass = bAnnotation.cls();
            Method bMethod = bClass.getDeclaredMethod(bAnnotation.method(),null);
            bMethod.invoke(bClass.newInstance(),null);
        }
        Object obj = method.invoke(target,args);
        //after
        if(method.isAnnotationPresent(After.class)){
            After aAnnotation = method.getAnnotation(After.class);
            Class aClass = aAnnotation.cls();
            Method aMethod = aClass.getDeclaredMethod(aAnnotation.method(),null);
            aMethod.invoke(aClass.newInstance(),null);
        }
        return obj;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),this);
    }
}
