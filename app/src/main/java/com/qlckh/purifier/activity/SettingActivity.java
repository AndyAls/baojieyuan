package com.qlckh.purifier.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseActivity;
import com.qlckh.purifier.user.UserConfig;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/20 1:54
 * Desc:
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.offline_button)
    Button offlineButton;
    @BindView(R.id.bt_set_font)
    Button btSetFont;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
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

    @OnClick({R.id.bt_set_font, R.id.offline_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_set_font:

                startActivity(new Intent(this,SetingFontActivity.class));
                break;
            case R.id.offline_button:
                UserConfig.reset();
                UserConfig.userInfo = null;
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

                default:
        }
    }
}
