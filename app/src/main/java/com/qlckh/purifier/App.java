package com.qlckh.purifier;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.lsh.packagelibrary.CasePackageApp;
import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.common.GlideApp;
import com.qlckh.purifier.common.LocationService;
import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.preview.GlideLoader;
import com.qlckh.purifier.preview.ZoomMediaLoader;
import com.qlckh.purifier.user.UserConfig;
import com.tencent.bugly.Bugly;

import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/5/14 11:28
 * Desc:
 */
public class App extends CasePackageApp {

    private static final String APPKEY = "0420889e73";
    private static App app;
    public LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(BuildConfig.DEBUG);
        XLog.e("--", "onCreate");
        Bugly.init(getApplicationContext(), APPKEY, true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        app = this;
        XLog.e("--", "attachBaseContext");
        init(base);
        initHttp();
    }

    private void initHttp() {
        /**
         * 全局请求的统一配置
         */
        RxHttpUtils.init(this);

        RxHttpUtils
                .getInstance()
                //开启全局配置
                .config()
                //全局的BaseUrl
                .setBaseUrl(ApiService.BASE_URL)
                //开启缓存策略
                .setCache()
                //全局的请求头信息
                //.setHeaders(headerMaps)
                //全局持久话cookie,保存本地每次都会携带在header中
                .setCookie(false)
                //全局ssl证书认证
                //信任所有证书,不安全有风险
                .setSslSocketFactory()
                //使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.cer"))
                //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.bks"), "123456", getAssets().open("your.cer"))
                //全局超时配置
                .setReadTimeout(ApiService.DEFAULT_TIME)
                //全局超时配置
                .setWriteTimeout(10)
                //全局超时配置
                .setConnectTimeout(10)
                //全局是否打开请求log日志
                .setLog(true);
    }

    private void init(Context context) {
        MultiDex.install(context);
        UserConfig.init(context);
        locationService = new LocationService(context);
        ZoomMediaLoader.getInstance().init(new GlideLoader());
    }

    public synchronized static Context getAppContext() {
        return app.getApplicationContext();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GlideApp.get(this).onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        GlideApp.get(this).onTrimMemory(level);
    }
}
