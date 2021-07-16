package decorator;

public class Test {

    public static void main(String[] args){
        System.out.println("before learned skill");
        Animal animal = new Monkey();
        animal.change();
        System.out.println("after learned skill");
        Animal animal1 = new Tiger(animal);
        animal1.change();
        animal1 = new Bird(animal);
        animal1.change();
    }
}
