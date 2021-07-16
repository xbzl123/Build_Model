package proxy;

public class Player implements IGame{
    String name;
    public Player(String name){
        this.name = name;
    }
    @Override
    public void playgame() {
        System.out.println(name+" is playing game.");
    }
}
