package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.user.UseDo;
import com.qlckh.purifier.user.UserInfo;

/**
 * @author Andy
 * @date 2018/5/15 20:14
 * Desc:
 */
public interface LoginPresenter extends BasePresenter<LoginPresenter.LoginView>{

    void login(String name,String pwd,int type);

    interface LoginView extends IBaseView {

        void showLoading();
        void dissmissLoading();
        void getUser(UseDo info);
    }
}
