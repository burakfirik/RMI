package com.pk.common;

public class RMIProp {
    private String driverDB;
    private String urlDB;
    private String userDB;
    private String passDB;

    private String rmiServerHost;
    private int rmiServerPort;

    private String fileTable;

    public RMIProp(){

    }

    public RMIProp(String driverDB, String urlDB, String userDB, String passDB, String rmiServerHost, int rmiServerPort, String fileTable) {
        this.driverDB = driverDB;
        this.urlDB = urlDB;
        this.userDB = userDB;
        this.passDB = passDB;
        this.rmiServerHost = rmiServerHost;
        this.rmiServerPort = rmiServerPort;
        this.fileTable = fileTable;
    }

    public String getDriverDB() {
        return driverDB;
    }

    public void setDriverDB(String driverDB) {
        this.driverDB = driverDB;
    }

    public String getUrlDB() {
        return urlDB;
    }

    public void setUrlDB(String urlDB) {
        this.urlDB = urlDB;
    }

    public String getUserDB() {
        return userDB;
    }

    public void setUserDB(String userDB) {
        this.userDB = userDB;
    }

    public String getPassDB() {
        return passDB;
    }

    public void setPassDB(String passDB) {
        this.passDB = passDB;
    }

    public String getRmiServerHost() {
        return rmiServerHost;
    }

    public void setRmiServerHost(String rmiServerHost) {
        this.rmiServerHost = rmiServerHost;
    }

    public int getRmiServerPort() {
        return rmiServerPort;
    }

    public void setRmiServerPort(int rmiServerPort) {
        this.rmiServerPort = rmiServerPort;
    }

    public String getFileTable() {
        return fileTable;
    }

    public void setFileTable(String fileTable) {
        this.fileTable = fileTable;
    }
}
