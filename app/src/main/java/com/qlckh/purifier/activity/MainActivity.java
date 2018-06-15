package com.qlckh.purifier.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.qlckh.purifier.R;
import com.qlckh.purifier.adapter.MainAdapter;
import com.qlckh.purifier.api.TestActivity;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.common.ACache;
import com.qlckh.purifier.common.GlideImageLoader;
import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.dao.BannerDao;
import com.qlckh.purifier.dao.HomeDao;
import com.qlckh.purifier.dao.MainDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.impl.MainPresenterImpl;
import com.qlckh.purifier.presenter.MainPresenter;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.usercase.BadgeEvent;
import com.qlckh.purifier.usercase.MessageEvent;
import com.qlckh.purifier.utils.JsonUtil;
import com.qlckh.purifier.utils.ScreenUtils;
import com.qlckh.purifier.utils.SpUtils;
import com.qlckh.purifier.view.MainView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @author Andy
 * @date 2018/5/15 16:47
 * Desc: 主界面
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainView {
    private static final String BADGE = "BADGE";
    private static final String MESSAGE = "MESSAGE";
    @BindView(R.id.braner)
    Banner braner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<BannerDao.ImageDao> imgs;
    private BadgeEvent mBadge;
    private MainAdapter adapter;
    private MessageEvent message;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        ibBack.setVisibility(View.GONE);
        setTitle("首页");
        mPresenter.getBanner();
        EventBus.getDefault().register(this);
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        List<MainDao> mDatas = mPresenter.getDatas();
        adapter = new MainAdapter(this, mDatas,mBadge,message);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                int spacing = ScreenUtils.px2dp(MainActivity.this, 15);
                int column = position % 3;
                outRect.left = column * spacing / 3;
                outRect.right = spacing - (column + 1) * spacing / 3;
                if (position >= 3) {
                    outRect.top = spacing;
                }
            }
        });

        adapter.setonItemClickListener((view, position) -> {
            MainDao mainDao = mDatas.get(position);
            int id = mainDao.getId();
            Intent intent = new Intent();
            switch (id) {
                case 0:
                    //签到
                    intent.setClass(MainActivity.this,AttendanceActivity.class);
                    break;
                    //评分
                case 1:
                    intent.setClass(MainActivity.this,MarkActivity.class);
                    break;
                    //事件处理
                case 2:
                    intent.setClass(MainActivity.this,TaskWorkingAcitivty.class);
                    break;
                    //桶报废
                case 3:
                    if (UserConfig.getType()==1){
                        intent.setClass(MainActivity.this,Scrap1Activity.class);
                    }else {
                        intent.setClass(MainActivity.this,Scrap2Activitiy.class);
                    }

                    break;
                    //意见反馈
                case 4:
                    intent.setClass(MainActivity.this,FeedBackActivity.class);
                    break;
                    //我的消息
                case 5:
                    intent.setClass(MainActivity.this,MessageActivity.class);
                    break;
                    //设置
                case 6:
                    intent.setClass(MainActivity.this,SettingActivity.class);
                    break;
                    //下发任务
                case 7:
                    intent.setClass(MainActivity.this,OrderActivity.class);
                    overridePendingTransition(0,0);
                  break;
                    //即时通讯
                case 8:
                    intent.setClass(MainActivity.this,SendActivity.class);
                    break;
                    //任务反馈
                case 9:
                    intent.setClass(MainActivity.this,TaskWorkingAcitivty.class);
                    break;
                case 11:
                    intent.setClass(MainActivity.this,PurifierManagerActivity.class);
                    break;
                default:


            }
            startActivity(intent);
        });
    }
    @Override
    protected void initSlidr() {
    }
    private void test() {
        /*
          caiid : 14
          caiuser : 王菊莲
          id : 774
          username : 陈灿军
          address : 6111
          sheng : 浙江省
          shi : 绍兴市
          xiang : 新昌县
          cun : 南苑
          cunid : 35
         */
        Intent intent = new Intent(this, TestActivity.class);
        HomeDao homedao = new HomeDao();
        homedao.setCaiid("11");
        homedao.setId("110");
        homedao.setCaiuser("保洁员");
        homedao.setCaiid("774");
        homedao.setAddress("6111");
        homedao.setSheng("浙江省");
        homedao.setShi("hangzhou");
        homedao.setXiang("jianggan");
        homedao.setCun("三村");
        homedao.setCunid("35");
        homedao.setUsername("Andy测试");
        intent.putExtra("HOME_DAO",homedao);
        startActivity(intent);

    }
    @Override
    public void initDate() {
        mPresenter.getBanner();
        String s = SpUtils.getStringParam(this, BADGE, "");
        this.mBadge = JsonUtil.json2Object2(s, BadgeEvent.class);
        String param = SpUtils.getStringParam(this, MESSAGE, "");
        this.message=JsonUtil.json2Object2(param,MessageEvent.class);
        initRecyclerView();

    }

    @Override
    public void showError(String msg) {
        showShort(msg);
        showErrorBranner();
    }

    private void showErrorBranner() {
        braner.setImageLoader(new GlideImageLoader());
        List<Integer> mList = new ArrayList<>();
        mList.add(R.drawable.error);
        mList.add(R.drawable.error);
        braner.setImages(mList);
        braner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        braner.isAutoPlay(true);
        braner.setIndicatorGravity(BannerConfig.RIGHT);
        braner.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshBadge(BadgeEvent badgeEvent){
        this.mBadge=badgeEvent;
        if (adapter!=null){
            adapter.setmBadge(badgeEvent);
            adapter.notifyDataSetChanged();
        }
        String json = JsonUtil.object2Json(badgeEvent);
        SpUtils.putParam(this,BADGE,json);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageBadge(MessageEvent messageEvent){
        this.message=messageEvent;
        if (adapter!=null){
            adapter.setMsgBadge(messageEvent);
            adapter.notifyDataSetChanged();
        }
        String json = JsonUtil.object2Json(messageEvent);
        SpUtils.putParam(this,MESSAGE,json);
    }
    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onSuccess(BannerDao dao) {
        XLog.e("---",dao);
        if (dao == null) {
            showError("轮播图获取失败");
            return;
        }
        if (dao.getStatus() == 1) {
            imgs = dao.getData();
            Collections.sort(imgs, (o1, o2) -> Integer.compare(o2.getFlag(), o1.getFlag()));
            mPresenter.showBanner(this, imgs, braner);

        } else {
            showError(dao.getMsg());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        braner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        braner.stopAutoPlay();
    }

    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (System.currentTimeMillis() - exitTime > 2000) {
            showShort("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
        return true;

    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }
}
