package com.banyuan.homework.thread.ex02;

public class Hamburger {
    private boolean ripe = false;

    public Hamburger() {
    }

    public Hamburger(boolean ripe) {
        this.ripe = ripe;
    }

    public boolean isRipe() {
        return ripe;
    }


    public void setRipe(boolean ripe) {
        this.ripe = ripe;
    }

    @Override
    public String toString() {
        return "Hamburger{" +
                "ripe=" + ripe +
                '}';
    }
}
