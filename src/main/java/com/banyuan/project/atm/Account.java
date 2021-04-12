package com.banyuan.project.atm;

import java.util.Date;

public class Account {
    //------------ATM属性--------------

    //卡号
    private String cardNumber;

    //密码
    private String password;

    //金额
    private int money;

    //状态 是否被冻结
    private boolean blocked;

    //-----------CRUD属性-----------
    //身份证号
    private String userID;

    //真实姓名
    private String realName;

    //开卡时间 系统自动生成
    private Date registerTime;

    //手机号码
    private String phone;

    //-----------constructor------------

    public Account(String cardNumber, String password, int money, String userID, String realName, Date registerTime, String phone, boolean blocked) {
        this.cardNumber = cardNumber;
        this.password = password;
        this.money = money;
        this.userID = userID;
        this.realName = realName;
        this.registerTime = registerTime;
        this.phone = phone;
        this.blocked = blocked;
    }

    //-----------getter & setter-------------

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
