package study.algorithm.juc2021.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {

    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]) ;
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        int tmpInt = 0 ;
        atomicIntegerArray.getAndSet(0,1122) ;
        System.out.println(tmpInt+"\t"+atomicIntegerArray.get(0));
        atomicIntegerArray.getAndIncrement(1) ;
        atomicIntegerArray.getAndIncrement(1) ;
        tmpInt = atomicIntegerArray.getAndIncrement(1);

        System.out.println(tmpInt+"\t"+atomicIntegerArray.get(1));
    }


}
