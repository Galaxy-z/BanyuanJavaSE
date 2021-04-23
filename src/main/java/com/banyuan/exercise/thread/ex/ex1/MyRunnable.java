package com.banyuan.exercise.thread.ex.ex1;

public class MyRunnable implements Runnable {
    private int sum = 0;

    @Override
    public void run() {
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (int) (Math.random() * 100 + 1);
        }
        System.out.println(sum);
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
