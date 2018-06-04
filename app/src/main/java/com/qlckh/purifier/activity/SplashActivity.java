package com.qlckh.purifier.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseActivity;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.impl.LoginPresenterImpl;
import com.qlckh.purifier.presenter.LoginPresenter;
import com.qlckh.purifier.user.UseDo;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.view.LoadingView;

import butterknife.BindView;

/**
 * @author Andy
 * @date 2018/5/14 11:00
 * Desc:   启动界面
 */
public class SplashActivity extends BaseMvpActivity<LoginPresenter> implements LoginPresenter.LoginView {

    @BindView(R.id.iv_bg)
    ImageView ivBg;

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        header.setVisibility(View.GONE);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        ImmersionBar.with(this).transparentBar().init();
    }

    @Override
    public void initDate() {

        if (UserConfig.isLogin()) {
           mPresenter.login(UserConfig.getUserName(),UserConfig.getPwd(),UserConfig.getType());
        } else {
            toLogin();
        }
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
        toLogin();
    }

    @Override
    public void release() {
//        RxHttpUtils.cancelAllRequest();

    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void dissmissLoading() {
    }

    @Override
    public void getUser(UseDo info) {
        if (info.getStatus()==1){

            toMian();
            UserConfig.userInfo=info.getData();
            UserConfig.savaLogin(true);
        }else {
            showShort(info.getMsg());
            toLogin();
            UserConfig.savaLogin(false);

        }

    }

    private void toMian() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
        overridePendingTransition(0,0);
    }

    private void toLogin() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
        overridePendingTransition(0,0);
    }
}
