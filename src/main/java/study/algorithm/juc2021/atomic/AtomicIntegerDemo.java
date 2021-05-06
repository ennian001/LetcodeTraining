package study.algorithm.juc2021.atomic;

import lombok.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ennian
 */
public class AtomicIntegerDemo {

    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(100) ;
        for (int i = 1; i <= 100; i++) {
            new Thread(()->{
                try {
                    for (int j = 1; j <= 5000; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(myNumber.getAtomicInteger().get());
    }


}

class MyNumber{
    @Getter
    private AtomicInteger atomicInteger = new AtomicInteger() ;
    public  void addPlusPlus(){
        atomicInteger.incrementAndGet();
    }
}