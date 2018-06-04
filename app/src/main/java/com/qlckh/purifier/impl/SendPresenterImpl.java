package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.CuntryDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.SendPresenter;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.usercase.CuntryListModel;

/**
 * @author Andy
 * @date 2018/5/31 9:38
 * Desc:
 */
public class SendPresenterImpl implements SendPresenter {

    private SendView mView;
    private CuntryListModel model;

    @Override
    public void sendSumbit(String gotoId, String title, String content) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .sendSumbit(UserConfig.getUserid()+"",gotoId,title,content)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);

                    }

                    @Override
                    protected void onSuccess(CommonDao dao) {
                        mView.dissmissLoading();
                        mView.onSuccess(dao);

                    }
                });
    }

    @Override
    public void getCuntryList() {
        mView.showLoading();
        if (model==null){
            model = new CuntryListModel();
        }
        model.getCuntryList().subscribe(new CommonObserver<CuntryDao>() {
            @Override
            protected void onError(String errorMsg) {
                mView.showError(errorMsg);
                mView.dissmissLoading();
            }

            @Override
            protected void onSuccess(CuntryDao dao) {
                mView.onCuntrySuccess(dao);
                mView.dissmissLoading();

            }
        });

    }

    @Override
    public void register(SendView view) {

        mView = view;
    }

    @Override
    public void unregister(SendView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
