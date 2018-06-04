package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.dao.CuntryDao;

/**
 * @author Andy
 * @date 2018/5/25 14:25
 * Desc:
 */
public interface SendPresenter extends BasePresenter<SendPresenter.SendView> {

    void sendSumbit(String gotoId,String title,String content);
    void getCuntryList();

    interface SendView extends CommView {

        void onCuntrySuccess(CuntryDao dao);

    }
}
