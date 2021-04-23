package com.banyuan.project.chatroom;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    OK,
    REPEATED_USERNAME,
    NEW_USER_LIST
}
