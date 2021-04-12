package com.banyuan.project.atm;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class CRUDUI {
    Scanner scanner = new Scanner(System.in);
    BankServer bs = new BankServer();
    private int code;


    // ————————————————————窗口———————————————————————
    // 欢迎窗口
    public void welcomeWindow() {
        System.out.println(" ***********************************");
        System.out.println(" *                                 *");
        System.out.println(" *     欢迎使用半圆银行账户管理系统      *");
        System.out.println(" *                                 *");
        System.out.println(" ***********************************");
        functionWindow();
    }

    // 功能窗口
    public void functionWindow() {
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println("+\t\t\t请选择账户操作:\t\t\t+");
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println("+\t\t  1.开户\t\t\t\t\t\t+");
        System.out.println("+\t\t  2.销户\t\t\t\t\t\t+");
        System.out.println("+\t\t  3.修改手机号\t\t\t\t+");
        System.out.println("+\t\t  4.查询指定账户信息\t\t\t+");
        System.out.println("+\t\t  5.账户列表(卡号排序)\t\t\t+");
        System.out.println("+\t\t  6.账户列表(开户时间排序)\t\t+");
        System.out.println("+\t\t  7.账号解冻\t\t\t\t\t+");
        System.out.println("+\t\t  0.退出\t\t\t\t\t\t+");
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.print("请输入相应的数字:");
        int choice = scanner.nextInt();
        // 选择项错误
        if (choice < 0 || choice > 7) {
            System.out.println("请选择正确的数字!");
            // 重新选择
            functionWindow();
        }

        // 跳转相应窗口
        switch (choice) {
            case 1:
                addAccountWindow();
                break;
            case 2:
                deleteAccountWindow();
                break;
            case 3:
                changePhoneNumberWindow();
                break;
            case 4:
                queryAccountWindow();
                break;
            case 5:
                listAccountByCardNumber();
                break;
            case 6:
                listAccountByRegisterTime();
                break;
            case 7:
                unblockAccountWindow();
                break;
            case 0:
                System.exit(0);
                break;
        }
    }

    // 增加用户窗口
    public void addAccountWindow() {
        // 获得注册信息
        System.out.print("请输入姓名:");
        String name = scanner.next();
        String id;
        do {
            System.out.print("请输入18位身份证号:");
            id = scanner.next();
        } while (id.length() != 18);

        String phone;
        do {
            System.out.print("请输入11位手机号:");
            phone = scanner.next();
        } while (phone.length() != 11);
        String password1;
        String password2;
        // 检查两次密码是否相同
        boolean flag = true;
        do {
            if (!flag) System.out.println("两次输入密码不相同!");
            System.out.print("请输入账户密码:");
            password1 = scanner.next();
            System.out.print("请确认账户密码:");
            password2 = scanner.next();
            flag = false;
        } while (!password1.equals(password2));
        // 调用增加用户的方法
        bs.addOne(name, id, phone, password1);
        System.out.println("成功添加账户!");
        System.out.println("—————————————————————————————————————");
        // 返回功能界面
        waitForContinue();
        functionWindow();
    }

    // 删除用户窗口
    public void deleteAccountWindow() {
        // 获取卡号
        System.out.println("———————————————————删除用户——————————————————");
        String cardNumber = getCardNumber();
        // 尝试删除
        code = bs.deleteOne(cardNumber);
        // 卡号不存在
        if (code == BankServer.CANTFIND) {
            System.out.println("卡号不存在");
            System.out.println("—————————————————————————————————————");
            deleteAccountWindow();
        }
        System.out.println("删除成功!");
        System.out.println("—————————————————————————————————————");
        // 返回功能列表
        waitForContinue();
        functionWindow();
    }

    // 更改手机号窗口
    public void changePhoneNumberWindow() {
        System.out.println("———————————————————更改手机号——————————————————");
        // 获取卡号
        String cardNumber = getCardNumber();
        // 获取手机号
        String phone;
        do {
            System.out.print("请输入11位手机号:");
            phone = scanner.next();
        } while (phone.length() != 11);
        // 尝试更改手机号
        code = bs.changePhone(cardNumber, phone);
        // 找不到
        if (code == BankServer.CANTFIND) {
            System.out.println("卡号无效");
            changePhoneNumberWindow();
        }
        System.out.println("修改成功!");
        System.out.println("———————————————————————————————————");
        // 返回功能列表
        waitForContinue();
        functionWindow();

    }

    // 查询用户窗口
    public void queryAccountWindow() {
        System.out.println("———————————————————查询用户——————————————————");
        // 获取卡号
        String cardNumber = getCardNumber();
        // 获取查到的账户
        Account account = bs.queryOne(cardNumber);
        // null -> 没找到
        if (account == null) {
            System.out.println("账户不存在");
            queryAccountWindow();
        }
        // 显示账户信息
        listAccount(new Account[]{account});
        // 返回功能窗口
        waitForContinue();
        functionWindow();

    }

    // 展示账户列表(卡号排序)
    public void listAccountByCardNumber() {
        // 获取当前用户数组
        Account[] accounts = bs.accountList();
        // 按卡号排序
        Arrays.sort(accounts, Comparator.comparing(Account::getCardNumber));
        // 打印
        listAccount(accounts);
        waitForContinue();
        functionWindow();
    }

    // 展示账户列表(时间排序)
    public void listAccountByRegisterTime() {
        // 获取当前用户数组
        Account[] accounts = bs.accountList();
        // 按时间排序
        Arrays.sort(accounts, (e1, e2) -> e1.getRegisterTime().compareTo(e2.getRegisterTime()));
        // 打印
        listAccount(accounts);
        waitForContinue();
        functionWindow();
    }

    // 账号解冻窗口
    public void unblockAccountWindow() {
        System.out.println("———————————————————账号解冻——————————————————");
        String cardNumber = getCardNumber();
        code = bs.unblockAccount(cardNumber);
        // 找不到
        if (code == BankServer.CANTFIND) {
            System.out.println("卡号无效");
            System.out.println("———————————————————————————————————");
            unblockAccountWindow();
        }
        // 没有被冻结
        if (code == BankServer.ALREADYUNBLOCKED) {
            System.out.println("该账号没有被冻结!");
            unblockAccountWindow();
        }

        System.out.println("解冻成功!");
        System.out.println("———————————————————————————————————");
        // 返回功能窗口
        waitForContinue();
        functionWindow();

    }

    // ————————————————————工具方法和共用窗口———————————————————————
    // 打印给定的账户数组
    public void listAccount(Account[] accounts) {
        System.out.println("———————————————————————————————————————————————————————————————————————————");
        System.out.println("序号\t卡号\t\t状态\t开户时间\t\t姓名\t\t身份证号\t\t\t\t电话\t\t\t余额");
        System.out.println("———————————————————————————————————————————————————————————————————————————");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < accounts.length; i++) {
            Account item = accounts[i];
            System.out.println(
                    " " + (i + 1) + "\t" +
                            item.getCardNumber() + "\t" +
                            (item.isBlocked() ? "冻结" : "正常") + "\t" +
                            // 格式化Date
                            sdf.format(item.getRegisterTime()) + "\t" +
                            item.getRealName() + "\t\t" +
                            item.getUserID() + "\t" +
                            item.getPhone() + "\t" +
                            item.getMoney()

            );
        }
        System.out.println("———————————————————————————————————————————————————————————————————————————");
    }

    // 等待继续
    public void waitForContinue() {
        System.out.print("输入任意字符继续:");
        scanner.next();
    }

    // 获取卡号
    public String getCardNumber() {
        String cardNumber;
        // 判断位数
        boolean flag = false;
        do {
            if (flag) {
                System.out.println("请输入6位卡号!");
            }
            System.out.print("请输入卡号(0退出):");
            cardNumber = scanner.next();
            // 0 -> 返回功能窗口
            if (cardNumber.equals("0")) {
                functionWindow();
            }
            flag = true;
        } while (cardNumber.length() != 6);
        // 返回卡号
        return cardNumber;
    }

    public static void main(String[] args) {
        CRUDUI crudui = new CRUDUI();
        crudui.welcomeWindow();
    }

}
