package com.qlckh.purifier.presenter;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;

/**
 * @author Andy
 * @date 2018/5/29 16:31
 * Desc:
 */
public class MessageDetailPresenterImpl implements MessageDetailPresenter {

    private CommView mView;

    @Override
    public void sure(int id, int state) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .sure(id,state)
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
