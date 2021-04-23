package com.banyuan.exercise.thread.ex.ex4;

public class Guest {
    private String name;



    public Guest(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void guess(Host host) {
        int guess = (int) (Math.random() * 100 + 1);
        System.out.println(this.name+"猜数字为:"+guess);
        host.checkNumber(this, guess);
    }
}
