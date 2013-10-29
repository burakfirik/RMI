package com.pk.common;

import com.pk.bean.UserInfo;
import java.rmi.registry.Registry;

public class AppData {
    public static String host_address = "59.181.114.222";
    public static int host_port = 1090;

    public static Registry registry;
    public static UserInfo userInfo;
}
