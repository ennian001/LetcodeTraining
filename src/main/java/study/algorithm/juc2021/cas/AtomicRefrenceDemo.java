package study.algorithm.juc2021.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

@Data
@ToString
@AllArgsConstructor
class User{

    String userName ;
    int age ;

}
public class AtomicRefrenceDemo {

    public static void main(String[] args) {
        User z3 = new User("z3",24);
        User li4 = new User("li4",26);
        AtomicReference<User> atomicRefrenceDemo = new AtomicReference();
        atomicRefrenceDemo.set(z3);
        System.out.println(atomicRefrenceDemo.compareAndSet(z3,li4)+"\t"+atomicRefrenceDemo.get().toString());
        System.out.println(atomicRefrenceDemo.compareAndSet(z3,li4)+"\t"+atomicRefrenceDemo.get().toString());
    }

}
