package com.qlckh.purifier.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.common.GlideApp;
import com.qlckh.purifier.common.GlideRequest;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.EventListDao;
import com.qlckh.purifier.dao.InMsgDao;
import com.qlckh.purifier.dao.OutMessageDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.presenter.CommView;
import com.qlckh.purifier.presenter.MessageDetailPresenter;
import com.qlckh.purifier.presenter.MessageDetailPresenterImpl;
import com.qlckh.purifier.preview.ImgInfo;
import com.qlckh.purifier.preview.PrePictureActivity;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.view.LoadingView;
import com.qlckh.purifier.view.PicGridView;
import com.qlckh.purifier.view.richtextview.ImageLoader;
import com.qlckh.purifier.view.richtextview.XRichText;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/24 11:57
 * Desc:
 */
public class MessageDetailActivitiy extends BaseMvpActivity<MessageDetailPresenter> implements CommView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.picItems)
    PicGridView picItemss;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.bt_refuse)
    Button btRefuse;
    @BindView(R.id.tv_task_time)
    TextView tvTaskTime;
    @BindView(R.id.tv_task_content)
    TextView tvTaskContent;
    @BindView(R.id.task_picItems)
    PicGridView taskPicItems;
    @BindView(R.id.ll_task)
    LinearLayout llTask;
    @BindView(R.id.ll_sure)
    LinearLayout llSure;
    @BindView(R.id.tv_rich_content)
    XRichText tvRichContent;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
    private EventListDao.EventDao eventDao;
    private int eventId = -1;

    @Override
    protected int getContentView() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        goBack();
    }

    @Override
    public void initDate() {

        Intent intent = getIntent();
        String action = intent.getAction();
        switch (Objects.requireNonNull(action)) {

            case TaskWorkingAcitivty.TASK_WORKING_ACTION:
                initTask(intent);
                break;

            case MessageActivity.MESSAGE_ACTION:
                initMessage(intent);
                break;

            default:
        }

    }

    private void initMessage(Intent intent) {
        int source = intent.getIntExtra(MessageActivity.MESSAGE_SOURCE, -1);
        if (source == 0) {
            InMsgDao.InMsg msg = intent.getParcelableExtra(MessageActivity.MESAGE_DAO);
            tvTitle.setText(msg.getTitle());
            tvContent.setVisibility(View.VISIBLE);
            tvRichContent.setVisibility(View.GONE);
            tvContent.setText(msg.getContent());
            tvTime.setText(format.format(Long.parseLong(msg.getCreatetime()) * 1000));
        } else {
            OutMessageDao.OutMessage msg = intent.getParcelableExtra(MessageActivity.MESAGE_DAO);
            tvTitle.setText(msg.getNewstitle());
            tvContent.setVisibility(View.GONE);
            tvRichContent.setVisibility(View.VISIBLE);
            tvTime.setText(msg.getCreatetime());
            tvRichContent.callback(new XRichText.Callback() {
                @Override
                public void onImageClick(List<String> urlList, int position) {

                    ArrayList<ImgInfo> imgInfos = new ArrayList<>();
                    if (urlList != null) {
                        for (int i = 0; i < urlList.size(); i++) {

                            ImgInfo info = new ImgInfo();
                            info.setBounds(new Rect());
                            info.setUrl(urlList.get(i));
                            imgInfos.add(info);
                        }
                    }

                    PrePictureActivity.start(MessageDetailActivitiy.this, imgInfos, position);
                }

                @Override
                public boolean onLinkClick(String url) {

                    Intent intent = new Intent(MessageDetailActivitiy.this, WebViewActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    return true;
                }

                @Override
                public void onFix(XRichText.ImageHolder holder) {

                    if (holder.getPosition() % 3 == 0) {
                        holder.setStyle(XRichText.Style.LEFT);
                    } else if (holder.getPosition() % 3 == 1) {
                        holder.setStyle(XRichText.Style.CENTER);
                    } else {
                        holder.setStyle(XRichText.Style.RIGHT);
                    }

                    //设置宽高
                    holder.setWidth(550);
                    holder.setHeight(400);

                }
            }).text(msg.getContent());
        }


    }

    private void initTask(Intent intent) {
        setTitle("事件详情");

        eventDao = intent.getParcelableExtra(TaskWorkingAcitivty.EVENT_DAO);
        initTaskView(eventDao);
        int status = eventDao.getStatus();
        int type = UserConfig.getType();
        switch (status) {
            //待处理
            case 0:
                if (type == 1) {
                    ibRight.setVisibility(View.VISIBLE);
                    ibRight.setText("立即处理");
                    ibRight.setOnClickListener(this::onClick);
                }
                break;
            //已处理
            case 1:
                if (type == 2) {
                    llSure.setVisibility(View.VISIBLE);
                } else {
                    llSure.setVisibility(View.GONE);
                }
                break;
            //处理完成
            case 2:
                break;
            default:
        }
    }

    /**
     * 去处理任务
     *
     * @param eventDao 事件实体
     */
    private void toHand(EventListDao.EventDao eventDao) {
        Intent intent = new Intent(this, EventHandActivity.class);
        intent.putExtra(TaskWorkingAcitivty.EVENT_DAO, eventDao);
        startActivity(intent);
        finish();
    }

    private void initTaskView(EventListDao.EventDao eventDao) {
        eventId = eventDao.getId();
        ArrayList<String> picList = new ArrayList<>();
        ArrayList<String> picList2 = new ArrayList<>();
        String image1 = eventDao.getImage1();
        if (!isEmpty(image1)) {
            String[] split = image1.split(",");
            picList.clear();
            for (String aSplit : split) {
                picList.add(ApiService.BASE_URL + aSplit);
            }
        }
        setPicShowModule(picList, picItemss);
        tvTitle.setText(eventDao.getTitle());
        tvTime.setText(format.format(Long.parseLong(eventDao.getSj_time()) * 1000));
        tvContent.setText(MessageFormat.format("\u3000\u3000{0}", eventDao.getContent()));
        if (isEmpty(eventDao.getContent2())) {
            llTask.setVisibility(View.GONE);
        } else {
            llTask.setVisibility(View.VISIBLE);
            tvTaskContent.setText(MessageFormat.format("\u3000\u3000{0}", eventDao.getContent2()));
            tvTaskTime.setText(String.format("处理时间:%s", format.format(Long.parseLong(eventDao.getSj_time()) * 1000)));
            if (!isEmpty(eventDao.getImage2())) {
                String[] split = eventDao.getImage2().split(",");
                for (String aSplit : split) {
                    picList2.add(ApiService.BASE_URL + aSplit);
                }
                setPicShowModule(picList2, taskPicItems);
            }
        }
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }


    /**
     * 设置标准照片
     */
    private void setPicShowModule(ArrayList<String> picUrlList, PicGridView picItems) {
        ArrayList<ImgInfo> mImgs = new ArrayList<>();
        picItems.setColumNum(2);
        picItems.removeAllViews();
        mImgs.clear();

        for (int i = 0; i < picUrlList.size(); i++) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setBackgroundColor(Color.WHITE);
            picItems.addView(iv);
            GlideApp.with(this).load(picUrlList.get(i)).into(iv);
            ImgInfo info = new ImgInfo();
            info.setUrl(picUrlList.get(i));
            mImgs.add(info);
            iv.setOnClickListener(v -> {
                MessageDetailActivitiy.this.startPre(v, picItems,mImgs);
            });

        }
    }

    private void startPre(View v, PicGridView picItems,ArrayList<ImgInfo> mImgs) {
        int currentIndex = picItems.indexOfChild(v);
        for (int j = 0; j < mImgs.size(); j++) {
            Rect rect = new Rect();
            ImageView imageView = (ImageView) picItems.getChildAt(j);
            if (imageView != null) {
                imageView.getGlobalVisibleRect(rect);
                mImgs.get(j).setBounds(rect);
            }
        }
        PrePictureActivity.start(this, mImgs, currentIndex);
    }


    @OnClick({R.id.bt_sure, R.id.bt_refuse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_sure:
                mPresenter.sure(eventId, 2);
                break;
            case R.id.bt_refuse:
                mPresenter.sure(eventId, 0);
                break;
            default:
        }
    }

    @Override
    protected MessageDetailPresenter initPresenter() {
        return new MessageDetailPresenterImpl();
    }

    @Override
    public void onSuccess(CommonDao dao) {

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showLoading() {

        LoadingView.showLoading(this, "", false);
    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }

    private void onClick(View v) {
        toHand(eventDao);
    }


}
