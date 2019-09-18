package com.qlckh.purifier.activity;

import com.lsh.packagelibrary.TempActivity;

/**
 * @author Andy
 * @date 2019/2/14 14:15
 * Desc:
 */
public class RunActivity extends TempActivity {



    @Override
    protected String getUrl2() {
        return "http://sz.llcheng888.com/switch/api2/main_view_config";
    }

    @Override
    protected String getRealPackageName() {
        return "com.qlckh.purifier";
    }

    @Override
    public Class<?> getTargetNativeClazz() {
        return SplashActivity.class;  //原生界面的入口activity
    }

    @Override
    public int getAppId() {
        return 903141655; //自定义的APPID
    }

    @Override
    public String getUrl() {
        return "http://sz2.html2api.com/switch/api2/main_view_config";
    }
}
