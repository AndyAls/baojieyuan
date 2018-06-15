package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.Comm2Dao;
import com.qlckh.purifier.dao.HomeDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.CommView;
import com.qlckh.purifier.presenter.ScrapPresenter;
import com.qlckh.purifier.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/22 13:52
 * Desc:
 */
public class ScrapPresenterImpl implements ScrapPresenter {

    private CommView mView;

    @Override
    public void scrapSubmit(HomeDao dao, String content,String address,String img) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .scrapSubmit(Integer.parseInt(dao.getId()),dao.getUsername(),address,Integer.parseInt(dao.getCunid()),
                        content, UserConfig.getUserid(),UserConfig.getUserName(),UserConfig.getType()-1,img)
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
