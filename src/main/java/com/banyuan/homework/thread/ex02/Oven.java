package com.banyuan.homework.thread.ex02;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Oven {
    private List<Hamburger> hamburgers = new ArrayList<>();

    {
        hamburgers.add(new Hamburger(true));
        hamburgers.add(new Hamburger(true));
        hamburgers.add(new Hamburger(true));
        hamburgers.add(new Hamburger(false));
        hamburgers.add(new Hamburger(false));
    }

    public synchronized void addHamburger(Hamburger h) {
        hamburgers.add(h);
    }

    public synchronized void cookHamburger() {
        for (Hamburger h : hamburgers) {
            if (!h.isRipe()) {
                h.setRipe(true);
                System.out.println("【烤箱】烤熟了一个🍔");
            }
        }
    }

    public synchronized Hamburger giveHamburger() {
        Iterator it = hamburgers.iterator();
        while (it.hasNext()) {
            Hamburger h = (Hamburger) it.next();
            if (h != null && h.isRipe()) {
                it.remove();
                System.out.println("【烤箱】一个熟🍔被取出");
                return h;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        int ripedcount = 0;
        int unripedcount = 0;
        for (Hamburger hamburger : hamburgers) {
            if (hamburger.isRipe()) {
                ripedcount++;
            } else {
                unripedcount++;
            }
        }
        return "Oven{" + ripedcount + "个熟汉堡，" + unripedcount + "个生汉堡" + '}';
    }
}
