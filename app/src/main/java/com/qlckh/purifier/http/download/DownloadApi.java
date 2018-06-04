package com.qlckh.purifier.http.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author Andy
 * @date   2018/5/15 18:41
 * Desc:    DownloadApi.java
 */

public interface DownloadApi {


    /**
     * 大文件官方建议用 @Streaming 来进行注解，不然会出现IO异常，小文件可以忽略不注入
     *
     * @param fileUrl 地址
     * @return ResponseBody
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
