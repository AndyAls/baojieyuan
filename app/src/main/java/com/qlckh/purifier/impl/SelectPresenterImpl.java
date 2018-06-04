package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.BaojieDao;
import com.qlckh.purifier.dao.GuanDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.SelectPresenter;

/**
 * @author Andy
 * @date 2018/5/28 14:17
 * Desc:
 */
public class SelectPresenterImpl implements SelectPresenter {

    private SelectView mView;

    @Override
    public void getGuanList(String address,int pos) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getGuanList(address)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<GuanDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(GuanDao guanDao) {

                        mView.onGuanSuccess(guanDao,pos);
                    }
                });
    }

    @Override
    public void getBaojie(String address,int pos) {

        RxHttpUtils.createApi(ApiService.class)
                .getBaojieList(address)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaojieDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(BaojieDao baojieDao) {

                        mView.onBaojieSuccess(baojieDao,pos);
                        mView.dissmissLoading();
                    }
                });
    }

    @Override
    public void register(SelectView view) {

        mView = view;

    }

    @Override
    public void unregister(SelectView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
