package com.banyuan.homework.oop;

public class Liangmin {
    private String name;

    public String getName() {
        return name;
    }

    public Liangmin(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int money = 100;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Liangmin{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
