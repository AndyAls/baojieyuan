package com.qlckh.purifier.presenter;

import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.view.MainView;

/**
 * @author Andy
 * @date 2018/5/18 11:47
 * Desc:
 */
public interface AttendancePresenter extends MainPresenter{

    void sign(int userId,String address,int type);

     interface AttendanceView extends MainView {

         void onSiginSuccess(CommonDao dao,String msg);

    }
}
