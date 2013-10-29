package com.pk.service;

import com.MainServer;
import com.pk.bean.FileResponse;
import com.pk.bean.UserInfo;
import com.pk.common.RMIUtil;
import com.pk.db.DBManager;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static Logger log = Logger.getLogger(FileService.class);

    private boolean isValidUser(String userName, String sessionId) {
        UserInfo userInfo = MainServer.sessionMap.get(userName);
        if (userInfo == null) {
            return false;
        } else if (userInfo.getSessionId() == null) {
            return false;
        } else if (userInfo.getSessionId().equals(sessionId)) {
            return true;
        }
        return false;
    }

    public List getRows(String userName, String sessionId) {
        boolean tableCreated = true;
        List rows = new ArrayList();
        String tableName = MainServer.prop.getFileTable();

        //create table if does not exists
        if (!isTableExists(tableName)) {
            log.debug("Table: " + tableName + " does not exists");
            tableCreated = createFileTable();
            return rows;
        }

        if (tableCreated) {
            try {
                if (isValidUser(userName, sessionId)) {
                    Statement st = DBManager.getDBConnection().createStatement();
                    ResultSet rs = st.executeQuery("select FILE_NAME,dbms_lob.getlength(CONTENT) from " + tableName);
                    while (rs.next()) {
                        String fileName = rs.getString(1);
                        String fileSize = rs.getBigDecimal(2) + "";
                        fileSize = fileSize == null ? "0" : fileSize; //convert null to 0

                        String row[] = {fileName, fileSize};
                        rows.add(row);
                    }
                }
            } catch (Exception ex) {
                log.error(RMIUtil.exceptionToString(ex));
            }
        }
        return rows;
    }

    public byte[] download(String userName, String sessionId, String fileName) {
        try {
            if (isValidUser(userName, sessionId)) {
                Statement st = DBManager.getDBConnection().createStatement();
                ResultSet rs = st.executeQuery("select CONTENT from " + MainServer.prop.getFileTable() + " where FILE_NAME='" + fileName + "'");
                if (rs.next()) {
                    Blob blob = rs.getBlob(1);
                    rs.close();
                    st.close();
                    return blob.getBytes(1, (int) blob.length());
                }
            }
        } catch (Exception ex) {
            log.error(RMIUtil.exceptionToString(ex));
        }
        return null;
    }

    //check if table exists;
    private boolean isTableExists(String tableName) {
        try {
            Statement st = DBManager.getDBConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from " + tableName);
            rs.close();
            st.close();

            return true;
        } catch (Exception ex) {
            //eat it
        }
        return false;
    }

    public boolean createFileTable() {
        String tableName = MainServer.prop.getFileTable();
        try {
            Statement st = DBManager.getDBConnection().createStatement();
            st.executeUpdate("CREATE TABLE " + tableName + " (FILE_NAME VARCHAR(100) PRIMARY KEY,CONTENT BLOB)");
            st.close();
            log.debug("Table: " + tableName + " created");
            return true;
        } catch (Exception ex) {
            log.debug(RMIUtil.exceptionToString(ex));
        }
        return false;
    }


    public FileResponse upload(String userName, String sessionId, String fileName, byte[] data) {
        log.debug(userName + ", requested to upload file : " + fileName);
        FileResponse response = new FileResponse();

        if (isValidUser(userName, sessionId)) {
            try {
                boolean tableCreated = true;
                String tableName = MainServer.prop.getFileTable();

                //create table if does not exists
                if (!isTableExists(tableName)) {
                    log.debug("Table: " + tableName + " does not exists");
                    tableCreated = createFileTable();
                }

                if (tableCreated) {
                    Connection con = DBManager.getDBConnection();
                    PreparedStatement st = con.prepareStatement("insert into " + tableName + " values(?,?)");
                    //int ID = getMaxId(tableName)+1;
                    Blob blob = con.createBlob();
                    blob.setBytes(1, data);

                    st.setString(1, fileName);
                    st.setBlob(2, blob);

                    st.executeUpdate();
                    st.close();
                    blob.free();

                    log.debug("File uploaded : " + fileName);
                    response.setStatus(true);
                }
            } catch (SQLIntegrityConstraintViolationException ex) {
                response.setStatus(false);
                response.setMessage("Cannot upload a duplicate file: " + fileName);
                log.error(RMIUtil.exceptionToString(ex));
            } catch (Throwable ex) {
                response.setStatus(false);
                response.setMessage(ex.toString());
                log.error(RMIUtil.exceptionToString(ex));
            }
        }
        return response;
    }

    public FileResponse delete(String userName,String sessionId,String fileName){
        if (isValidUser(userName, sessionId)) {
            FileResponse response = new FileResponse();
            try{
                Connection connection = DBManager.getDBConnection();
                PreparedStatement pst = connection.prepareStatement("delete from "+MainServer.prop.getFileTable()+ " where FILE_NAME=?");
                pst.setString(1,fileName);
                pst.executeUpdate();
                response.setStatus(true);
            }
            catch (Throwable ex){
                response.setStatus(false);
                response.setMessage(ex.toString());
                log.error(RMIUtil.exceptionToString(ex));
            }
            return response;
        }
        return null;
    }
}
