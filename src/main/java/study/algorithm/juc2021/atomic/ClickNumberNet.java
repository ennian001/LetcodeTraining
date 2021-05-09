package study.algorithm.juc2021.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author ennian
 */
public class ClickNumberNet {

    int number = 0 ;
    public synchronized void clickBySync(){
        number++ ;
    }
    AtomicLong atomicLong  = new AtomicLong(0) ;

    public void clickByAtomicLong(){
        atomicLong.incrementAndGet() ;
    }

    LongAdder longAdder = new LongAdder() ;
    public void clickByLongAdder(){
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x,y)->x+y,0) ;

    public void clickByLongAccumulator(){
        longAccumulator.accumulate(1);
    }

    static class LongAdderDemo2{

        public static void main(String[] args) throws InterruptedException {
            ClickNumberNet clickNumberNet = new ClickNumberNet();

            long startTime;
            long endTime;

            CountDownLatch countDownLatch = new CountDownLatch(50) ;
            CountDownLatch countDownLatch2 = new CountDownLatch(50) ;
            CountDownLatch countDownLatch3 = new CountDownLatch(50) ;
            CountDownLatch countDownLatch4 = new CountDownLatch(50) ;

            startTime = System.currentTimeMillis() ;

            for (int i = 1; i <= 50; i++) {
                new Thread(()->{
                    try {
                        for (int j = 0; j <100*10000 ; j++) {
                            clickNumberNet.clickBySync();
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                },String.valueOf(1)).start();
            }
            countDownLatch.await();
            endTime = System.currentTimeMillis() ;
            System.out.println("----costTime: "+(endTime - startTime) +" 毫秒"+"\t clickBySync result: "+clickNumberNet.number);
            for (int i = 1; i <=50; i++) {
                new Thread(() -> {
                    try
                    {
                        for (int j = 1; j <=100 * 10000; j++) {
                            clickNumberNet.clickByAtomicLong();
                        }
                    }finally {
                        countDownLatch2.countDown();
                    }
                },String.valueOf(i)).start();
            }
            countDownLatch2.await();
            endTime = System.currentTimeMillis();
            System.out.println("----costTime: "+(endTime - startTime) +" 毫秒"+"\t clickByAtomicLong result: "+clickNumberNet.atomicLong);

            startTime = System.currentTimeMillis();
            for (int i = 1; i <=50; i++) {
                new Thread(() -> {
                    try
                    {
                        for (int j = 1; j <=100 * 10000; j++) {
                            clickNumberNet.clickByLongAdder();
                        }
                    }finally {
                        countDownLatch3.countDown();
                    }
                },String.valueOf(i)).start();
            }
            countDownLatch3.await();
            endTime = System.currentTimeMillis();
            System.out.println("----costTime: "+(endTime - startTime) +" 毫秒"+"\t clickByLongAdder result: "+clickNumberNet.longAdder.sum());

            startTime = System.currentTimeMillis();
            for (int i = 1; i <=50; i++) {
                new Thread(() -> {
                    try
                    {
                        for (int j = 1; j <=100 * 10000; j++) {
                            clickNumberNet.clickByLongAccumulator();
                        }
                    }finally {
                        countDownLatch4.countDown();
                    }
                },String.valueOf(i)).start();
            }
            countDownLatch4.await();
            endTime = System.currentTimeMillis();
            System.out.println("----costTime: "+(endTime - startTime) +" 毫秒"+"\t clickByLongAccumulator result: "+clickNumberNet.longAccumulator.longValue());
        }


    }
}
