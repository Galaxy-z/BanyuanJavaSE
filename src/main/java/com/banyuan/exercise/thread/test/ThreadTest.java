package com.banyuan.exercise.thread.test;

public class ThreadTest {
    public static void main(String[] args) {
        Runnable r = ()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName()+"->"+i);
            }
        };
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
    }
}
