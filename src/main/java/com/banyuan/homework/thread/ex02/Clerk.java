package com.banyuan.homework.thread.ex02;

public class Clerk {
    private String name;

    public Clerk(String name) {
        this.name = name;
    }

    public void putHamburger(Oven oven){
        oven.addHamburger(new Hamburger());
        System.out.println("【店员】"+name+"向烤箱放入一个生🍔");
    }

    @Override
    public String toString() {
        return "Clerk{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
