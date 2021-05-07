package study.algorithm.juc2021.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author ennian
 */
public class LongAdderDemo {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder() ;
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        System.out.println(longAdder.longValue());

        LongAccumulator longAccumulator  =new LongAccumulator((x,y)->x*y,2) ;
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(2);
        longAccumulator.accumulate(3);

        System.out.println(longAccumulator.longValue());
    }

}
