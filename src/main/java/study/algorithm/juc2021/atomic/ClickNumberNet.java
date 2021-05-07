package study.algorithm.juc2021.atomic;

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

        public static void main(String[] args) {
            ClickNumberNet clickNumberNet = new ClickNumberNet();

            long startTime;
            long endTime;

        }


    }
}
