package manybackstore.backstore;

import java.util.HashMap;

public class GamePlayer {
    private String status1;
    private String status2;
    private String status3;

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getStatus3() {
        return status3;
    }

    public void setStatus3(String status3) {
        this.status3 = status3;
    }

    public void playGame(){
        System.out.println("play game!");
    }

    public BackStore createBackStore(HashMap<String,Object> status){
        System.out.println("save game!");

        return new BackStore(status);
    }
    public BackStore resetBackStore(BackStore backStore){
        System.out.println("restore game!");
        return new BackStore(backStore.getStatus());
    }
}
