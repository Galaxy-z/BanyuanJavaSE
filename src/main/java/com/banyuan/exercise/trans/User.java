package com.banyuan.exercise.trans;

public class User {

    private String name;

    public void askForTransfer(String id,String password,
                               int money,String toId,AliPay aliPay){
           String result =  aliPay.serveForTransfer(id,password,money,toId);
    }
}
