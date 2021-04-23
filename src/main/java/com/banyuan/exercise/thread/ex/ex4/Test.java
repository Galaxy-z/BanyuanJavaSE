package com.banyuan.exercise.thread.ex.ex4;

public class Test {

    public static void main(String[] args) {
        WinSingal win = new WinSingal();
        Host host = new Host(win);
        Guest guest1 = new Guest("顾客A");
        Guest guest2 = new Guest("顾客B");

        Thread t1 = new Thread(()->{
            while (!win.isWin()){
                guest1.guess(host);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(()->{
            while (!win.isWin()){
                guest2.guess(host);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(()->{
            int time = 0;
            while (!win.isWin()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time++;

            }

            System.out.println("游戏结束，用时"+time+"秒");
        });

        t1.start();
        t2.start();
        t3.start();

    }
}
