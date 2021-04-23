package com.banyuan.exercise.innerclass;

public class Outer {
    private int i = 100;

    public void outerMethod() {
        System.out.println("外部类的方法");
    }

    public void outerMethod2() {
        Inner inner = new Inner();
        inner.innerMethod();
    }

    class Inner {
        private int i = 300;
        private int j = 200;

        public void innerMethod() {
            System.out.println(Outer.this.i);
            outerMethod();
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.outerMethod2();
    }
}
