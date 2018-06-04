package com.qlckh.purifier.http.http;

import okhttp3.OkHttpClient;

/**
 * @author Andy
 * @date   2018/5/15 18:43
 * Desc:    HttpClient.java
 */

public class HttpClient {

    private static HttpClient instance;

    private OkHttpClient.Builder builder;

    public HttpClient() {
        builder = new OkHttpClient.Builder();
    }

    public static HttpClient getInstance() {

        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }

        }
        return instance;
    }


    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

}
