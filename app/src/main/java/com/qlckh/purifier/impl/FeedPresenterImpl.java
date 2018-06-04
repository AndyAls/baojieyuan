package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.CommView;
import com.qlckh.purifier.presenter.FeedPresenter;
import com.qlckh.purifier.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/22 11:50
 * Desc:
 */
public class FeedPresenterImpl implements FeedPresenter {

    private CommView mView;

    @Override
    public void sumbit(String content) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .feedSubmit(UserConfig.getUserid(), UserConfig.getUserName(),content)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(CommonDao dao) {

                        mView.onSuccess(dao);
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
