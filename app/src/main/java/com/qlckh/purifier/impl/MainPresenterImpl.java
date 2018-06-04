package com.qlckh.purifier.impl;

import android.content.Context;
import android.content.Intent;

import com.qlckh.purifier.R;
import com.qlckh.purifier.activity.WebViewActivity;
import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.common.GlideImageLoader;
import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.dao.BannerDao;
import com.qlckh.purifier.dao.MainDao;
import com.qlckh.purifier.http.observer.CommonObserver;
import com.qlckh.purifier.presenter.MainPresenter;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.usercase.MainUserCase;
import com.qlckh.purifier.view.MainView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author Andy
 * @date 2018/5/17 11:18
 * Desc:
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mView;
    private MainUserCase mainUserCase;

    @Override
    public void getBanner() {
        XLog.e("--");
        if (mainUserCase == null) {
            mainUserCase = new MainUserCase();
        }
        Observable<BannerDao> bannerImg = mainUserCase.getBannerImg();
        bannerImg.subscribe(new CommonObserver<BannerDao>() {
            @Override
            protected void onError(String errorMsg) {

                mView.showError(errorMsg);
            }

            @Override
            protected void onSuccess(BannerDao bannerDao) {
                try {
                    mView.onSuccess(bannerDao);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    @Override
    public List<MainDao> getDatas() {
        List<MainDao> daos = new ArrayList<>();
        int type = UserConfig.getType();
        String[] titles=null;
        int[] icons =null;
        int[]ids=null;
        switch (type) {
            case 1:
                titles = new String[]{
                        "考勤打卡", "评分", "事件处理","桶报废","意见反馈","我的消息","设置"
                };
                icons = new int[]{
                        R.drawable.attendance_icon,
                        R.drawable.mark_icon,
                        R.drawable.event_icon,
                        R.drawable.scrap_icon,
                        R.drawable.feedback_icon,
                        R.drawable.message_icon,
                        R.drawable.setting_icon,
                };
                ids=new int[]{0,1,2,3,4,5,6};
                break;
            case 2:

                titles = new String[]{
                        "下发任务","事件处理","我的消息","桶报废","意见反馈","设置"
                };
                icons = new int[]{
                        R.drawable.order_icon,
                        R.drawable.event_icon,
                        R.drawable.message_icon,
                        R.drawable.scrap_icon,
                        R.drawable.feedback_icon,
                        R.drawable.setting_icon,
                };
                ids=new int[]{7,2,5,3,4,6};
                break;
            case 3:
                titles = new String[]{
                        "下发任务","即时通讯","任务反馈","桶报废","设置"
                };
                icons = new int[]{
                        R.drawable.order_icon,
                        R.drawable.send_icon,
                        R.drawable.feedback_icon,
                        R.drawable.scrap_icon,
                        R.drawable.setting_icon,
                };
                ids=new int[]{7,8,9,3,6};
                break;
            default:
        }

        //,"事件处理","桶报废","意见反馈","我的消息",

        assert titles != null;
        for (int i = 0; i < titles.length; i++) {
            MainDao mainDao = new MainDao();
            mainDao.setTitle(titles[i]);
            mainDao.setIcon(icons[i]);
            mainDao.setId(ids[i]);
            daos.add(mainDao);
        }
        return daos;
    }

    @Override
    public void showBanner(Context context, List<BannerDao.ImageDao> imgs, Banner braner) {


        List<String> imgUrl = new ArrayList<>();
        List<String> title = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            imgUrl.add(ApiService.IMG_URL + imgs.get(i).getImgpath());
            title.add(imgs.get(i).getTitle());
        }
        braner.setImageLoader(new GlideImageLoader());
        braner.setImages(imgUrl);
        braner.setBannerTitles(title);
        braner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        braner.isAutoPlay(true);
        braner.setDelayTime(2500);
        braner.setIndicatorGravity(BannerConfig.RIGHT);
        braner.start();
        braner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", imgs.get(position).getLinks());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void register(MainView view) {
        mView = view;
    }

    @Override
    public void unregister(MainView view) {
        if (mView != null) {
            mView = null;
        }
    }
}
