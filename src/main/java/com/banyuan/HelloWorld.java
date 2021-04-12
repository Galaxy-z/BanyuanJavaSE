package com.banyuan;

public class HelloWorld {
    public static void main(String[] args) {
        int a = 5, b = 2, c = 10;
//        int max;
//        if (a>b){
//            max = a;
//        }else{
//            max = b;
//        }
//        if (max<c){
//            max = c;
//        }
//        System.out.println(max);

        int temp;
        if (a>b){
            temp = a;
            a = b;
            b = temp;
        }
        if (b>c){
            temp = b;
            b = c;
            c = temp;
        }
        if (a>b){
            temp = a;
            a = b;
            b = temp;
        }
        System.out.println("a="+a+",b="+b+",c="+c);
        System.out.println(5|8);
    }
}
