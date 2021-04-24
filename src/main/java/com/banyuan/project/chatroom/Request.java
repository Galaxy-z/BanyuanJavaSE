package com.banyuan.project.chatroom;

import java.io.Serializable;

public class Request implements Serializable {
    private String from;

    private String to;

    private RequestType type;

    private String expression;

    private boolean isWhisper;

    private String text;

    private byte[] content;

    public Request(String from, String to, RequestType type, String text, byte[] content) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.text = text;
        this.content = content;
    }

    public Request(String from, String to, RequestType type, String text) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.text = text;
    }

    public Request(String from, String to, RequestType type, String expression, boolean isWhisper, String text) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.expression = expression;
        this.isWhisper = isWhisper;
        this.text = text;
    }

    public Request(String from, String to, RequestType type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public RequestType getType() {
        return type;
    }

    public String getExpression() {
        return expression;
    }

    public boolean isWhisper() {
        return isWhisper;
    }

    public String getText() {
        return text;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Request{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type=" + type +
                '}';
    }
}
