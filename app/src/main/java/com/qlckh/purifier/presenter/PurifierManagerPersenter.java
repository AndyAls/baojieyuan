package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.dao.CunListDao;
import com.qlckh.purifier.dao.ScanListDao;

/**
 * @author Andy
 * @date 2018/6/12 10:52
 * Desc:
 */
public interface PurifierManagerPersenter extends BasePresenter<PurifierManagerPersenter.PurifierView>{

    void getCunList();
    void scanList(String streetId,String startTime,String cunId);

    interface PurifierView extends CommView{

        void onCunSuccess(CunListDao dao);
        void onScanSuccess(ScanListDao dao);
    }

}
