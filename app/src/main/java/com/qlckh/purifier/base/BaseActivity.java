package com.qlckh.purifier.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.qlckh.purifier.R;
import com.qlckh.purifier.common.IToast;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Andy
 * @date 2018/5/14 11:24
 * Desc:  基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    //<editor-fold desc="控件初始化">
    @BindView(R.id.ib_back)
    protected ImageButton ibBack;
     @BindView(R.id.title)
     protected TextView title;
    @BindView(R.id.ib_right)
    protected TextView ibRight;
    @BindView(R.id.header)
    protected LinearLayout header;
    protected FrameLayout flContainer;
    private Unbinder unbinder;
    protected BaseActivity mActivity;
    //</editor-fold>

    //<editor-fold desc="基类初始化">
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        ImmersionBar.with(this).init();
        mActivity = this;
        initView();
        initDate();
        initSlidr();

    }
    //</editor-fold>

    //<editor-fold desc="滑动退出">
    protected void initSlidr() {

        SlidrConfig config=new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)
                .edge(true)
                .edgeSize(0.2f)
                .build();
        Slidr.attach(this,config);
    }
    //</editor-fold>

    //<editor-fold desc="视图初始化">
    protected abstract int getContentView();
    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setFitsSystemWindows(true);
        }
        flContainer=view.findViewById(R.id.fl_container);
        View childView = LayoutInflater.from(this).inflate(layoutResID, null);
        flContainer.addView(childView);
        super.setContentView(view);
    }
    //</editor-fold>

    //<editor-fold desc="释放资源">
    @Override
    protected void onDestroy() {
        release();
        super.onDestroy();
        unbinder.unbind();
        ImmersionBar.with(this).destroy();
    }
//</editor-fold>

    //<editor-fold desc="软键盘控制">
    /**
     * 分配触摸事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 判断动作，如点击，按下等
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 得到获取焦点的view
            View v = getCurrentFocus();
            // 点击的位子
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        // 判断view是否为空，
        if (v != null && (v instanceof EditText)) {
            // view是否为EditText
            int[] l = {0, 0};
            // 判断view是否为空，
            v.getLocationInWindow(l);
            // 计算坐标
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            // 比较坐标
            // 点击EditText的事件，忽略它。
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }


    /**
     * 多种隐藏软件盘方法的其中一种
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert im != null;
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    //</editor-fold>

    //<editor-fold desc="基类抽取">
    protected void showShort(String msg) {
        IToast.showShort(this, msg);
    }

    protected void showLong(String msg) {
        IToast.showLong(this, msg);
    }

    protected void goBack(){
        ibBack.setOnClickListener(v -> finish());
    }
    protected void setTitle(String msg){
        title.setText(msg);
    }

    protected boolean isEmpty(String msg){
        return TextUtils.isEmpty(msg)||"".equals(msg)||"null".equals(msg);
    }
    //</editor-fold>
}
