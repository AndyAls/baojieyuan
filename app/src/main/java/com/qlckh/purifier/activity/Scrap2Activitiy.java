package com.qlckh.purifier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/22 13:22
 * Desc:
 */
public class Scrap2Activitiy extends BaseActivity {
    @BindView(R.id.bt_scan)
    Button btScan;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;


    @Override
    protected int getContentView() {
        return R.layout.activity_mark;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        setTitle("桶报废");
        goBack();
        llScan.setVisibility(View.GONE);
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


    @OnClick(R.id.bt_scan)
    public void onViewClicked() {
        startActivity(new Intent(this,ScanActivity.class));
    }
}
