package com.qlckh.purifier.http.interfaces;

import io.reactivex.disposables.Disposable;

/**
 * @author Andy
 * @date   2018/5/15 18:49
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    IStringSubscriber.java
 */

public interface IStringSubscriber {

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
     * @param string data
     */
    void doOnNext(String string);

    /**
     * 请求完成回调
     */
    void doOnCompleted();
}
