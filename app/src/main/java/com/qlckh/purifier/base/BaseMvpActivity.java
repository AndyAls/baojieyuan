package com.qlckh.purifier.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author Andy
 * @date 2018/5/14 17:01
 * Desc: MVP架构基类
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    /**
     * @return 子类Presenter
     */
    protected abstract P initPresenter();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        if (mPresenter != null){
            mPresenter.register(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.unregister(this);
        }
    }
}
