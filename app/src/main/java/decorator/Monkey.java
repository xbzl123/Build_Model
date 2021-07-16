package decorator;

public class Monkey implements Animal{
    @Override
    public void change() {
        System.out.println("switch to a monkey");
    }
}
