package com.banyuan.project.chatroom;

import java.io.Serializable;

public class Response implements Serializable {
    private String from;

    private String to;

    private ResponseType type;

    private String expression;

    private boolean isWhisper;

    private String text;

    private byte[] content;

    public Response(String from, String to, ResponseType type, String text) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.text = text;
    }

    public Response(String from, String to, ResponseType type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public Response(String from, String to, ResponseType type, String expression, boolean isWhisper, String text, byte[] content) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.expression = expression;
        this.isWhisper = isWhisper;
        this.text = text;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public ResponseType getType() {
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
        return "Response{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
