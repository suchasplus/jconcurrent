package com.suchasplus.jconcurrent.week11;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Powered by suchasplus@gmail.com on 2017/5/4.
 */
public class MemoryUtils {
    private static final int cacheLineBytes;
    static{
        final int defaultValue = 64;
        int value = defaultValue;
        try
        {
            value = Integer.parseInt(AccessController.doPrivileged(new PrivilegedAction<String>()
            {
                @Override
                public String run()
                {
                    //return System.getProperty("org.eclipse.jetty.util.cacheLineBytes", String.valueOf(defaultValue));
                    return "64";
                }
            }));
        }
        catch (Exception ignored)
        {
        }
        cacheLineBytes = value;
    }

    private MemoryUtils()
    {
    }

    public static int getCacheLineBytes()
    {
        return cacheLineBytes;
    }

    public static int getIntegersPerCacheLine()
    {
        return getCacheLineBytes() >> 2;
    }

    public static int getLongsPerCacheLine()
    {
        return getCacheLineBytes() >> 3;
    }
}
