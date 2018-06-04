package com.qlckh.purifier.http.download;

import com.qlckh.purifier.http.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;

import static com.qlckh.purifier.http.utils.ToastUtils.showToast;

/**
 * @author Andy
 * @date   2018/5/15 18:40
 * Desc:    BaseDownloadObserver.java
 */

public abstract class BaseDownloadObserver implements Observer<ResponseBody> {

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void doOnError(String errorMsg);


    @Override
    public void onError(@NonNull Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    private void setError(String errorMsg) {
        showToast(errorMsg);
        doOnError(errorMsg);
    }

}
