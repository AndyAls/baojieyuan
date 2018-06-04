package com.qlckh.purifier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseActivity;
import com.qlckh.purifier.user.UserConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/20 1:54
 * Desc:
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.offline_button)
    Button offlineButton;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {

        setTitle("设置");
        goBack();
    }

    @Override
    public void initDate() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }


    @OnClick(R.id.offline_button)
    public void onViewClicked() {
        UserConfig.reset();
        UserConfig.userInfo=null;
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
