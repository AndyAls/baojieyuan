package com.qlckh.purifier.view;

import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.dao.BannerDao;

/**
 * @author Andy
 * @date 2018/5/18 21:12
 * Desc:
 */
public interface MainView extends IBaseView {
    void onSuccess(BannerDao dao);
}
