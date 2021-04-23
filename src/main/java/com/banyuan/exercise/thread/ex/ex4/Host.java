package com.banyuan.exercise.thread.ex.ex4;


public class Host {
    private final int number;

    private WinSingal win;

    public Host(WinSingal win) {
        this.number = (int)(Math.random() * 100 + 1);
        this.win = win;
    }

    public void checkNumber(Guest guest, int guess){

            if (guess == number) {
                System.out.println(guest.getName() + "猜对了，答案是:" + number);
                win.setWin(true);
            }

    }
}
