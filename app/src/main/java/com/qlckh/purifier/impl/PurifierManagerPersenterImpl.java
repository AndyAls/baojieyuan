package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.CunListDao;
import com.qlckh.purifier.dao.ScanListDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.PurifierManagerPersenter;
import com.qlckh.purifier.user.UserConfig;

/**
 * @author Andy
 * @date 2018/6/12 13:14
 * Desc:
 */
public class PurifierManagerPersenterImpl implements PurifierManagerPersenter {

    private PurifierView mView;

    @Override
    public void getCunList() {

        RxHttpUtils.createApi(ApiService.class)
                .getCunList(UserConfig.getUserid()+"")
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CunListDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CunListDao dao) {
                        mView.onCunSuccess(dao);
                    }
                });

    }

    @Override
    public void scanList(String streetId, String startTime,String cunid) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                    .getScanList(streetId,startTime,cunid,UserConfig.getUserid()+"")
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<ScanListDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();

                    }

                    @Override
                    protected void onSuccess(ScanListDao dao) {
                        mView.dissmissLoading();
                        mView.onScanSuccess(dao);

                    }
                });
    }

    @Override
    public void register(PurifierView view) {

        mView = view;

    }

    @Override
    public void unregister(PurifierView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
