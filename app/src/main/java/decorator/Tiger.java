package decorator;

public class Tiger extends Decoration{
    public Tiger(Animal animal) {
        super(animal);
    }

    @Override
    public void change() {
        System.out.println("switch to a tiger");
    }
}
