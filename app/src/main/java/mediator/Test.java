package mediator;

public class Test {
    public static void main(String[] args){
        BigMediator bigMediator = new BigMediator();
        UserPower userPower = new UserPower(bigMediator);
        UserRich userRich = new UserRich(bigMediator);
        bigMediator.setUserPower(userPower);
        bigMediator.setUserRich(userRich);
        userRich.buyGun();
        userPower.buyHouse();
    }
}
