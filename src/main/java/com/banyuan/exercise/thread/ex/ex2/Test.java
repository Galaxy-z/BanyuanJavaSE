package com.banyuan.exercise.thread.ex.ex2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int sum = 0;
        int num = 1;
        FutureTask<Integer>[] futures = new FutureTask[5];
        for (int i = 0; i < 5; i++) {
            FutureTask<Integer> ft = new FutureTask<>(new myCallable(num));
            num += 20;
            futures[i] = ft;
            Thread t = new Thread(ft);
            t.start();
        }

        for (FutureTask<Integer> future : futures) {
            sum += future.get();
        }

        System.out.println(sum);

    }
}

class myCallable implements Callable<Integer> {
    private int num;

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 20; i++) {
            sum += (num + i);
        }
        return sum;
    }

    public myCallable(int num) {
        this.num = num;
    }
}
