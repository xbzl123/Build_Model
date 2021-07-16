package decorator;

public abstract class Decoration implements Animal{
    Animal animal = null;
    public Decoration(Animal animal){
        this.animal = animal;
    }

    @Override
    public void change() {
        this.animal.change();
    }
}
