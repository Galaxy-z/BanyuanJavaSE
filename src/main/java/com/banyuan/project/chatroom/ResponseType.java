package com.banyuan.project.chatroom;

import java.io.Serializable;

public enum ResponseType implements Serializable {

    OK,
    // 用户名重复
    REPEATED_USERNAME,
    // 新用户列表
    NEW_USER_LIST,
    // 即将收到的信息
    INCOMING_MSG,
    // 即将发送的信息
    OUTGOING_MSG,
    // 客户端发送信息
    SEND_CLIENT_MSG,
    // 客户端发送文件传输请求
    SEND_FILE_ACCEPT_REQUEST,
    // 询问文件接收
    ASK_FILE_ACCEPT,
    // 目标接受请求
    TARGET_ACCEPT_FILE,
    // 目标拒绝请求
    TARGET_REFUSE_FILE,
    // 文件接收请求验证失败
    VERIFICATION_FAILED,
    // 文件数据包
    FILE_PACKAGE,
    // 创建文件数据包
    CREATE_FILE_PACKAGE,
    // 合并文件
    COMBINE_FILES,
    // 发送注销请求
    SEND_LOGOUT,
    // 服务器已关闭
    SERVICE_SHUTDOWN



}
