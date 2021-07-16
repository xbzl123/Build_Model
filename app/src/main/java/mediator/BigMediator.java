package mediator;

public class BigMediator extends Mediator{
    @Override
    public void doSomeThing1() {
        super.userRich.getMemony();
        super.userPower.getPower();
        System.out.println("Mediator is getting gun over");
    }

    @Override
    public void doSomeThing2() {
        super.userPower.getPower();
        super.userRich.getMemony();
        System.out.println("Mediator is getting house over");
    }
}
