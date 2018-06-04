package com.qlckh.purifier.base;

/**
 * @author Andy
 * @date 2018/5/14 15:43
 * Desc:
 */
public interface IBaseView {

    void initView();
    void initDate();
    void showError(String msg);
    void release();
}
