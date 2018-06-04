package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.dao.BaojieDao;
import com.qlckh.purifier.dao.GuanDao;

/**
 * @author Andy
 * @date 2018/5/26 13:43
 * Desc:
 */
public interface SelectPresenter extends BasePresenter<SelectPresenter.SelectView>{

    void getGuanList(String address,int position);
    void getBaojie(String address,int position);
    interface SelectView extends CommView {

       void onGuanSuccess(GuanDao guanDao,int pos);
       void onBaojieSuccess(BaojieDao baojieDao,int pos);
    }
}
