package dutychain;

public class Info extends LogHandler {
    public Info(LogLevel level){
        this.level = level;
    }
    @Override
    public void writeMessage(String msg) {
        System.out.println("info-----"+msg);
    }
}
