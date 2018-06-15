package com.qlckh.purifier.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * @author Andy
 * @date   2018/6/6 15:28
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    ResourceUtils.java
 */
public class ResourceUtils {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Context configWrap(Context context,float textsize){
        Resources resources = context.getResources();// 获得res资源对象
        Configuration config = resources
                .getConfiguration();// 获得设置对象
        config.fontScale=textsize;
        resources.updateConfiguration(config,resources.getDisplayMetrics());
        return context.createConfigurationContext(config);
    }
}
