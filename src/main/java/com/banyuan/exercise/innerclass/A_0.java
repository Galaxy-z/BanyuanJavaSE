package com.banyuan.exercise.innerclass;

public class A_0 {
    static int access$0(A_0 a) {
        return a.i;
    }

    private int i;

    class A$B {
        final A_0 this$0;

        public A$B(A_0 a) {
            this$0 = a;
        }

        public void method() {
            A_0.access$0(this$0);
        }
    }
}
