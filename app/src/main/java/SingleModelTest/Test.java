package SingleModelTest;

public class Test {
    public static void main(String[] args){
        //懒汉双重判断单例验证
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println("懒汉双重判断单例验证 is single success ? :"+(singleton1 == singleton2));


        //静态内部类单例验证
        Singleton singleton3 = Singleton.getSingleton();
        Singleton singleton4 = Singleton.getSingleton();
        System.out.println("静态内部类单例验证 is single success ? :"+(singleton3 == singleton4));


        //枚举单例验证
        Singleton.SingletonEnum singleton5 = Singleton.SingletonEnum.INSTANCE;
        Singleton.SingletonEnum singleton6 = Singleton.SingletonEnum.INSTANCE;
        System.out.println("枚举单例验证 is single success ? :"+(singleton5.equals(singleton6)));
    }
}
