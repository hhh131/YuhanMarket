package com.example.yuhanmarket;

public class UserVO {

    String UserId;
    String pwd;
    String nick;
    boolean MailAuthStatus;
    String AuthString;

    public UserVO(String UserId, String pwd, String nick, boolean mailAuthStatus, String authString) {
        this.UserId = UserId;
        this.pwd = pwd;
        this.nick = nick;
        MailAuthStatus = mailAuthStatus;
        AuthString = authString;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean isMailAuthStatus() {
        return MailAuthStatus;
    }

    public void setMaiAuthStatus(boolean mailAuthStatus) {
        MailAuthStatus = mailAuthStatus;
    }

    public String getAuthString() {
        return AuthString;
    }

    public void setAuthString(String authString) {
        AuthString = authString;
    }
}
