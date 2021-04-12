package com.banyuan.homework.oop;

public class Story {
    public static void main(String[] args) {
        Tufei zhangsan = new Tufei("张三",100);
        Liangmin lisi = new Liangmin("李四", 100);
        System.out.println(zhangsan);
        System.out.println(lisi);
        zhangsan.rob(lisi, 10);
        System.out.println("-----------");
        System.out.println(zhangsan);
        System.out.println(lisi);


    }
}
