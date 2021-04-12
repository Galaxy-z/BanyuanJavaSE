package com.banyuan.project.atm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * 用户交互界面
 * 控制台用于输入和输出
 * 每一个窗口对应一个方法
 */
public class ATMUI {

    private Scanner scanner = new Scanner(System.in);
    private BankServer bs = new BankServer();
    private int code;
    private int wrongCount = 0;
    private String myCardNumber;


    // ——————————————————窗口—————————————————————
    // 欢迎窗口
    public void welcomeWindow() {
        System.out.println("***********************************");
        System.out.println("*                                 *");
        System.out.println("*        欢迎使用半圆ATM系统         *");
        System.out.println("*                                 *");
        System.out.println("***********************************");

        // 跳转卡号输入窗口
        loginWindow();
    }

    // 卡号输入窗口
    public void loginWindow() {
        System.out.println("—————————————————登录——————————————————");
        System.out.print("请输入卡号:");
        String cardNumber = scanner.next();

        // 检查卡号位数
        if (cardNumber.length() != 6) {
            System.out.println("请输入6位卡号!");
            loginWindow();
        }

        // 验证卡号
        code = bs.checkCardNumber(cardNumber);

        // 状态码处理
        if (code == BankServer.OK) {
            myCardNumber = cardNumber;
            passwordWindow();
        } else {
            printError(code);
            loginWindow();
        }

    }

    //密码输入窗口
    public void passwordWindow() {
        System.out.println("—————————————————登录——————————————————");
        System.out.print("请输入密码:");
        String password = scanner.next();
        // 验证密码
        code = bs.checkPassword(password);
        if (code != BankServer.OK) {
            // 处理密码错误
            printError(code);
            // 错误次数增加
            wrongCount++;
            // 三次错误冻结账号
            if (wrongCount == 3) {
                bs.Block();
                System.out.println("3次密码错误，账号被冻结!");
                wrongCount = 0;
                // 跳转卡号输入界面
                loginWindow();
            }
            System.out.println("再输入" + (3 - wrongCount) + "次错误密码，账号将被冻结!");
            System.out.print("要继续尝试吗？(Y/N):");
            String choice = scanner.next();
            if (choice.equals("y")||choice.equals("Y")){
                // 跳转密码输入界面重新输入密码
                passwordWindow();
            }
            loginWindow();
        }
        // 验证成功
        System.out.println("———————————————————————————————————");
        System.out.println("登录成功!");
        // 跳转服务界面
        serviceWindow();
    }

    // 服务窗口
    public void serviceWindow() {
        System.out.println("\t+++++++++++++++++++++");
        System.out.println("\t+\t请选择服务项目:\t+");
        System.out.println("\t+++++++++++++++++++++");
        System.out.println("\t+\t  1.存款\t\t\t+");
        System.out.println("\t+\t  2.取款\t\t\t+");
        System.out.println("\t+\t  3.转账\t\t\t+");
        System.out.println("\t+\t  4.查询余额\t\t+");
        System.out.println("\t+\t  0.退出\t\t\t+");
        System.out.println("\t+++++++++++++++++++++");
        System.out.print("请输入相应的数字:");
        int choice = scanner.nextInt();
        // 选择项错误
        if (choice < 0 || choice > 4) {
            System.out.println("请选择正确的数字!");
            // 重新选择
            serviceWindow();
        }
        // 跳转到对应窗口
        switch (choice) {
            case 1:
                inMoneyWindow();
                break;
            case 2:
                outMoneyWindow();
                break;
            case 3:
                transferMoneyWindow();
                break;
            case 4:
                queryMoneyWindow();
                break;
            case 0:
                System.out.println("欢迎下次光临!");
                System.exit(0);
                break;

        }
    }

    // 存款窗口
    public void inMoneyWindow() {
        System.out.println("—————————————————存款——————————————————");
        int moneyNumber = getMoneyNumber();
        // 调用存钱方法
        bs.inMoney(moneyNumber);
        // 记录时间
        Date tradTime = new Date();
        System.out.println("成功存入" + moneyNumber + "元!");
        System.out.println("———————————————————————————————————");
        // 询问是否打印凭条
        askForPrintReceipt(2, moneyNumber, tradTime, myCardNumber);
        // 返回服务窗口
        serviceWindow();

    }

    // 取款窗口
    public void outMoneyWindow() {
        System.out.println("—————————————————取款——————————————————");
        int moneyNumber = getMoneyNumber();
        // 调用取钱方法
        code = bs.outMoney(moneyNumber);
        // 检查状态码是否OK
        if (code != BankServer.OK) {
            // 打印错误信息
            printError(code);
            // 返回取钱窗口
            outMoneyWindow();
        }
        System.out.println("成功取出" + moneyNumber + "元!");
        System.out.println("———————————————————————————————————");
        // 获取当前时间
        Date tradTime = new Date();
        // 询问是否打印凭条
        askForPrintReceipt(1, moneyNumber, tradTime, myCardNumber);
        // 返回服务窗口
        serviceWindow();

    }

    // 转账窗口
    public void transferMoneyWindow() {
        System.out.println("—————————————————转账——————————————————");
        System.out.println("请输入收款人账号,0退出");
        String toCardNumber = scanner.next();
        // 检测是否为0
        if (toCardNumber.equals("0")) {
            // 返回服务菜单
            serviceWindow();
        }
        if (toCardNumber.length() != 6) { // 检测卡号长度
            System.out.println("请输入6位卡号!");
            // 返回转账窗口重新输入
            transferMoneyWindow();
        }

        // 尝试转账0元
        code = bs.transferMoney(toCardNumber, 0);
        // 检测错误是否是找不到账户或给自己转账
        // OK or CANTFIND or TRANSTOSELF
        if (code != BankServer.OK) {
            // 打印错误码
            printError(code);
            // 返回转账窗口重新输入账号
            transferMoneyWindow();
        }
        // 获取转账金额
        int moneyNumber = getMoneyNumber();
        // 尝试转账
        code = bs.transferMoney(toCardNumber, moneyNumber);
        // OK or INSUFFICIENT
        while (code == BankServer.INSUFFICIENT) {
            // 打印错误信息
            printError(code);
            // 重新获取
            moneyNumber = getMoneyNumber();
            // 尝试转账
            code = bs.transferMoney(toCardNumber, moneyNumber);
        }
        System.out.println("成功向账户" + toCardNumber + "转账" + moneyNumber + "元!");
        System.out.println("———————————————————————————————————");
        // 获取时间
        Date tradTime = new Date();
        // 询问是否要打印凭条
        askForPrintReceipt(3, moneyNumber, tradTime, myCardNumber, toCardNumber);
        // 返回服务窗口
        serviceWindow();

    }

    // 查询余额窗口
    public void queryMoneyWindow() {
        System.out.println("———————————————————————————————————");
        System.out.println("您账户的余额为:" + bs.queryMoney() + "元");
        System.out.println("———————————————————————————————————");
        waitForContinue();
        serviceWindow();
    }

    // ————————————————工具方法和共用窗口—————————————————
    // 获取正确的金额
    public int getMoneyNumber() {
        int moneyNumber;
        do {
            System.out.print("请输入金额(大于0的100的整数,0退出):");
            moneyNumber = scanner.nextInt();
        }
        while (moneyNumber % 100 != 0 || moneyNumber < 0);
        if (moneyNumber == 0) {
            serviceWindow();
        }
        return moneyNumber;
    }

    // 询问是否打印凭条
    public void askForPrintReceipt(int which, int money, Date time, String... cardNumbers) {
        System.out.println("是否打印交易凭条？(Y/N):");
        String choice = scanner.next();
        if (choice.equals("Y") || choice.equals("y")) {
            printReceipt(which, money, time, cardNumbers);
        }

    }

    // 打印凭条窗口
    public void printReceipt(int which, int money, Date time, String... cardNumbers) {
        // 判断交易种类
        String type = null;
        switch (which) {
            case 1:
                type = "取款";
                break;
            case 2:
                type = "存款";
                break;
            case 3:
                type = "转账";
                break;
        }
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String tTime = sdf.format(time);
        // 打印
        System.out.println(" ———————————————————————");
        System.out.println("|\t\t--交易凭条--\t\t|");
        System.out.println(" ———————————————————————");

        System.out.println("|\t交易卡号:" + cardNumbers[0] + "\t\t|");
        System.out.println("|\t交易种类:" + type + "\t\t\t|");
        // 转账需要打印对方卡号
        if (which == 3) {
            System.out.println("|\t对方卡号:" + cardNumbers[1] + "\t\t|");
        }
        System.out.println("|\t交易金额:" + money + "元\t\t|");
        System.out.println("|\t交易时间:\t\t\t\t|\n|\t" + tTime + "\t|");
        System.out.println(" ——————————————————————— ");
        waitForContinue();

    }

    // 打印错误信息
    public void printError(int code) {
        switch (code) {
            case BankServer.BLOCKED:
                System.out.println("账号被冻结!请前往银行解除冻结!");
                break;
            case BankServer.CANTFIND:
                System.out.println("卡号无效!");
                break;
            case BankServer.INSUFFICIENT:
                System.out.println("金额不足!");
                break;
            case BankServer.WRONGPASSWORD:
                System.out.println("密码错误!");
                break;
            case BankServer.TRANSTOSELF:
                System.out.println("不能向自己转账!");
        }
    }

    // 等待继续
    public void waitForContinue() {
        System.out.print("输入任意字符继续:");
        scanner.next();
    }

    // 显示欢迎界面
    public void display() {
        welcomeWindow();
    }

    public static void main(String[] args) {
        ATMUI atmui = new ATMUI();
        atmui.display();

    }

}
