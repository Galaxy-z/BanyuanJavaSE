package com.banyuan.project.chatroom;

import java.io.Serializable;

public enum RequestType implements Serializable {
    // 登录
    LOGIN,
    // 刷新用户列表
    REFRESH_USER_LIST,
    // 发送信息
    SEND_MSG,
    // 服务器发送信息
    SEND_SERVER_MSG,
    // 转发文件接受询问
    SEND_ASK_FILE_ACCEPT,
    // 接受文件
    ACCEPT_FILE,
    // 拒绝文件
    REFUSE_FILE,
    // 转发验证失败信息
    SEND_VERIFICATION_FAILED,
    // 转发文件数据包
    SEND_FILE_PACKAGE,
}
