package com.banyuan.exercise.thread.ex.ex1;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable r1 = new MyRunnable();
        MyRunnable r2 = new MyRunnable();

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(r1.getSum()+ r2.getSum());

    }
}
