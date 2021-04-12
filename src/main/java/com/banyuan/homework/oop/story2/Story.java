package com.banyuan.homework.oop.story2;

public class Story {
    public static void main(String[] args) {
        Student zs = new Student("张三", 100);
        Student ls = new Student("李四", 100);
        System.out.println(zs);
        System.out.println(ls);
        zs.borrow(ls,10);
        System.out.println(zs);
        System.out.println(ls);
    }
}
