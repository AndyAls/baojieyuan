package com.qlckh.purifier.activity;

import android.content.Intent;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseActivity;
import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/6/6 16:44
 * Desc:
 */
public class SetingFontActivity extends BaseActivity {

    private static final String TAG = "SetingFontActivity";
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.tv_pre)
    TextView tvPre;
    private float p=0.0f;

    @Override
    protected int getContentView() {
        return R.layout.activity_set_font;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {
        goBack();
        setTitle("设置字体大小");
        if (textsize>=1.0f){
            tvPre.setTextSize(TypedValue.COMPLEX_UNIT_SP,18*textsize);
            seekBar.setProgress((int) ((textsize-1.0f)*100/0.45));
            XLog.e(TAG,textsize,18*textsize);
        }else {
            tvPre.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            seekBar.setProgress(0);
            XLog.e(TAG,textsize);
        }

    }

    @Override
    public void initDate() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                p = (float) (progress * 0.45 / 100);
                tvPre.setTextSize(TypedValue.COMPLEX_UNIT_SP,18*(1.0f+ p));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        SpUtils.putParam(this,TEXT_SIZE,p);
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
