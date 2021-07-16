package backstore;

public class Test {
    public static void main(String[] args){
        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.playGame();
        Manager manager = new Manager();
        manager.setBackStore(gamePlayer.createBackStore());
        gamePlayer.playGame();
        gamePlayer.resetBackStore(manager.getBackStore());
    }
}
