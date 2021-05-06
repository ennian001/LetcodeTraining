package study.algorithm.juc2021.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，要求只能初始化一次
 */
public class AtomicReferenceFieldUpdaterDemo {

    public static void main(String[] args) {
        MyVar myVar = new MyVar() ;
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                myVar.init(myVar);
            },String.valueOf(i)).start();
        }
    }


}

class MyVar{
    public volatile Boolean isInit = Boolean.FALSE ;
    AtomicReferenceFieldUpdater<MyVar,Boolean> atomicReferenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class , Boolean.class , "isInit") ;
    public void init(MyVar var){
        if (atomicReferenceFieldUpdater.compareAndSet(var,Boolean.FALSE,Boolean.TRUE)){
            System.out.println(Thread.currentThread().getName()+"\t"+"---init.....");
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"---init.....over");
        }else {
            System.out.println(Thread.currentThread().getName()+"\t"+"其它线程正在初始化");
        }
    }
}