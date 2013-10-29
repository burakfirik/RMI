package com.pk.rmi;

import com.pk.bean.FileResponse;
import com.pk.service.ServiceManager;
import org.apache.log4j.Logger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class FileRemote implements IFileRemote {
    private static Logger log = Logger.getLogger(FileRemote.class);

    public FileRemote() throws RemoteException {

    }

    public List getRows(String userName, String sessionId) throws RemoteException {
        log.debug("Table row is requested by: " + userName);
        return ServiceManager.getFileService().getRows(userName, sessionId);
    }

    public byte[] download(String userName,String sessionId,String fileName) throws RemoteException{
        log.debug("Download requested by: " + userName);
        return ServiceManager.getFileService().download(userName,sessionId,fileName);
    }
    public FileResponse upload(String userName,String sessionId,String fileName,byte[]data) throws RemoteException{
        return ServiceManager.getFileService().upload(userName,sessionId,fileName,data);
    }
    public FileResponse delete(String userName,String sessionId,String fileName) throws RemoteException{
        log.debug("Delete requested by: " + userName+" for file: "+fileName);
        return ServiceManager.getFileService().delete(userName,sessionId,fileName);
    }
}
