package CloneableTest;

public class Test {
    public static void main(String[] arg){
        Person p1 = new Person();
        p1.setAge(10);
        p1.setName("jack");
        Person p2 = (Person)p1.clone();
        p2.setName("luck");
        System.out.println(p1.print());
        System.out.println(p2.print());

    }
}
