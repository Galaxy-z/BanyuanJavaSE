package com.banyuan.exercise.innerclass.test;

public class A {

    static int access$0(A a){
        return a.i;
    }
    private int  i = 10;
}

class A$B{
    final A this$0;

    public A$B(A a){
        this$0 = a;
    }

    public void method(){
        int j = A.access$0(this$0);
    }
}
