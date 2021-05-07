package study.algorithm.juc2021.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 *  LongAdder只能用来计算加法 ， 且从零开始计算
 *  LongAccumulator提供了自定义的函数操作
 *  long类型聚合器 ， 需要传入long类型的二元操作 ， 可以用来计算各种聚合操作 ， 包括加乘等
 */
public class LongAccumulatorDemo {

        LongAdder longAdder = new LongAdder() ;
        public void  add_LongAdder(){
            longAdder.increment();;
        }

        // LongAccumulator longAccumulator = new LongAccumulator((x,y)->x+y,0);
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left - right;
            }
        },777) ;

        public void add_LongAccumulator(){
            longAccumulator.accumulate(1);
        }

    public static void main(String[] args) {
        LongAccumulatorDemo longAccumulatorDemo = new LongAccumulatorDemo() ;

        longAccumulatorDemo.add_LongAdder();
        longAccumulatorDemo.add_LongAccumulator();
        longAccumulatorDemo.add_LongAccumulator();
        System.out.println(longAccumulatorDemo.longAccumulator.longValue());
        System.out.println(longAccumulatorDemo.longAdder.longValue());
    }
}
