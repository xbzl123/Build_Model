package dutychain;

public class Error extends LogHandler {
    public Error(LogLevel level){
        this.level = level;
    }
    @Override
    public void writeMessage(String msg) {
        System.out.println("error-----"+msg);
    }
}
