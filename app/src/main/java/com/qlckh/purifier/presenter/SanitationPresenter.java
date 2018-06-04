package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.dao.HomeDao;

/**
 * @author Andy
 * @date 2018/5/20 1:13
 * Desc:
 */
public interface SanitationPresenter extends BasePresenter<CompositeView>{

    void sanitationSubmit(HomeDao dao,int envScore,String address,String tel,String imgs);
}
