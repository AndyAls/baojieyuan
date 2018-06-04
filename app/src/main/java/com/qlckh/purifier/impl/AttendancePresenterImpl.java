package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.AttendancePresenter;
import com.qlckh.purifier.view.MainView;

/**
 * @author Andy
 * @date 2018/5/18 11:55
 * Desc:
 */
public class AttendancePresenterImpl extends MainPresenterImpl implements AttendancePresenter {

    private AttendanceView mView;

    @Override
    public void sign(int userId, String address, int type) {
        RxHttpUtils.createApi(ApiService.class)
                .signin(userId,address,type)
                .compose(Transformer.<CommonDao>switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(CommonDao commonDao) {

                        try {
                            if (type==0){
                                mView.onSiginSuccess(commonDao,"上班签到成功");
                            }
                            if (type==1){
                                mView.onSiginSuccess(commonDao,"下班签到成功");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void register(MainView view) {

        super.register(view);
         mView = (AttendanceView) view;
    }

    @Override
    public void unregister(MainView view) {

        super.unregister(view);
        if (mView!=null){
            mView=null;
        }
    }
}
