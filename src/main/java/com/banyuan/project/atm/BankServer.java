package com.banyuan.project.atm;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class BankServer {

    // 当前使用者的账户索引
    private int myIndex = -1;
    // 下一个新账号的卡号
    private int cardNumber = 100006;
    // 账户数组
    private Account[] accounts = new Account[5];
    // 获取用户数量
    private int count  = accounts.length;
    // 状态码
    // 操作成功
    public static final int OK = 1;
    // 被冻结
    public static final int BLOCKED = 0;
    // 找不到
    public static final int CANTFIND = -1;
    // 余额不足
    public static final int INSUFFICIENT = -2;
    // 密码错误
    public static final int WRONGPASSWORD = -3;
    // 给自己转账
    public static final int TRANSTOSELF = -4;
    // 没有冻结
    public static final int ALREADYUNBLOCKED = -5;

    //------初始化账户数组------
    public BankServer() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, Calendar.APRIL, 16);
        Date date1 = calendar.getTime();
        calendar.set(1999, Calendar.JULY, 2);
        Date date2 = calendar.getTime();
        calendar.set(2016, Calendar.DECEMBER, 20);
        Date date3 = calendar.getTime();
        calendar.set(2004, Calendar.SEPTEMBER, 26);
        Date date4 = calendar.getTime();
        calendar.set(2010, Calendar.MAY, 5);
        Date date5 = calendar.getTime();

        accounts[0] = new Account(
                "100001",
                "123456",
                5000,
                "320482123411119345",
                "张三",
                date1,
                "18947594749",
                false
        );
        accounts[1] = new Account(
                "100002",
                "123456",
                100,
                "320482123411119345",
                "李一",
                date2,
                "15802750282",
                false
        );
        accounts[2] = new Account(
                "100003",
                "123456",
                100000,
                "320482123411119345",
                "王二",
                date3,
                "18804638505",
                false
        );
        accounts[3] = new Account(
                "100004",
                "123456",
                0,
                "320482123411119345",
                "陈五",
                date4,
                "13926922648",
                false
        );
        accounts[4] = new Account(
                "100005",
                "123456",
                800000,
                "320482123411119345",
                "徐六",
                date5,
                "18994649373",
                true
        );
    }

    // ——————————————ATM————————————————
    // ——————————————功能————————————————
    // 检查卡号
    public int checkCardNumber(String cardNumber) {
        // 更改使用者的账户索引值
        myIndex = findAccount(cardNumber);
        // 索引值-1 -> 不存在
        if (myIndex == -1) {
            return CANTFIND;
        }
        // 被冻结
        if (accounts[myIndex].isBlocked()) {
            return BLOCKED;
        }
        // 成功
        return OK;
    }

    // 检验密码
    public int checkPassword(String password) {
        boolean result = accounts[myIndex].getPassword().equals(password);
        if (result) {
            // 成功
            return OK;
        }
        // 密码错误
        return WRONGPASSWORD;

    }

    // 取钱
    public int outMoney(int moneyNumber) {
        int money = queryMoney();
        // 判断余额是否足够
        if (money >= moneyNumber) {
            // 扣钱
            accounts[myIndex].setMoney(money - moneyNumber);
            return OK;
        }
        // 金额不足
        return INSUFFICIENT;
    }

    // 存钱
    public void inMoney(int moneyNumber) {
        // 加钱
        accounts[myIndex].setMoney(accounts[myIndex].getMoney() + moneyNumber);

    }

    // 转账
    public int transferMoney(String to, int moneyNumber) {
        // 寻找对方账号索引值
        int toIndex = findAccount(to);
        // 没找到
        if (toIndex == -1) {
            return CANTFIND;
        }
        // 给自己转
        if (toIndex == myIndex) {
            return TRANSTOSELF;
        }
        // 判断是否余额足够
        if (queryMoney() >= moneyNumber) {
            accounts[myIndex].setMoney(accounts[myIndex].getMoney() - moneyNumber);
            accounts[toIndex].setMoney(accounts[toIndex].getMoney() + moneyNumber);
            return OK;
        }
        // 余额不足
        return INSUFFICIENT;
    }

    // 查询余额
    public int queryMoney() {
        return accounts[myIndex].getMoney();
    }

    // 冻结账号
    public void Block() {
        accounts[myIndex].setBlocked(true);
    }

    // ——————————————工具方法————————————
    // 找到需要的卡号对应的索引
    private int findAccount(String cardNumber) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getCardNumber().equals(cardNumber)) {
                return i;
            }
        }
        return -1;
    }

    // ———————————————————CRUD————————————————————
    // ——————————————功能———————————————
    // 增加用户
    public void addOne(String name, String id, String phone, String password) {
        // 新建Account类
        Account account = new Account(
                (cardNumber++) + "",
                password,
                0,
                id,
                name,
                new Date(),
                phone,
                false
        );
        // 扩容 容量翻倍
        if (count==accounts.length){
            accounts = Arrays.copyOf(accounts, accounts.length * 2);
        }
        // 新用户放到新数组最后
        accounts[count] = account;
        // 用户计数+1
        count++;
    }

    // 更改电话号码
    public int changePhone(String cardNumber, String newPhone) {
        // 找到需要修改的账户索引
        int index = findAccount(cardNumber);
        if (index == -1) {
            return CANTFIND;
        }
        System.out.println(index);
        // 设置新号码
        accounts[index].setPhone(newPhone);
        return OK;
    }

    // 删除指定账户
    public int deleteOne(String cardNumber) {
        // 找到需要删除的账户索引
        int index = findAccount(cardNumber);
        if (index == -1) {
            return CANTFIND;
        }
        // 被删除的账户被后面的账户覆盖
        for (int i = index; i < count-1; i++) {
            accounts[i] = accounts[i + 1];
        }
        // 舍弃尾部
        accounts[count-1] = null;
        // 用户计数减一
        count--;
        return OK;
    }

    // 查询指定用户
    public Account queryOne(String cardNumber) {
        // 获取卡号对应账户索引
        int index = findAccount(cardNumber);
        // 找不到返回null
        if (index == -1) {
            return null;
        }
        // 取得对应账户对象
        Account item = accounts[index];
        // 返回该对象副本
        return copyAccount(item);
    }

    // 获取账户列表副本
    public Account[] accountList() {
        Account[] newAccounts = new Account[count];
        for (int i = 0; i < count; i++) {
            newAccounts[i] = copyAccount(accounts[i]);
        }
        return newAccounts;
    }

    // 账户解冻
    public int unblockAccount(String cardNumber) {
        // 找到需要解冻的账户索引
        int index = findAccount(cardNumber);
        if (index == -1) {
            return CANTFIND;
        }
        // 获取账户
        Account item = accounts[index];
        // 没有被冻结
        if (!item.isBlocked()) {
            return ALREADYUNBLOCKED;
        }
        // 解冻
        item.setBlocked(false);
        return OK;
    }

    // —————————————工具方法—————————————
    // 获取账户副本
    private Account copyAccount(Account item) {
        return new Account(
                item.getCardNumber(),
                item.getPassword(),
                item.getMoney(),
                item.getUserID(),
                item.getRealName(),
                new Date(item.getRegisterTime().getTime()),
                item.getPhone(),
                item.isBlocked()
        );
    }
}
