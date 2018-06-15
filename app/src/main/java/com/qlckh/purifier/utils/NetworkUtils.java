package com.qlckh.purifier.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author Andy
 * @date   2018/4/28 11:14
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    网络管理工具类
 */
public final class NetworkUtils {

    private NetworkUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     *
     * {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isNetWorkAvailable(){
        // 阿里巴巴公共ip
        String ip = "223.5.5.5";
        ShellUtils.CommandResult result = ShellUtils.execCmd(String.format("ping -c 1 %s", ip), false);
        return result.result == 0;
    }

}
