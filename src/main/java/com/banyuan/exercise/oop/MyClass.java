package com.banyuan.exercise.oop;

public class MyClass {
    private int a;
    private int b;
    int[] arr;

    public MyClass() {
    }

    public MyClass(int a, int b, int[] arr) {
        this.a = a;
        this.b = b;
        this.arr = arr;
    }


    public int method(){
        return a * a + b * b;
    }

    public int method2(int i){
        return arr[i];
    }

    public static void main(String[] args) {
        MyClass mc = new MyClass(2, 3, new int[]{1,3,5,7,9});
        System.out.println(mc.method());
        System.out.println(mc.method2(2));
    }
}
