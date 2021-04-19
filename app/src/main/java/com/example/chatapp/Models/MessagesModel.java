package com.example.chatapp.Models;

public class MessagesModel {

    String uId, msg;
    Long timestamp;

    public MessagesModel(String uId, String msg, Long timestamp) {
        this.uId = uId;
        this.msg = msg;
        this.timestamp = timestamp;
    }

    public MessagesModel(String uId, String msg) {
        this.uId = uId;
        this.msg = msg;
    }

    public MessagesModel(){}

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
