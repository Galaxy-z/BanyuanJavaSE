package com.banyuan.exercise.thread;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            for (int i = 10; i > 0; i--) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.next());

    }


}
