package com.example.yuhanmarket.ChatPack;

import java.util.HashMap;
import java.util.Map;

public class Chat {
    public Map<String,Boolean> users = new HashMap<>(); //채팅방 유저들

    String UserId;
    String text;
    String date;


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
