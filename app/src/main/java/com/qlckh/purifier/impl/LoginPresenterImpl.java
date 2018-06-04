package com.qlckh.purifier.impl;

import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.LoginPresenter;
import com.qlckh.purifier.user.UseDo;
import com.qlckh.purifier.usercase.LoginUserCase;

/**
 * @author Andy
 * @date 2018/5/15 20:17
 * Desc:
 */
public class LoginPresenterImpl implements LoginPresenter {

    private static final String TAG = "LoginPresenterImpl";
    private LoginView mView;
    private LoginUserCase loginUserCase;

    @Override
    public void login(String name, String pwd, int type) {

        mView.showLoading();
        if (loginUserCase==null){
            loginUserCase = new LoginUserCase();
        }
        loginUserCase.login(name,pwd,type).subscribe(new CommonObserver<UseDo>() {
            @Override
            protected void onError(String errorMsg) {
                XLog.e(TAG,errorMsg);
                mView.dissmissLoading();
                mView.showError(errorMsg);
            }

            @Override
            protected void onSuccess(UseDo userInfo) {

                mView.dissmissLoading();
                mView.getUser(userInfo);
                XLog.e(TAG,userInfo);
            }

        });
    }

    @Override
    public void register(LoginView view) {
        mView=view;

    }

    @Override
    public void unregister(LoginView view) {
        if (mView!=null){
            mView=null;
        }

    }
}
