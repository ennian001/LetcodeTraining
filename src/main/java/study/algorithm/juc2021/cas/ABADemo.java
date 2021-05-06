package study.algorithm.juc2021.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author ennian
 */
public class ABADemo {

    static AtomicInteger atomicInteger = new AtomicInteger(100) ;
    static AtomicStampedReference atomicStampedReference = new AtomicStampedReference(100,1) ;

    static AtomicMarkableReference<Integer> atomicMarkableReference  = new AtomicMarkableReference<>(100 , false)  ;

    public static void main(String[] args) {
        new Thread(()->{
            atomicInteger.compareAndSet(100,101) ;
            atomicInteger.compareAndSet(101,100) ;
        },"t1").start();
        new Thread(() -> {
            //暂停一会儿线程
            try { Thread.sleep( 500 ); } catch (InterruptedException e) { e.printStackTrace(); };
            System.out.println(atomicInteger.compareAndSet(100, 2019)+"\t"+atomicInteger.get());
        },"t2").start();
        //暂停一会儿线程,main彻底等待上面的ABA出现演示完成。
        try { Thread.sleep( 2000 ); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("============以下是ABA问题的解决=============================");

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t 首次版本号"+stamp);
            //暂停一会儿线程,
            try { Thread.sleep( 1000 ); } catch (InterruptedException e) { e.printStackTrace(); }
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1) ;
            System.out.println(Thread.currentThread().getName()+"\t 2次版本号:"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t 3次版本号:"+atomicStampedReference.getStamp());
        },"t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t 首次版本号:"+stamp);//1
            //暂停一会儿线程，获得初始值100和初始版本号1，故意暂停3秒钟让t3线程完成一次ABA操作产生问题
            try { Thread.sleep( 3000 ); } catch (InterruptedException e) { e.printStackTrace(); }
            boolean result = atomicStampedReference.compareAndSet(100,2019,stamp,stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t"+result+"\t"+atomicStampedReference.getReference());
        },"t4").start();

        System.out.println("=======================AtomicMarkableReference不关心修改过几次，只关心是否修改过==================");
        new Thread(
                ()->{
                    boolean marked =  atomicMarkableReference.isMarked();
                    System.out.println(Thread.currentThread().getName()+"\t"+"第一次版本号："+marked);
                    try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }

                    atomicMarkableReference.compareAndSet(100,101,marked,!marked) ;
                    System.out.println(Thread.currentThread().getName()+"\t"+"第二次版本号："+marked);
                    atomicMarkableReference.compareAndSet(101,100,marked,!marked) ;
                    System.out.println(Thread.currentThread().getName()+"\t"+"第三次版本号："+marked);
                }
        ,"t5").start();

        new Thread(()->{
             boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"\t"+"第一次修改版本号"+marked);
            try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            atomicMarkableReference.compareAndSet(100,2020,marked,!marked) ;
            System.out.println(Thread.currentThread().getName()+"\t"+atomicMarkableReference.getReference()+"\t"+atomicMarkableReference.isMarked());
        },"t6").start();

    }


}

