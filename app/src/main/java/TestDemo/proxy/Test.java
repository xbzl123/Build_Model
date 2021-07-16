package TestDemo.proxy;

//不用直接操作Student的对象，获取它的接口代理对象来调用Student里面的方法
public class Test {
    public static void main(String[] args){
        ProxyStudent proxyStudent = new ProxyStudent();
        Study proxy = (Study) proxyStudent.bind(new Student());
        proxy.study();
    }
}
