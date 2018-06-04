package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.dao.EventListDao;

/**
 * @author Andy
 * @date 2018/5/29 9:29
 * Desc:
 */
public interface TaskWorkingPresenter extends BasePresenter<TaskWorkingPresenter.TaskWorkingView>{

    void getEventList(int page);

    interface TaskWorkingView extends CommView{

       void onEventSuccess(EventListDao dao);
    }
}
