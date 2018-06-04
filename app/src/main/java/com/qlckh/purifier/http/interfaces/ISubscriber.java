package com.qlckh.purifier.http.interfaces;

import io.reactivex.disposables.Disposable;

/**
 * @author Andy
 * @date   2018/5/15 18:46
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    ISubscriber.java
 */

public interface ISubscriber<T> {

    /**
     * doOnSubscribe 回调
     *
     * @param d Disposable
     */
    void doOnSubscribe(Disposable d);

    /**
     * 错误回调
     *
     * @param errorMsg 错误信息
     */
    void doOnError(String errorMsg);

    /**
     * 成功回调
     *
     * @param t 泛型
     */
    void doOnNext(T t);

    /**
     * 请求完成回调
     */
    void doOnCompleted();
}
