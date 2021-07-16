package dutychain;

public class DeBug extends LogHandler {
    public DeBug(LogLevel level){
        this.level = level;
    }
    @Override
    public void writeMessage(String msg) {
        System.out.println("debug-----"+msg);
    }
}
