package com.pk.rmi;

import com.pk.bean.UserInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthRemote extends Remote {
    public UserInfo login(String userName, String password) throws RemoteException;
    public boolean logout(String userName, String sessionId) throws RemoteException;
    public UserInfo register(String userName,String password, String name) throws RemoteException;
}
