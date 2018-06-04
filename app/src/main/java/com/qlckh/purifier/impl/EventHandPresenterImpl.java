package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.CommView;
import com.qlckh.purifier.presenter.EventHandPresenter;

/**
 * @author Andy
 * @date 2018/5/29 16:58
 * Desc:
 */
public class EventHandPresenterImpl implements EventHandPresenter {

    private CommView mView;

    @Override
    public void handEvent(int id, int state, String content, String imgPath) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .handEvent(id, state, content, imgPath)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CommonDao dao) {

                        mView.onSuccess(dao);
                        mView.dissmissLoading();
                    }
                });
    }

    @Override
    public void register(CommView view) {

        mView = view;
    }

    @Override
    public void unregister(CommView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
