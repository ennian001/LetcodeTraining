package study.algorithm.juc2021.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author ennian
 * 以一种线程安全的方式操作非线程安全对象的某些字段。
 * 需求：
 * 1000个人同时向一个账号转账一元钱，那么累计应该增加1000元，
 * 除了synchronized和CAS,还可以使用AtomicIntegerFieldUpdater来实现。
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount() ;
        for (int i = 1; i <=1000; i++) {
            new Thread(
                    ()->{
                        bankAccount.transferMoney(bankAccount);
                    }
            ,String.valueOf(i)).start();
        }
        //暂停毫秒
        try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace();}
        System.out.println(bankAccount.money);
    }
}

class BankAccount{
    private String bankName = "CCB" ;
    public volatile int money = 0 ;
    AtomicIntegerFieldUpdater<BankAccount> accountAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money") ;
    // 不加锁性能高微创
    public void transferMoney(BankAccount bankAccount){
        accountAtomicIntegerFieldUpdater.incrementAndGet(bankAccount) ;
    }
}