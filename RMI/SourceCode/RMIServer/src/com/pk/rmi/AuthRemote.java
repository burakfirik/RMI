package com.pk.rmi;

import com.MainServer;
import com.pk.bean.UserInfo;
import com.pk.common.RMIUtil;
import com.pk.db.DBManager;
import com.pk.service.ServiceManager;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthRemote extends UnicastRemoteObject implements IAuthRemote {
    private static Logger log = Logger.getLogger(AuthRemote.class);

    public AuthRemote() throws RemoteException{

    }
    public UserInfo login(String userName, String password)throws RemoteException{
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setPassword(password);
        return ServiceManager.getUserService().login(userInfo);
    }
    public boolean logout(String userName, String sessionId) throws RemoteException{
        UserInfo userOnServer = MainServer.sessionMap.get(userName);
        if(userOnServer==null){
            return false;
        }
        else if(userOnServer.getSessionId()==null){
            return false;
        }
        else if(!userOnServer.getSessionId().equals(sessionId)){
            return false;
        }
        else if(userOnServer.getSessionId().equals(sessionId)){
            MainServer.sessionMap.remove(userOnServer);
            log.debug("Logout done for: "+userName);
            return true;
        }
        return false;
    }

    public UserInfo register(String userName,String password, String name) throws RemoteException{
        return ServiceManager.getUserService().register(userName,password,name);
    }
}
