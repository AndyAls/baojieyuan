package com.qlckh.purifier.api;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.qlckh.purifier.App;
import com.qlckh.purifier.R;
import com.qlckh.purifier.activity.MarkActivity;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.common.GlideApp;
import com.qlckh.purifier.common.LocationService;
import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.dao.Comm2Dao;
import com.qlckh.purifier.dao.HomeDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.utils.IntentUtil;
import com.qlckh.purifier.impl.SanitationPresenterImpl;
import com.qlckh.purifier.presenter.CompositeView;
import com.qlckh.purifier.presenter.SanitationPresenter;
import com.qlckh.purifier.preview.ImgInfo;
import com.qlckh.purifier.preview.PrePictureActivity;
import com.qlckh.purifier.utils.Base64Util;
import com.qlckh.purifier.utils.ImgUtil;
import com.qlckh.purifier.view.LoadingView;
import com.qlckh.purifier.view.PicGridView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Andy
 * @date 2018/5/20 1:12
 * Desc:
 */
public class TestActivity extends BaseMvpActivity<SanitationPresenter> implements CompositeView {
    private static final int REQUEST_CODE_SELECT_PIC_FROM_CAMERA = 100;
    private static final int REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA = 120;
    private static final String TAG = "test";
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rb_put_1)
    RadioButton rbPut1;
    @BindView(R.id.rb_put_2)
    RadioButton rbPut2;
    @BindView(R.id.rb_put_3)
    RadioButton rbPut3;
    @BindView(R.id.rg_put)
    RadioGroup rgPut;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.picItems)
    PicGridView picModify;
    private LocationService locationService;
    private List<String> picFilePathList = new ArrayList<>();
    private String photoPath;
    private int totalScore;
    private int envScore = 2;
    private boolean isDone = true;
    private ArrayList<ImgInfo> imgInfos=new ArrayList<>();

    @Override
    protected SanitationPresenter initPresenter() {
        return new SanitationPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sanitation;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void showLoading() {
        LoadingView.showLoading(this, "", false);
    }

    @Override
    public void dissmissLoading() {
        LoadingView.cancelLoading();
    }

    @Override
    public void onSuccess(Comm2Dao dao) {
        finish();
    }

    @Override
    public void onAddScanedSuccess() {

    }

    @Override
    public void initView() {
        goBack();
        setTitle("环境卫生");
        totalScore = envScore;
        tvScore.setText(String.format("%d", totalScore));
        addListener();
    }

    private void addListener() {

        rgPut.setOnCheckedChangeListener((group, checkedId) -> {

            View viewById = rgPut.findViewById(checkedId);
            if (!viewById.isPressed()) {
                return;
            }

            switch (checkedId) {
                case R.id.rb_put_1:
                    rbPut1.setChecked(true);
                    rbPut3.setChecked(false);
                    envScore = 2;
                    break;
                case R.id.rb_put_3:
                    rbPut1.setChecked(false);
                    rbPut3.setChecked(true);
                    envScore = 0;
                    break;
                default:
                    break;
            }
            updaScore();

        });
    }

    private void updaScore() {
        totalScore = envScore;
        tvScore.setText(MessageFormat.format("{0}", totalScore));
    }

    @Override
    public void initDate() {
        HomeDao homeDao = getIntent().getParcelableExtra(MarkActivity.HOME_DAO);
        String username = homeDao.getUsername();
        tvHome.setText(username);
        String userAddress = MessageFormat.format("{0}{1}{2}{3}{4}",
                homeDao.getSheng(), homeDao.getShi(), homeDao.getXiang(), homeDao.getCun(), homeDao.getAddress());
        tvAddress.setText(userAddress);
        ibRight.setVisibility(View.GONE);
        Button btSumbit = findViewById(R.id.bt_submit);
        btSumbit.setOnClickListener(v -> {
            if (isDone) {
                if (imgPath.length()>0){
                    mPresenter.sanitationSubmit(homeDao, envScore, address, "",
                            imgPath.substring(0,imgPath.length()-1));
                }

                XLog.e(TAG,builder.toString());
            } else {

                showShort("等待图片上传,请稍后重试");
            }

        });
        setPic();
    }

    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService = ((App) getApplication()).locationService;
        locationService.registerListener(listener);
        locationService.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationService.unregisterListener(listener);
    }

    private String address;
    private BDAbstractLocationListener listener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {


            if (bdLocation != null) {
                String addrStr = bdLocation.getAddrStr();
                String locationDescribe = bdLocation.getLocationDescribe();
                if (locationDescribe != null) {
                    address = addrStr.concat(locationDescribe.substring(1, locationDescribe.length() - 2));
                } else {
                    address = addrStr;
                }
            }
            locationService.stop();
        }
    };

    /**
     * 设置照片
     */
    private void setPic() {
        picModify.setColumNum(4);
        picModify.removeAllViews();
        imgInfos.clear();
        for (int i = 0; i < picFilePathList.size(); i++) {
            String filePath = picFilePathList.get(i);
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(params);
            picModify.addView(iv);
            GlideApp.with(this).load(filePath).into(iv);
            Rect rect = new Rect();
            ImgInfo info = new ImgInfo();
            iv.getGlobalVisibleRect(rect);
            info.setUrl(filePath);
            info.setBounds(rect);
            imgInfos.add(info);
            iv.setOnClickListener(v -> {

                PrePictureActivity.start(this,imgInfos,picModify.indexOfChild(v));
            });
        }

        ImageView iv = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
        iv.setLayoutParams(params);
        if (picModify.getChildCount() < 4) {
            picModify.addView(iv);
        }
        iv.setImageResource(R.drawable.task_iv_default);
        iv.setBackgroundColor(Color.WHITE);
        iv.setOnClickListener(v -> {
            photoPath = getPicSavaPath() + "/" + System.currentTimeMillis() + ".jpg";
            if (Build.VERSION.SDK_INT > 23) {
                startActivityForResult(IntentUtil.getGrantPicFromCameraIntent(TestActivity.this, photoPath),
                        REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA);
            } else {
                startActivityForResult(IntentUtil.getPicFromCameraIntent(photoPath),
                        REQUEST_CODE_SELECT_PIC_FROM_CAMERA);
            }
        });
    }

    public String getPicSavaPath() {
        File sdcardDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdcardDir = Environment.getExternalStorageDirectory();
        } else {
            sdcardDir = getCacheDir();
        }

        String path = sdcardDir.getPath() + File.separator + getPackageName();
        File picDir = new File(path);
        if (!picDir.exists()) {
            picDir.mkdirs();
        }
        return path;
    }

    private StringBuffer builder = new StringBuffer();
    private String imgPath= "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_PIC_FROM_CAMERA:
                    picFilePathList.add(photoPath);
                    File compress = ImgUtil.compress(new File(photoPath), 50, 2100000);
                    new Handler().post(this::setPic);
                    XLog.e(TAG,"normal",ImgUtil.getFileSizes(this,new File(photoPath)));
                    XLog.e(TAG,"compress",ImgUtil.getFileSizes(this,compress));
                    doTask(compress);
//                    ThreadPool.getInstance().execute("thread", () ->
//                            builder.append(Base64Util.ioEncode(compress)).append("分"));
                    break;
                case REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA:
                    picFilePathList.add(photoPath);
                    File compress1 = ImgUtil.compress(new File(photoPath), 50, 2100000);
                    new Handler().post(this::setPic);
                    XLog.e(TAG,"normal",Formatter.formatFileSize(this,ImgUtil.getFileSizes(this,new File(photoPath))));
                    XLog.e(TAG,"compress", Formatter.formatFileSize(this,ImgUtil.getFileSizes(this,compress1)));
                    doTask(compress1);
//                    ThreadPool.getInstance().execute("thread", () ->
//                            builder.append(Base64Util.ioEncode(compress1)).append("分"));
                    break;
                default:
            }
        }
    }

    private void doTask(File compress) {

        MyTask task = new MyTask(this);
        task.execute(compress);
    }

    private static class MyTask extends AsyncTask<File, Void, String> {

        private final WeakReference<Activity> activityWeakReference;

        private MyTask(Activity activity) {
            activityWeakReference = new WeakReference<>(activity);

        }

        @Override
        protected String doInBackground(File... files) {
            String ioEncode = Base64Util.ioEncode(files[0]);
            String s = "data:image/png;base64," .concat(ioEncode) ;
            XLog.e("+++","doInBackground",ioEncode);
            String source = s.concat("分");
            XLog.e("+++","source",source);
            return source;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TestActivity activity = (TestActivity) activityWeakReference.get();
            if (activity==null||activity.isFinishing()){
                return;
            }
            activity.imgPath+=s;
            XLog.e("+++","s",s);
            activity.isDone = true;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            TestActivity activity = (TestActivity) activityWeakReference.get();
            if (activity==null||activity.isFinishing()){
                return;
            }
            activity.isDone = true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TestActivity activity = (TestActivity) activityWeakReference.get();
            if (activity==null||activity.isFinishing()){
                return;
            }
            activity.isDone = false;
        }
    }
}
