package com.qlckh.purifier.http.http;

import com.qlckh.purifier.http.gson.GsonAdapter;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Andy
 * @date   2018/5/15 18:43
 * Desc:    RetrofitClient.java
 */

public class RetrofitClient {

    private static RetrofitClient instance;

    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpBuilder;

    private RetrofitClient() {

        mOkHttpBuilder = HttpClient.getInstance().getBuilder();

        mRetrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()));
    }


    public static RetrofitClient getInstance() {

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }

        }
        return instance;
    }


    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }

    public Retrofit getRetrofit() {
        return mRetrofitBuilder.client(mOkHttpBuilder.build()).build();
    }

}
