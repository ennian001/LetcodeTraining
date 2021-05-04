package study.algorithm.juc2021.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ennian
 * 题目：实现一个自旋锁
 * 自旋锁好处：循环比较获取没有类似wait的阻塞。
 *
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，B随后进来后发现
 * 当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到。
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void mylock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t"+"come in");
        while (!atomicReference.compareAndSet(null,thread)) ;
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread() ;
        atomicReference.compareAndSet(thread,null) ;
        System.out.println(Thread.currentThread().getName()+"\t"+"myLock over");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo() ;
        new Thread(()->{
            spinLockDemo.mylock();
            try { TimeUnit.SECONDS.sleep( 5 ); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.myUnLock();
        },"A").start();
        //暂停一会儿线程，保证A线程先于B线程启动并完成
        try { TimeUnit.SECONDS.sleep( 1 ); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(()->{
            spinLockDemo.mylock();
            spinLockDemo.myUnLock();
        },"B").start();
    }
}
