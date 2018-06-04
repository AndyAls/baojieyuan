package com.qlckh.purifier.base;

/**
 * @author Andy
 * @date   2018/5/14 17:03
 * Desc:    Presenter基类
 */
public interface BasePresenter<T extends IBaseView> {

    /**
     * view注册presenter
     * @param view view
     */
    void register(T view);

    /**
     * view注销presenter
     * @param view view
     */
    void unregister(T view);
}
