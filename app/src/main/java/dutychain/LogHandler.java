package dutychain;

public abstract class LogHandler {
    private LogHandler nextHandler;
    protected LogLevel level;

    public void setNextHandler(LogHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handler(LogLevel logLevel,String msg){
    //level不符合就用对象的里面的nextHandler.handler()，直到符合为止
        if(logLevel == level){
            writeMessage(msg);
        }else{
            this.nextHandler.handler(logLevel,msg);
        }
    }
    public abstract void writeMessage(String msg);
}
