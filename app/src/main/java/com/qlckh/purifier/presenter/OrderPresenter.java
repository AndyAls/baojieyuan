package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.dao.CuntryDao;

/**
 * @author Andy
 * @date 2018/5/26 11:39
 * Desc:
 */
public interface OrderPresenter extends BasePresenter<OrderPresenter.OrderView>{

        void getCuntryList();
        void orderSumbit(String title,String content,String imgpath,String guanId,String baojieId);
    interface OrderView extends CommView{

        void onCuntrySuccess(CuntryDao dao);

    }
}
