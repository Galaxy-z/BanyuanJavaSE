package com.banyuan.exercise.oop;

public class StaticTest {
    static{
        System.out.println("静态初始化块");
    }
    public StaticTest(){
        System.out.println("构造器");
    }

    public static void main(String[] args) {

    }
}
