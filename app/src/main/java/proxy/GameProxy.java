package proxy;

public class GameProxy implements IGame{
    IGame iGame;
    public GameProxy(IGame iGame){
        this.iGame = iGame;
    }

    @Override
    public void playgame() {
        this.iGame.playgame();
    }
}
