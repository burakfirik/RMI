package com.pk.service;

import com.pk.bean.FileResponse;
import com.pk.common.AppData;
import com.pk.rmi.IFileRemote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;

public class FileService {
    private IFileRemote getFileRemote() {
        try {
            if (AppData.registry == null) {
                AppData.registry = LocateRegistry.getRegistry(AppData.host_address, AppData.host_port);
            }
            return (IFileRemote) AppData.registry.lookup("fileRemote");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List openTable(){
        try{
            return getFileRemote().getRows(AppData.userInfo.getUserName(),AppData.userInfo.getSessionId());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public byte[] download(String fileName){
        try{
            return getFileRemote().download(AppData.userInfo.getUserName(),AppData.userInfo.getSessionId(),fileName);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public FileResponse upload(String fileName,byte[]data){
        try{
            return getFileRemote().upload(AppData.userInfo.getUserName(),AppData.userInfo.getSessionId(),fileName,data);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public FileResponse delete(String fileName){
        try{
            return getFileRemote().delete(AppData.userInfo.getUserName(),AppData.userInfo.getSessionId(),fileName);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
