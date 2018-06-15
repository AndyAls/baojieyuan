package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;

/**
 * @author Andy
 * @date 2018/5/22 11:49
 * Desc:
 */
public interface FeedPresenter extends BasePresenter<CommView>{

    void sumbit(String content,String img);

}
