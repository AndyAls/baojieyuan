package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.dao.Comm2Dao;

/**
 * @author Andy
 * @date 2018/5/20 1:19
 * Desc:
 */
public interface CompositeView extends IBaseView{
    void showLoading();
    void dissmissLoading();
    void onSuccess(Comm2Dao dao);
}
