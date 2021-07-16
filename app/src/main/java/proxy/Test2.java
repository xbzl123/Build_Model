package proxy;

import java.lang.reflect.Proxy;

public class Test2 {
    public static void main(String[] args){
        IGame player = new Player("zhang san");
        DyHandler dyHandler = new DyHandler(player);
        IGame proxy = (IGame)dyHandler.getProxy();
//        IGame proxy = (IGame)Proxy.newProxyInstance(player.getClass().getClassLoader(),
//                player.getClass().getInterfaces(),dyHandler);
        proxy.playgame();
//        player.playgame();
    }
}
