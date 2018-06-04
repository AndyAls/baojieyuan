package com.qlckh.purifier.http.base;


import com.qlckh.purifier.http.bean.BaseData;
import com.qlckh.purifier.http.exception.ApiException;
import com.qlckh.purifier.http.interfaces.IDataSubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Andy
 * @date   2018/5/15 18:35
 * Desc:    BaseDataObserver.java
 */
public abstract class BaseDataObserver<T> implements Observer<BaseData<T>>, IDataSubscriber<T> {

    /**
     * 是否隐藏toast
     *
     * @return
     */
    protected boolean isHideToast() {
        return false;
    }

    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(BaseData<T> baseData) {
        doOnNext(baseData);
    }

    @Override
    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }


    private void setError(String errorMsg) {
        doOnError(errorMsg);
    }

}
