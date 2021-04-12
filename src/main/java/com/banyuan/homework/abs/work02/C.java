package com.banyuan.homework.abs.work02;

class A implements B
{
    public int methodB(int i)
    {
        return i += i * i;
    }
}

interface B
{
    int methodB(int i);
}

class MainClass
{
    public static void main(String[] args)
    {
        B b = new A();

        System.out.println(b.methodB(2));
    }
}
