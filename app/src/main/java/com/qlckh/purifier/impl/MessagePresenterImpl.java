package com.qlckh.purifier.impl;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.base.IBaseView;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.InMsgDao;
import com.qlckh.purifier.dao.OutMessageDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.MessagePresenter;
import com.qlckh.purifier.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/22 21:09
 * Desc:
 */
public class MessagePresenterImpl implements MessagePresenter {

    private MessageView mView;


    @Override
    public void getInMessageList(String tel, String page) {
        RxHttpUtils.createApi(ApiService.class)
                .getMsgList(tel,"20",page)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<InMsgDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(InMsgDao dao) {

                        mView.onInMessageSuccess(dao);
                    }
                });
    }

    @Override
    public void getOutMessageList(String tel, String page) {

        RxHttpUtils.createApi(ApiService.class)
                .getMsgOutList(tel,"20",page)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<OutMessageDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(OutMessageDao outMessageDao) {

                        mView.onOutMessageSuccess(outMessageDao);
                    }
                });
    }


    @Override
    public void updateMessageState(int messageId,int status) {

        if (status==0){
            RxHttpUtils.createApi(ApiService.class)
                    .updateMessageState(messageId)
                    .compose(Transformer.switchSchedulers())
                    .subscribe(new CommonObserver<CommonDao>() {
                        @Override
                        protected void onError(String errorMsg) {
                            mView.showError(errorMsg);
                        }

                        @Override
                        protected void onSuccess(CommonDao dao) {

                            mView.onSuccess(dao);
                        }
                    });
        }else {
            RxHttpUtils.createApi(ApiService.class)
                    .updateMessageState2(messageId)
                    .compose(Transformer.switchSchedulers())
                    .subscribe(new CommonObserver<CommonDao>() {
                        @Override
                        protected void onError(String errorMsg) {
                            mView.showError(errorMsg);
                        }

                        @Override
                        protected void onSuccess(CommonDao dao) {
                            mView.onSuccess(dao);
                        }
                    });
        }


    }

    @Override
    public void register(MessageView view) {
        mView = view;
    }

    @Override
    public void unregister(MessageView view) {
        if (mView!=null){
            mView=null;
        }

    }
}
