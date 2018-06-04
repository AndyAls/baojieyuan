package com.qlckh.purifier.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseActivity;
import com.qlckh.purifier.view.MyWebView;

/**
 * @author Andy
 * @date   2018/5/17 16:04
 * Desc:    WebViewActivity.java
 */
public class WebViewActivity extends BaseActivity{
    private Context context;
    private MyWebView webView;
    private String url;
    private String title;

    @Override
    protected int getContentView() {
        return R.layout.activity_web_view;
    }


    @Override
    public void initView() {
        context = this;
        url = getIntent().getStringExtra("url");
        setTitle("新闻");
        goBack();

    }
    @SuppressLint("NewApi")
    @Override
    public void initDate() {
        webView = (MyWebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);//开启DOM缓存，关闭的话H5自身的一些操作是无效的
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setBlockNetworkImage(false);
        CookieSyncManager.createInstance(this);
        final CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
//        cookieManager.setCookie(url, "ticket=" + UserConfig.getTicket());
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();

        } else {
            CookieManager.getInstance().flush();
        }
        if (url != null) {
            webView.loadUrl(url);
        } else {
            showShort("数据出错");
        }
        Log.e("---", "url"+url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.contains("http://backtoapp/?BackToApp")) {
                    finish();
                } else {
                    view.loadUrl(url);
                    Log.e("---", "url--"+url);
                }
                return true;
            }

        });
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);//加载空地址
            webView.clearCache(true);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            if (System.currentTimeMillis() - exitTime > 2500) {
                showShort("再按一次退出网页");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

    }

}
