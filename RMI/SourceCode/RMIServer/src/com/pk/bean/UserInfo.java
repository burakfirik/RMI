package com.pk.bean;

import java.io.Serializable;
import java.rmi.Remote;

public class UserInfo implements Serializable {
    private String userName;
    private String password;
    private String name;
    private String sessionId;
    private String errorMessage;

    public UserInfo(){

    }
    public UserInfo(String userName, String password, String name, String sessionId) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
