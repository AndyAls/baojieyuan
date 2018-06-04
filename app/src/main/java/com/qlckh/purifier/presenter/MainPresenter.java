package com.qlckh.purifier.presenter;

import android.content.Context;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.dao.BannerDao;
import com.qlckh.purifier.dao.MainDao;
import com.qlckh.purifier.view.MainView;
import com.youth.banner.Banner;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/17 11:12
 * Desc:
 */
public interface MainPresenter extends BasePresenter<MainView> {

    void getBanner();

    List<MainDao> getDatas();

    void showBanner(Context context, List<BannerDao.ImageDao> imgs, Banner banner);

}
