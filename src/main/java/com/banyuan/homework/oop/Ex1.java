package com.banyuan.homework.oop;

public class Ex1 {
    public static void main(String[] args) {
        int guess = 10;
        A a = new A();
        if(guess<a.v){
            System.out.println("小了");
        }else{
            System.out.println("大了");
        }


    }
}

class A {
    public int v = 100;
}
