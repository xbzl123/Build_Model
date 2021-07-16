package backstore;

public class GamePlayer {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void playGame(){
        System.out.println("play game!");
    }

    public BackStore createBackStore(){
        System.out.println("save game!");

        return new BackStore(status);
    }
    public BackStore resetBackStore(BackStore backStore){
        System.out.println("restore game!");
        return new BackStore(backStore.getStatus());
    }
}
