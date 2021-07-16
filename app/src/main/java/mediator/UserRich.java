package mediator;

public class UserRich extends College{
    public UserRich(Mediator mediator) {
        super(mediator);
    }

    public void getMemony(){
        System.out.println("This is a easy thing to getMemony");
    }
    public void buyCar(){
        System.out.println("This is a easy thing to buy car");
    }

    public void buyGun(){
        super.mediator.doSomeThing1();
    }
}
