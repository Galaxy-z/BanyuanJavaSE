package com.banyuan.exercise.trans;

public class AliPay {

    private Account[] accounts = new Account[5];

    public AliPay() {
        accounts[0] = new Account("10001", "111", 5000);
        accounts[1] = new Account("10002", "111", 2000);
        accounts[2] = new Account("10003", "111", 3000);
        accounts[3] = new Account("10004", "111", 1000);
        accounts[4] = new Account("10005", "111", 6000);
    }

    public String serveForTransfer(String id, String password,
                                   int money, String toId) {
        //账号要对
        Account myAccount = null;
        for (Account account : accounts) {
            if (id.equals(account.getId())) {
                myAccount = account;
                break;
            }
        }

        if (myAccount ==null) {
            return "账号不存在";
        }
        //密码要对
        if (!password.equals(myAccount.getPassword())) {
            return "密码错误";
        }
        //不能自己转自己
        if (myAccount.getId().equals(toId)) {
            return "不能给自己转";
        }
        //对方账号要对
        Account targetAccount = null;
        for (Account account : accounts) {
            if (toId.equals(account.getId())) {
                targetAccount = account;
                break;
            }
        }
        if (targetAccount==null) {
            return "账号错误";
        }
        //余额要足
        if (money < myAccount.getMoney()) {
            return "余额不足";
        }
        //允许转
        Transfer(myAccount, targetAccount, money);
        return "转账成功";
    }

    private void Transfer(Account userAccount, Account targetAccount, int money) {
        userAccount.setMoney(userAccount.getMoney() - money);
        targetAccount.setMoney(targetAccount.getMoney() + money);
    }
}
