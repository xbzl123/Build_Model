package Factory;

import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.FileReader;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.schedulers.Schedulers;

public class Test {
//    public static void main(String[] args){
//        Factory factory = new CarFactory();
//        Car car1 = factory.createCar("BWM");
//        car1.run();
//        Car car2 = factory.createCar("Benz");
//        car2.run();
//    }
    public static void main(String[] args) {
        practice1();
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static Subscription mSubscription;
    public static void practice1() {

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                try {
                    FileReader reader = new FileReader("chase.txt");
                    BufferedReader br = new BufferedReader(reader);
                    String str;
                    while ((str = br.readLine()) != null && !emitter.isCancelled()) {
                        while (emitter.requested() == 0) {
                            if (emitter.isCancelled()) {
                                break;
                            }
                        }
                        emitter.onNext(str);
                    }
                    br.close();
                    reader.close();
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.ERROR).
                subscribeOn(Schedulers.io()).
                observeOn(Schedulers.newThread()).
                subscribe(new FlowableSubscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                        try {
                            Thread.sleep(s.length()*100);
                            mSubscription.request(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
