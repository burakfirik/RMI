package com.pk.service;

public class ServiceManager {
    public static UserService getUserService(){
        return new UserService();
    }
    public static FileService getFileService(){
        return new FileService();
    }
}
