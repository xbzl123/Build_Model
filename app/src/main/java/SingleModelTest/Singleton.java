package SingleModelTest;

public class Singleton {
    private static volatile Singleton instance = null;
    private Singleton(){

    }

    public static Singleton getInstance() {
        if (instance == null){
          synchronized (Singleton.class){
              if(instance == null){
                  instance = new Singleton();
              }
          }
        }
        return instance;
    }

    //通过静态内部类实现单例模式
    public static Singleton getSingleton(){
        return SingleDemo.singleton;
    }
    private static class SingleDemo{
        private static volatile Singleton singleton = new Singleton();

    }

    //通过枚举实现内部单例
    public enum SingletonEnum{
        INSTANCE;
        private Singleton singleton;
         SingletonEnum(){
//            singleton = new Singleton();
        }
//        public Singleton getSingleton() {
//            return singleton;
//        }

    }
}
