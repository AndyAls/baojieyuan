package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.dao.HomeDao;

/**
 * @author Andy
 * @date 2018/5/22 13:48
 * Desc:
 */
public interface ScrapPresenter extends BasePresenter<CommView>{

    void scrapSubmit(HomeDao dao,String content,String address,String img);
}
