package com.banyuan.exercise.thread.test;

public class Exercise extends Thread implements Runnable{
    @Override
    public void run() {
        System.out.println(1);
    }

    public static void main(String[] args) {
        Thread t = new Thread(new Exercise());
        t.start();
    }
}
