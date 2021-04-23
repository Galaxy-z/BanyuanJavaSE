package com.banyuan.exercise.thread.ex.ex1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (int) (Math.random() * 100 + 1);
        }
        System.out.println(sum);
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask1 = new FutureTask<>(myCallable);
        FutureTask<Integer> futureTask2 = new FutureTask<>(myCallable);
        Thread t1 = new Thread(futureTask1);
        Thread t2 = new Thread(futureTask2);
        t1.start();
        t2.start();
        System.out.println(futureTask1.get() + futureTask2.get());
    }
}
