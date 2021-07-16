package mediator;

public abstract class Mediator {
    protected UserRich userRich;
    protected UserPower userPower;

    public abstract void doSomeThing1();
    public abstract void doSomeThing2();

    public UserRich getUserRich() {
        return userRich;
    }

    public void setUserRich(UserRich userRich) {
        this.userRich = userRich;
    }

    public UserPower getUserPower() {
        return userPower;
    }

    public void setUserPower(UserPower userPower) {
        this.userPower = userPower;
    }
}
