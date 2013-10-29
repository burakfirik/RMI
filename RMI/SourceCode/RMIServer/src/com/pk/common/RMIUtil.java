package com.pk.common;

import com.MainServer;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RMIUtil {
    public static String exceptionToString(Throwable feException)
    {
        Throwable loRootCause = getRootCause(feException);
        StackTraceElement[] laTrace = loRootCause.getStackTrace();
        String lsData = loRootCause.toString() + "\r\n";
        String lsMessage = feException.getMessage();

        if (lsMessage != null)
        {
            lsData += lsMessage + "\r\n\r\n";
        }

        for (StackTraceElement aLaTrace : laTrace)
        {
            lsData += "\tat " + aLaTrace + "\r\n";
        }
        return lsData;
    }

    /**
     * returns root cause object of the exception
     *
     * @param feException exception
     * @return Throwable
     */
    public static Throwable getRootCause(Throwable feException)
    {
        Throwable loThrowable = feException;
        while (loThrowable instanceof Error && loThrowable.getCause() != null)
        {
            loThrowable = loThrowable.getCause();
        }
        return loThrowable;
    }

    public static String getNewSessionId(){
        return new BigInteger(130, MainServer.random).toString(32);
    }
}
