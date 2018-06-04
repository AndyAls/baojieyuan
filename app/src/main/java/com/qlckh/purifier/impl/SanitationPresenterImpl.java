package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.dao.Comm2Dao;
import com.qlckh.purifier.dao.HomeDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.CompositePresenter;
import com.qlckh.purifier.presenter.CompositeView;
import com.qlckh.purifier.presenter.SanitationPresenter;
import com.qlckh.purifier.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/20 1:17
 * Desc:
 */
public class SanitationPresenterImpl implements SanitationPresenter {

    private CompositeView mView;
    private static final String TAG = "test";

    @Override
    public void sanitationSubmit(HomeDao dao, int envScore, String address, String tel, String imgs) {
        XLog.e(TAG,imgs);
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .sanitationSubmit(dao.getUsername(),Integer.parseInt(dao.getId()),
                        UserConfig.userInfo.getFullname(),UserConfig.getUserid(),
                        Integer.parseInt(dao.getCunid()),
                        envScore,envScore,address,tel,imgs)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<Comm2Dao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(Comm2Dao dao) {
                        mView.dissmissLoading();
                        mView.onSuccess(dao);

                    }
                });
    }

    @Override
    public void register(CompositeView view) {
        mView = view;
    }

    @Override
    public void unregister(CompositeView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
