package com.qlckh.purifier.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.adapter.MessageAdapter;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.common.ThreadPool;
import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.EventListDao;
import com.qlckh.purifier.dao.InMsgDao;
import com.qlckh.purifier.dao.OutMessageDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.impl.MessagePresenterImpl;
import com.qlckh.purifier.presenter.MessagePresenter;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.usercase.BadgeEvent;
import com.qlckh.purifier.usercase.MessageEvent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/22 11:43
 * Desc:
 */
public class MessageActivity extends BaseMvpActivity<MessagePresenter> implements MessagePresenter.MessageView {
    public static final String MESSAGE_ACTION = "MESSAGE_ACTION";
    public static final String MESAGE_DAO = "MESAGE_DAO";
    public static final String MESSAGE_SOURCE = "MESSAGE_SOURCE";
    private static final String TAG = "MessageActivity";
    @BindView(R.id.tv_in_notice)
    TextView tvInNotice;
    @BindView(R.id.tv_out_notice)
    TextView tvOutNotice;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private int source = 0;
    private int page = 0;
    private List<InMsgDao.InMsg> mInDatas = new ArrayList<>();
    private List<OutMessageDao.OutMessage> mOutDatas = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {



        setTitle("我的消息");
        goBack();
        tvInNotice.setSelected(true);
        tvOutNotice.setSelected(false);
        initRefresh();
        initRecyclerView();
    }

    private void initRecyclerView() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private void initRefresh() {

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                page++;
                if (source == 0) {
                    mPresenter.getInMessageList(UserConfig.getUserName(), page + "");
                } else {
                    mPresenter.getOutMessageList(UserConfig.getUserName(), page + "");
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                if (source == 0) {
                    mPresenter.getInMessageList(UserConfig.getUserName(), page + "");
                } else {
                    mPresenter.getOutMessageList(UserConfig.getUserName(), page + "");
                }

            }
        });



    }

    @Override
    public void initDate() {

        refresh.autoRefresh();
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
        refresh.finishRefresh();
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }

    @Override
    protected MessagePresenter initPresenter() {
        return new MessagePresenterImpl();
    }


    @Override
    public void onSuccess(CommonDao dao) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissmissLoading() {

    }


    @OnClick({R.id.tv_in_notice, R.id.tv_out_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_in_notice:
                tvOutNotice.setSelected(false);
                tvInNotice.setSelected(true);
                tvOutNotice.setEnabled(false);
                tvInNotice.setEnabled(false);
                source = 0;
                refresh.autoRefresh();
                break;
            case R.id.tv_out_notice:
                tvOutNotice.setSelected(true);
                tvInNotice.setSelected(false);
                tvOutNotice.setEnabled(false);
                tvInNotice.setEnabled(false);
                source = 1;
                refresh.autoRefresh();
                break;
            default:
        }

    }

    @Override
    public void onInMessageSuccess(InMsgDao dao) {

        refresh.finishRefresh();
        refresh.finishLoadMore();
        List<InMsgDao.InMsg> data = dao.getData();
        if (data != null) {
            if (data.size() == 0) {
                refresh.setEnableLoadMore(false);
            } else {
                if (dao.getTotal() > data.size()) {
                    refresh.setEnableLoadMore(true);
                } else {
                    refresh.finishLoadMoreWithNoMoreData();
                }

            }
            if (page == 0) {
                mInDatas.clear();
                mInDatas.addAll(data);
                setAdapter();
            } else {
                mInDatas.addAll(data);
                setAdapter();
            }
        }


        XLog.e(TAG,"0",System.currentTimeMillis());
        new Handler().postDelayed(() -> {
            XLog.e(TAG,"1",System.currentTimeMillis());
            tvInNotice.setEnabled(true);
            tvOutNotice.setEnabled(true);
        },800);

        postBadge();
    }
    private void postBadge() {
        int caiCount=0;

        if (mInDatas!=null){
            for (int i = 0; i < mInDatas.size(); i++) {

                InMsgDao.InMsg msg = mInDatas.get(i);
                if (msg.getIsread()==0){
                    caiCount++;
                }
            }
        }
        if (mOutDatas!=null){
            for (int i = 0; i < mOutDatas.size(); i++) {
                OutMessageDao.OutMessage outMessage = mOutDatas.get(i);
                if (outMessage.getIsread()==0){
                    caiCount++;
                }
            }
        }
        MessageEvent event = new MessageEvent();
        event.setUnRead(caiCount);
        EventBus.getDefault().post(event);
    }
    private void setAdapter() {


        MessageAdapter adapter = new MessageAdapter(this, source, mInDatas, mOutDatas);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setonItemClickListener(this::onItemClick);

    }

    private void toMessageDetail(int position) {
        Intent intent = new Intent(this, MessageDetailActivitiy.class);

        intent.setAction(MESSAGE_ACTION);
        if (source == 0) {
            InMsgDao.InMsg msgBean = mInDatas.get(position);
            intent.putExtra(MESAGE_DAO, msgBean);
        } else {
            OutMessageDao.OutMessage msgDao = mOutDatas.get(position);
            intent.putExtra(MESAGE_DAO, msgDao);

        }
        intent.putExtra(MESSAGE_SOURCE, source);
        startActivity(intent);

    }

    @Override
    public void onOutMessageSuccess(OutMessageDao dao) {

        refresh.finishRefresh();
        refresh.finishLoadMore();
        List<OutMessageDao.OutMessage> data = dao.getData();
        if (data != null) {
            if (data.size() == 0) {
                refresh.setEnableLoadMore(false);
            } else {
                if (dao.getTotal() > data.size()) {
                    refresh.setEnableLoadMore(true);
                } else {
                    refresh.finishLoadMoreWithNoMoreData();
                }

            }
            if (page == 0) {
                mOutDatas.clear();
                mOutDatas.addAll(data);
                setAdapter();
            } else {
                mOutDatas.addAll(data);
                setAdapter();
            }
        }
        XLog.e(TAG,"0",System.currentTimeMillis());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                XLog.e(TAG,"1",System.currentTimeMillis());
                tvInNotice.setEnabled(true);
                tvOutNotice.setEnabled(true);
            }
        },800);
        postBadge();
    }

    private void onItemClick(View view, int position) {
        int id = -1;
        if (source == 0) {
            id = mInDatas.get(position).getId();
        } else {
            id = mOutDatas.get(position).getId();
        }
        mPresenter.updateMessageState(id, source);
        toMessageDetail(position);
    }
}
