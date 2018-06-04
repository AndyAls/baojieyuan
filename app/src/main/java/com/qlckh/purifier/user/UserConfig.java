package com.qlckh.purifier.user;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Andy
 * @date 2018/5/15 14:52
 * Desc:
 */
public class UserConfig {

    private static final String USER_SP_NAME = "USER_SP_NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PWD = "USER_PWD";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String USER_ID = "USERUSER_ID";
    public static final String IS_LOGIN = "IS_LOGIN";
    private static SharedPreferences sp;

    public static UserInfo userInfo;

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        UserConfig.userInfo = userInfo;
    }

    public static void init(Context context) {

        if (sp == null) {
            sp = context.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE);
        }

    }

    public static void savaUserName(String userName) {
        sp.edit().putString(USER_NAME, userName).apply();
    }

    public static void savaPwd(String pwd) {
        sp.edit().putString(USER_PWD, pwd).apply();
    }

    public static void savaType(int type) {
        sp.edit().putInt(USER_TYPE, type).apply();
    }

    public static void savaUserid(int id) {
        sp.edit().putInt(USER_ID, id).apply();
    }


    public static String getUserName() {
        return sp.getString(USER_NAME, "");
    }

    public static String getPwd() {
        return sp.getString(USER_PWD, "");
    }

    public static int getType() {
        return sp.getInt(USER_TYPE, -1);
    }

    public static int getUserid() {
        return sp.getInt(USER_ID, -1);
    }

    public static void savaLogin(boolean isLogin) {
        sp.edit().putBoolean(IS_LOGIN, isLogin).apply();
    }

    public static boolean isLogin() {
        return sp.getBoolean(IS_LOGIN, false);
    }

    public static void reset() {
        savaLogin(false);
        savaPwd("");
        savaType(-1);
        savaUserName("");
        savaUserid(-1);
    }

}
