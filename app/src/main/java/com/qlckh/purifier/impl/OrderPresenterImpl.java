package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.CuntryDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.OrderPresenter;
import com.qlckh.purifier.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/26 13:12
 * Desc:
 */
public class OrderPresenterImpl implements OrderPresenter {

    private OrderView mView;

    @Override
    public void getCuntryList() {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getCuntryList()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CuntryDao>() {
                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(CuntryDao cuntryDao) {

                        mView.onCuntrySuccess(cuntryDao);
                        mView.dissmissLoading();
                    }
                });
    }

    @Override
    public void orderSumbit(String title, String content, String imgpath, String guanId, String baojieId) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .order(title,content,imgpath, UserConfig.getUserid()+"",guanId,baojieId)
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
    public void register(OrderView view) {

        mView = view;

    }

    @Override
    public void unregister(OrderView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
