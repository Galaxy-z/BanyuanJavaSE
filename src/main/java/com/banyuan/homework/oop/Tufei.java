package com.banyuan.homework.oop;

public class Tufei {
    private String name;
    private int money = 100;

    public Tufei(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void rob(Liangmin lm, int num) {
        lm.setMoney(lm.getMoney() - num);
        this.setMoney(this.getMoney() + num);
    }

    @Override
    public String toString() {
        return "Tufei{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
