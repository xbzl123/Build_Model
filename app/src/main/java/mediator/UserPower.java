package mediator;


public class UserPower extends College{
    public UserPower(Mediator mediator) {
        super(mediator);
    }

    public void getPower(){
        System.out.println("This is a easy thing to get power");
    }

    public void buyHouse(){
        super.mediator.doSomeThing2();
    }
}
