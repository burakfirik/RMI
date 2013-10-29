package com;

import com.pk.bean.UserInfo;
import com.pk.common.RMIProp;
import com.pk.common.RMIUtil;
import com.pk.rmi.AuthRemote;
import com.pk.rmi.FileRemote;
import com.pk.rmi.IAuthRemote;
import com.pk.rmi.IFileRemote;
import com.pk.rmi.socfac.XorClientSocketFactory;
import com.pk.rmi.socfac.XorServerSocketFactory;
import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class MainServer {
    private static Logger log = Logger.getLogger(MainServer.class);

    public static RMIProp prop;
    public static FileRemote obj;
    //keeps userId, UserInfo
    public static Map <String,UserInfo>sessionMap = new HashMap<String,UserInfo>();

    //used for sessionid generation
    public static SecureRandom random = new SecureRandom();

    public MainServer(){

    }
    public static void main(String[] args) {
        prop = new MainServer().loadProperties("app.properties");

        if(System.getSecurityManager()==null)
        {
            System.setSecurityManager(new SecurityManager());
        }
        try
        {
            IAuthRemote authRemote = new AuthRemote();

            String server=prop.getRmiServerHost();
            int port=prop.getRmiServerPort();

            Registry registry = LocateRegistry.getRegistry(server,port);
            try
            {
                registry.list();
                log.error("RMI registry found on //" + server + ":" + port);
            }
            catch(RemoteException remoteexception)
            {
                log.error("RMI registry not found on //" + server + ":" + port);
                //log.error(RMIUtil.exceptionToString(remoteexception));
                registry = null;
            }
            if(registry==null) {
                log.info("Creating RMI registry on //" + server + ":" + port + " ...");
                registry = LocateRegistry.createRegistry(port);

            }
            //binding authentication remote object
            registry.rebind("authRemote",authRemote);
            System.out.println("Remote object bind to registry : "+authRemote.getClass());

            //binding file remote object using socket
            byte pattern = (byte) 0xAC;
            RMIClientSocketFactory csf = new XorClientSocketFactory(pattern);
            RMIServerSocketFactory ssf = new XorServerSocketFactory(pattern);
            obj = new FileRemote();
            IFileRemote fileRemote = (IFileRemote) UnicastRemoteObject.exportObject(obj, 0, csf, ssf);
            registry.rebind("fileRemote",fileRemote);
            System.out.println("Remote object bind to registry : "+obj.getClass());

            System.out.println("Server ready...");
        }
        catch(Exception e)
        {
            log.error(RMIUtil.exceptionToString(e));
        }
    }

    public  RMIProp loadProperties(String fsPath){
        log.debug("Loading properties...");
        RMIProp app = new RMIProp();
        try{
            Properties properties = new Properties();
            properties.load(new FileInputStream(fsPath));

            app.setDriverDB(properties.getProperty("JDBCDRIVER"));
            app.setUrlDB(properties.getProperty("DBURL"));
            app.setUserDB(properties.getProperty("USERNAME"));
            app.setPassDB(properties.getProperty("USERPASSWORD"));
            app.setFileTable(properties.getProperty("FILE_TABLE_NAME"));

            app.setRmiServerHost(properties.getProperty("rmi_server_ip"));
            app.setRmiServerPort(Integer.parseInt(properties.getProperty("rmi_server_port")));
        }
        catch (Exception ex){
            log.error(RMIUtil.exceptionToString(ex));
            System.exit(0);
        }
        log.debug("Properties loaded");
        return app;
    }
}
