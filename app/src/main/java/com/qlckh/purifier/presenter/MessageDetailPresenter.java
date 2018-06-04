package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;

/**
 * @author Andy
 * @date 2018/5/29 16:29
 * Desc:
 */
public interface MessageDetailPresenter extends BasePresenter<CommView>{

    void sure(int id,int state);
}
