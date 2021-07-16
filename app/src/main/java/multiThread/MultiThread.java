package multiThread;

public class MultiThread {

    private static int count = 0;

        public static void main(String[] args)throws Exception{
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<1000000000;i++){
                    count ++;
                }
                System.out.print(/*"myLooper is "+ Looper.myLooper().toString() +*/", the result is : "+count);

            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.start();
        t2.start();

        }
}
