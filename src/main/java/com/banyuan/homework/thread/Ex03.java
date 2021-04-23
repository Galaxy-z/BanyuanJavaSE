package com.banyuan.homework.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Ex03 {

    public static void main(String[] args) {
        int n = 5;

        FactorialThread t1 = new FactorialThread();
        t1.setN(n);


        FactorialRunnable fr = new FactorialRunnable(n);
        Thread t2 = new Thread(fr);


        FactorialCallable fc = new FactorialCallable(n);
        FutureTask<Integer> futureTask = new FutureTask<>(fc);
        Thread t3 = new Thread(futureTask);

        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int result1 = t1.getResult();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int result2 = fr.getResult();
        int result3 = 0;
        try {
            result3 = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);



    }
}

class FactorialThread extends Thread{
    private int result = 1;
    private int n;


    @Override
    public void run() {
        for (int i = 1; i <= n ; i++) {
            result *= i;
        }
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getResult() {
        return result;
    }
}

class FactorialRunnable implements Runnable{
    private int result = 1;
    private int n;


    @Override
    public void run() {
        for (int i = 1; i <= n ; i++) {
            result *= i;
        }
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getResult() {
        return result;
    }

    public FactorialRunnable(int n) {
        this.n = n;
    }

}

class FactorialCallable implements Callable<Integer>{
    private int n;

    public FactorialCallable(int n) {
        this.n = n;
    }

    @Override
    public Integer call() throws Exception {
        int result = 1;
        for (int i = 1; i <= n ; i++) {
            result *= i;
        }
        return result;
    }
}


