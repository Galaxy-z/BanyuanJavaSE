package com.banyuan.homework.thread.ex02;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Story {
    public static void main(String[] args) {
        Clerk clerk = new Clerk("秦始皇");
        Oven oven = new Oven();
        Customer customer1 = new Customer("老子");
        Customer customer2 = new Customer("孙子");

//        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                    clerk.putHamburger(oven);

            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                    oven.cookHamburger();

            }
        });

        Thread t3 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                    customer1.takeHamburger(oven);

            }
        });

        Thread t4 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                    customer2.takeHamburger(oven);

            }
        });

        Thread t5 = new Thread(() -> {
            for (int i = 20; i > 0; i--) {
                System.out.println(i + "s-->");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(oven);

            System.exit(0);

        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }
}
