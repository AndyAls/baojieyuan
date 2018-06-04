package com.qlckh.purifier.http.base;

import com.qlckh.purifier.http.exception.ApiException;
import com.qlckh.purifier.http.interfaces.IStringSubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
/**
 * @author Andy
 * @date   2018/5/15 18:40
 * Desc:    BaseStringObserver.java
 */
public abstract class BaseStringObserver implements Observer<String>, IStringSubscriber {

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
    public void onNext(String string) {
        doOnNext(string);
    }

    @Override
    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        doOnError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }

}
