package com.pk.rmi;

import com.pk.bean.FileResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IFileRemote extends Remote {
    public List getRows(String userName, String sessionId) throws RemoteException;
    public byte[] download(String userName,String sessionId,String fileName) throws RemoteException;
    public FileResponse upload(String userName,String sessionId,String fileName,byte[]data) throws RemoteException;
    public FileResponse delete(String userName,String sessionId,String fileName) throws RemoteException;
}
