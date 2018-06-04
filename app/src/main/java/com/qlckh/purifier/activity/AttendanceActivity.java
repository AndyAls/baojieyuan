package com.qlckh.purifier.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.qlckh.purifier.App;
import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.common.GlideImageLoader;
import com.qlckh.purifier.common.LocationService;
import com.qlckh.purifier.common.ThreadPool;
import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.dao.BannerDao;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.impl.AttendancePresenterImpl;
import com.qlckh.purifier.presenter.AttendancePresenter;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.utils.SpUtils;
import com.qlckh.purifier.utils.TimeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/18 10:57
 * Desc: 签到界面
 */
public class AttendanceActivity extends BaseMvpActivity<AttendancePresenter> implements AttendancePresenter.AttendanceView{
    private static final String TAG = "AttendanceActivity";
    private static final String WORK_TIME = "WORK_TIME";
    private static final String OFFLINE_TIME = "OFFLINE_TIME";
    private static final String WORK_LOCATION = "WORK_LOCATION";
    private static final String OFFLINE_LOCATION = "OFFLINE_LOCATION";
    private static final String WUSERID = "WUSERID";
    private static final String OUSERID = "OUSERID";
    @BindView(R.id.braner)
    Banner braner;
    @BindView(R.id.tv_working_location)
    TextView tvWorkingLocation;
    @BindView(R.id.tv_working_time)
    TextView tvWorkingTime;
    @BindView(R.id.tv_working_sign)
    TextView tvWorkingSign;
    @BindView(R.id.tv_offline_location)
    TextView tvOfflineLocation;
    @BindView(R.id.tv_offline_time)
    TextView tvOfflineTime;
    @BindView(R.id.tv_offline_sign)
    TextView tvOfflineSign;
    private List<BannerDao.ImageDao> imgs;
    private static int type=-1;
    private SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
    private LocationService locationService;
    private boolean worked=false;

    @Override
    protected AttendancePresenter initPresenter() {
        return new AttendancePresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_attendance;
    }

    @Override
    public void initView() {

        String getWorkTime = SpUtils.getStringParam(this, WORK_TIME, "");
        String getOffLineTime = SpUtils.getStringParam(this, OFFLINE_TIME, "");
        String wUserid = SpUtils.getStringParam(this, WUSERID, "");
        String oUserid = SpUtils.getStringParam(this, OUSERID, "");
        if (TimeUtils.isToday(getWorkTime)&&wUserid.equals(UserConfig.getUserName())){
            tvWorkingSign.setEnabled(false);
            tvWorkingSign.setText("已签到");
            tvWorkingTime.setText(getWorkTime);
            tvWorkingLocation.setText(SpUtils.getStringParam(this,WORK_LOCATION,""));
        }else {
            tvWorkingSign.setEnabled(true);
            ThreadPool.getInstance().executeTimer(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(0);
                    XLog.e(TAG,"executeTimer");
                }
            },0,1000);

        }
        tvOfflineSign.setEnabled(false);
        if (TimeUtils.isToday(getOffLineTime)&&oUserid.equals(UserConfig.getUserName())){
            tvOfflineSign.setEnabled(false);
            tvOfflineTime.setText(getOffLineTime);
            tvOfflineSign.setText("已签到");
            tvOfflineLocation.setText(SpUtils.getStringParam(AttendanceActivity.this,OFFLINE_LOCATION,""));
        }else {
            tvOfflineSign.setEnabled(true);
            if (TimeUtils.isToday(getWorkTime)){
                ThreadPool.getInstance().executeTimer(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(1);
                        XLog.e(TAG,"executeTimer");
                    }
                },0,1000);
            }else {
                tvOfflineTime.setText("签到时间");
            }


        }
        setTitle("签到");
        goBack();

    }

    @Override
    public void initDate() {
        mPresenter.getBanner();
    }

    private Handler mHandler=new Handler(msg -> {
        if (msg.what==0){
            if (tvWorkingTime!=null) {
                tvWorkingTime.setText(format.format(System.currentTimeMillis()));
            }
        }else if (msg.what==1){
            if (tvOfflineTime!=null) {
                tvOfflineTime.setText(format.format(System.currentTimeMillis()));
            }
        }
        return false;
    });
    @Override
    public void showError(String msg) {
        showShort(msg);
        showErrorBranner();
    }

    @Override
    public void release() {

        ThreadPool.getInstance().release();
        if (mHandler.hasMessages(0)) {
            mHandler.removeMessages(0);
        }
        if (mHandler.hasMessages(1)){
            mHandler.removeMessages(1);
        }
        RxHttpUtils.cancelAllRequest();

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



    @OnClick({R.id.tv_working_sign, R.id.tv_offline_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_working_sign:
                locationService.start();
                type=0;
                String workTime = this.format.format(System.currentTimeMillis());
                tvWorkingTime.setText(workTime);
                SpUtils.putParam(AttendanceActivity.this,WORK_TIME, workTime);
                SpUtils.putParam(AttendanceActivity.this,WUSERID,UserConfig.getUserName());
                ThreadPool.getInstance().release();
                mHandler.removeMessages(0);

                break;
            case R.id.tv_offline_sign:
                if (!worked){
                    showShort("必须上班签到后,才能下班签到");
                    return;
                }
                locationService.start();
                type=1;
                String offLineTime = this.format.format(System.currentTimeMillis());
                tvOfflineTime.setText(offLineTime);
                SpUtils.putParam(AttendanceActivity.this,OFFLINE_TIME, offLineTime);
                SpUtils.putParam(AttendanceActivity.this,OUSERID,UserConfig.getUserName());
                ThreadPool.getInstance().release();
                mHandler.removeMessages(1);
                break;
                default:
        }
    }

    @Override
    public void onSuccess(BannerDao dao) {
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
        locationService = ((App) getApplication()).locationService;
        locationService.registerListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        braner.stopAutoPlay();
        locationService.unregisterListener(listener);
    }

    private BDAbstractLocationListener listener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            String address="无法定位到位置";
            if (bdLocation!=null){
                String addrStr = bdLocation.getAddrStr();
                String locationDescribe = bdLocation.getLocationDescribe();
                if (locationDescribe!=null){
                    address= addrStr.concat(locationDescribe.substring(1,locationDescribe.length()-2));
                }else {
                    address=addrStr;
                }
            }

            if (type==0){
                tvWorkingLocation.setText(address);
                SpUtils.putParam(AttendanceActivity.this,WORK_LOCATION,address);
            }
            if (type==1){
                tvOfflineLocation.setText(address);
                SpUtils.putParam(AttendanceActivity.this,OFFLINE_LOCATION,address);
            }
            mPresenter.sign(UserConfig.getUserid(),address,type);
            locationService.stop();
        }
    };

    @Override
    public void onSiginSuccess(CommonDao dao,String msg) {
        showShort(msg);
        if (msg.contains("上班")){
            tvWorkingSign.setEnabled(false);
            tvWorkingSign.setText("已签到");
            worked=true;
        }
        if (msg.contains("下班")){
            tvOfflineSign.setEnabled(false);
            tvOfflineSign.setText("已签到");
        }
    }
}
