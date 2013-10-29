package com.pk.db;

import com.MainServer;
import com.pk.common.RMIProp;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBManager {
    public static Connection getDBConnection(){
        try{
            RMIProp app = MainServer.prop;

            Class.forName(app.getDriverDB());
            Connection con = DriverManager.getConnection(app.getUrlDB(), app.getUserDB(), app.getPassDB());
            return con;
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.printf("Done");
    }
}
