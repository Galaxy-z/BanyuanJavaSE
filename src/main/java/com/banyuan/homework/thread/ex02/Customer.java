package com.banyuan.homework.thread.ex02;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;

    private List<Hamburger> pocket = new ArrayList<>();


    public void takeHamburger(Oven oven){
        pocket.add(oven.giveHamburger());
        System.out.println("【顾客】"+name+"取得一个熟🍔");
    }

    public Customer(String name) {
        this.name = name;
    }
}
