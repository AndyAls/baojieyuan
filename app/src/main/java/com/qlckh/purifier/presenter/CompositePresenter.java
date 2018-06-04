package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.dao.HomeDao;

/**
 * @author Andy
 * @date 2018/5/19 22:15
 * Desc:
 */
public interface CompositePresenter extends BasePresenter<CompositeView>{

    void sumbit(HomeDao dao,int categoryScore,int bucketScore,int putScore,int totalScore,String address,String tel,String imgs);
}
