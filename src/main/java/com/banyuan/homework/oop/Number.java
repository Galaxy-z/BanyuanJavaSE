package com.banyuan.homework.oop;

public class Number {
    private int n1;
    private int n2;

    public Number(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public int addition(){
        return n1 + n2;
    }
    public int subtraction(){
        return n1 - n2;
    }
    public int multiplication(){
        return n1 * n2;
    }
    public int division(){
        return n1 / n2;
    }

    public static void main(String[] args) {
        Number number = new Number(10, 4);
        System.out.println(number.addition());
        System.out.println(number.subtraction());
        System.out.println(number.multiplication());
        System.out.println(number.division());
    }
}
