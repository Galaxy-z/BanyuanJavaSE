package com.banyuan.exercise.thread;

import java.util.Scanner;

public class MyThread extends Thread{
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(s);
    }
}
