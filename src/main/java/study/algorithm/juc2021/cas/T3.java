package study.algorithm.juc2021.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程环境下使用原子类保证线程安全
 */
public class T3 {


    AtomicInteger atomicInteger = new AtomicInteger() ;

    public int getAtomicInteger() {
        return atomicInteger.get();
    }

    public void setAtomicInteger(AtomicInteger atomicInteger) {
        atomicInteger.getAndIncrement();
    }
}
