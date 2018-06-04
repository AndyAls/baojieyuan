package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.EventListDao;
import com.qlckh.purifier.dao.InMsgDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.TaskWorkingPresenter;
import com.qlckh.purifier.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/29 9:37
 * Desc:
 */
public class TaskWorkingPresenterImpl implements TaskWorkingPresenter {

    private TaskWorkingView mView;

    @Override
    public void getEventList(int page) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getEventList(UserConfig.userInfo.getTopflag(),UserConfig.getUserid(),page,20)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<EventListDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();

                    }

                    @Override
                    protected void onSuccess(EventListDao dao) {

                        mView.dissmissLoading();
                        mView.onEventSuccess(dao);
                    }
                });


    }

    @Override
    public void register(TaskWorkingView view) {

        mView = view;
    }

    @Override
    public void unregister(TaskWorkingView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
