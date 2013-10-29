package com.pk.service;

public class ServiceFactory {
    public static UserService getUserService(){
        return new UserService();
    }
    public static FileService getFileService(){
        return new FileService();
    }
}
