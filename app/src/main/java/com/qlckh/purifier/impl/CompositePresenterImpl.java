package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.Comm2Dao;
import com.qlckh.purifier.dao.HomeDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.CompositePresenter;
import com.qlckh.purifier.presenter.CompositeView;
import com.qlckh.purifier.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/19 22:22
 * Desc:
 */
public class CompositePresenterImpl implements CompositePresenter {

    private CompositeView mView;

    @Override
    public void sumbit(HomeDao dao, int categoryScore, int bucketScore, int putScore, int totalScore, String address, String tel, String imgs) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .compositeSubmit(dao.getUsername(),Integer.parseInt(dao.getId()), UserConfig.userInfo.getFullname(),UserConfig.getUserid(),
                        Integer.parseInt(dao.getCunid()),
                        categoryScore,bucketScore,putScore,totalScore,address,tel,imgs)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<Comm2Dao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(Comm2Dao comm2Dao) {
                        mView.onSuccess(comm2Dao);
                        mView.dissmissLoading();

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
