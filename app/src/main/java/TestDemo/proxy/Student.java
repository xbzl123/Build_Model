package TestDemo.proxy;

public class Student implements Study{
    @Override
    public void study() {
        System.out.println("study .....");
    }

    public void eat(){
        System.out.println("eat .....");
    }
}
