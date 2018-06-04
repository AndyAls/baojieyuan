package com.qlckh.purifier.presenter;

import com.qlckh.purifier.base.BasePresenter;
import com.qlckh.purifier.dao.InMsgDao;
import com.qlckh.purifier.dao.OutMessageDao;

/**
 * @author Andy
 * @date 2018/5/22 18:42
 * Desc:
 */
public interface MessagePresenter extends BasePresenter<MessagePresenter.MessageView> {

    void getInMessageList(String tel, String page);
    void getOutMessageList(String tel, String page);
    void updateMessageState(int  messageId,int position);

    interface MessageView extends CommView {

       void onInMessageSuccess(InMsgDao dao);
        void onOutMessageSuccess(OutMessageDao dao);
    }
}
