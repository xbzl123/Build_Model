package decorator;

public class Bird extends Decoration{


    public Bird(Animal animal) {
        super(animal);
    }

    @Override
    public void change() {
        System.out.println("switch to a bird");

    }
}
