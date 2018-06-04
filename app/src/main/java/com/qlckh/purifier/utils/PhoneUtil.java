package com.qlckh.purifier.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * @author Andy
 * @date 2018/5/15 11:40
 * Desc: 判断是手机还是其他
 */
public class PhoneUtil {

    public static boolean isPhone(Activity activity){
        TelephonyManager telephony = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        return telephony != null && telephony.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;

    }

    public static boolean isN5s(){

        return "N5S".equals(Build.MODEL);
    }
}
