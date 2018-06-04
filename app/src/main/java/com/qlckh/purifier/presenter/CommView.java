package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.dao.CommonDao;

/**
 * @author Andy
 * @date 2018/5/22 11:47
 * Desc:
 */
public interface CommView extends IBaseView {
    void onSuccess(CommonDao dao);
    void showLoading();
    void dissmissLoading();
}
