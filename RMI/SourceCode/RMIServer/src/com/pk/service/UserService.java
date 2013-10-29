package com.pk.service;

import com.MainServer;
import com.pk.bean.UserInfo;
import com.pk.common.RMIUtil;
import com.pk.db.DBManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserService {
    private static Logger log = Logger.getLogger(UserService.class);

    public UserInfo login(UserInfo userInfo) {
        log.debug("Login requested by: "+userInfo.getUserName());
        try {
            PreparedStatement st = DBManager.getDBConnection().prepareStatement("select * from users where username=? and password=?");
            st.setString(1, userInfo.getUserName());
            st.setString(2, userInfo.getPassword());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                String name = rs.getString("name");
                UserInfo userInfoOld = MainServer.sessionMap.get(userInfo.getUserName());
                if (userInfoOld == null) {
                    userInfo.setName(name);
                    userInfo.setSessionId(RMIUtil.getNewSessionId());
                    MainServer.sessionMap.put(userInfo.getUserName(), userInfo);
                }
                else{
                    userInfo = userInfoOld;
                }
                log.debug(userInfo.getUserName()+" is authenticated with sessionId="+userInfo.getSessionId());
            }
            else{
                log.debug(userInfo.getUserName()+" is not authenticated");
            }
            rs.close();
            st.close();
        } catch (Exception ex) {
            log.error(RMIUtil.exceptionToString(ex));
        }

        return userInfo;
    }

    public UserInfo register(String userName,String password, String name){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setPassword(password);
        userInfo.setName(name);

        try{
            Statement st = DBManager.getDBConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from users where username='"+userName+"'");
            if(rs.next()){
                userInfo.setErrorMessage(userName+", is already registered, please use another username");
                rs.close();
                st.close();

                log.debug(userName+", not registered as it is already taken by some other user");
            }
            else{
                //register
                PreparedStatement pst = DBManager.getDBConnection().prepareStatement("insert into users values(?,?,?)");
                pst.setString(1,userName);
                pst.setString(2,password);
                pst.setString(3,name);

                pst.executeUpdate();

                userInfo.setSessionId(RMIUtil.getNewSessionId());
                MainServer.sessionMap.put(userName,userInfo);
                pst.close();

                log.debug(userName+", is newly registered with sessionId="+userInfo.getSessionId());
            }
        }
        catch (Exception ex){
            log.error(RMIUtil.exceptionToString(ex));
        }
        return userInfo;
    }

}
