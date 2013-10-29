package com.pk.service;

import com.pk.bean.UserInfo;
import com.pk.common.AppData;
import com.pk.rmi.IAuthRemote;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class UserService {
    private IAuthRemote getAuthRemote(){
        try{
            AppData.registry = LocateRegistry.getRegistry(AppData.host_address, AppData.host_port);
            return  (IAuthRemote) AppData.registry.lookup("authRemote");

            //below tested ok
            //String addServerURL = "rmi://" + AppData.host_address + ":"+AppData.host_port+"/authRemote";
            //System.out.println("Connecting....: "+addServerURL);
            //IAuthRemote addServerIntf =  (IAuthRemote) Naming.lookup(addServerURL);
            //return addServerIntf;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public UserInfo login(String userName, String password){
        UserInfo userInfo = null;
        try{

            IAuthRemote acc =  getAuthRemote();
            userInfo = acc.login(userName, password);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return userInfo;
    }

    public boolean logout(){
        try{
            IAuthRemote acc =  getAuthRemote();
            return acc.logout(AppData.userInfo.getUserName(), AppData.userInfo.getSessionId());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public UserInfo register(String userName,String password, String name){
        try{
            IAuthRemote acc =  getAuthRemote();
            return acc.register(userName,password,name);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
