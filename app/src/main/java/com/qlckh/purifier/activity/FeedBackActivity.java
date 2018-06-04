package com.qlckh.purifier.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.common.GlideApp;
import com.qlckh.purifier.common.MyTask;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.HomeDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.utils.IntentUtil;
import com.qlckh.purifier.impl.FeedPresenterImpl;
import com.qlckh.purifier.presenter.CommView;
import com.qlckh.purifier.presenter.FeedPresenter;
import com.qlckh.purifier.preview.ImgInfo;
import com.qlckh.purifier.preview.PrePictureActivity;
import com.qlckh.purifier.utils.ImgUtil;
import com.qlckh.purifier.view.LoadingView;
import com.qlckh.purifier.view.PicGridView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/22 11:42
 * Desc:意见反馈
 */
public class FeedBackActivity extends BaseMvpActivity<FeedPresenter> implements CommView {
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.sumit)
    Button sumit;
    @BindView(R.id.picItems)
    PicGridView picModify;
    private String userAddress;
    private HomeDao homeDao;
    private String imgPath = "";
    private String photoPath = "";
    private boolean isDone = true;

    private ArrayList<ImgInfo> imgInfos = new ArrayList<>();
    private ArrayList<String> picFilePathList = new ArrayList<>();
    private static final int REQUEST_CODE_SELECT_PIC_FROM_CAMERA = 100;
    private static final int REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA = 120;
    @Override
    protected FeedPresenter initPresenter() {
        return new FeedPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feed;
    }

    @Override
    public void onSuccess(CommonDao dao) {

        finish();
    }

    @Override
    public void showLoading() {
        LoadingView.showLoading(this,"",false);
    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }

    @Override
    public void initView() {
        setTitle("意见反馈");
        goBack();

    }

    @Override
    public void initDate() {

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

    // TODO: 2018/5/31 接口加上图片功能 图片还没改
    @OnClick(R.id.sumit)
    public void onViewClicked() {
        if (checkData()) {
            if (isDone) {
                mPresenter.sumbit(etContent.getText().toString());
            } else {
                showShort("等待图片上传,请稍后重试");
            }
        }
    }

    private boolean checkData() {

        if (isEmpty(etContent.getText().toString().trim())) {
            showShort("请输入内容");
            return false;
        }
        if (imgPath.length() <= 0) {
            showShort("请至少上传一张拍照");
            return false;
        }
        return true;
    }
    /**
     * 设置照片
     */
    private void setPic() {
        picModify.setColumNum(4);
        picModify.removeAllViews();
        imgInfos.clear();
        for (int i = 0, picFilePathListSize = picFilePathList.size(); i < picFilePathListSize; i++) {
            String filePath = picFilePathList.get(i);
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(params);
            picModify.addView(iv);
            ImgInfo info = new ImgInfo();
            info.setUrl(filePath);
            imgInfos.add(info);
            GlideApp.with(this).load(filePath).into(iv);
            iv.setOnClickListener(this::startPre);
        }

        ImageView iv = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
        iv.setLayoutParams(params);
        if (picModify.getChildCount() < 2) {
            picModify.addView(iv);
        }
        iv.setImageResource(R.drawable.task_iv_default);
        iv.setBackgroundColor(Color.WHITE);
        iv.setOnClickListener(v -> {
            photoPath = ImgUtil.getPicSavaPath(FeedBackActivity.this) + "/" + System.currentTimeMillis() + ".jpg";
            if (Build.VERSION.SDK_INT > 23) {
                startActivityForResult(IntentUtil.getGrantPicFromCameraIntent(FeedBackActivity.this, photoPath),
                        REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA);
            } else {
                startActivityForResult(IntentUtil.getPicFromCameraIntent(photoPath),
                        REQUEST_CODE_SELECT_PIC_FROM_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_PIC_FROM_CAMERA:
                    picFilePathList.add(photoPath);
                    File compress = ImgUtil.compress(new File(photoPath), 50, 2100000);
                    new Handler().post(this::setPic);
                    doTask(compress);
                    break;
                case REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA:
                    picFilePathList.add(photoPath);
                    File compress1 = ImgUtil.compress(new File(photoPath), 50, 2100000);
                    new Handler().post(this::setPic);
                    doTask(compress1);
                    break;
                default:
            }
        }
    }

    private void doTask(File compress) {

        MyTask task = new MyTask(this, imgPath, new Handler(msg -> {

            if (msg.what == MyTask.COMPRESS_SUCCESS) {
                isDone = true;
                imgPath = (String) msg.obj;
            } else {
                isDone = false;
            }
            return false;
        }));
        task.execute(compress);

    }

    private void startPre(View v) {
        int currentIndex = picModify.indexOfChild(v);
        for (int j = 0; j < imgInfos.size(); j++) {
            Rect rect = new Rect();
            ImageView imageView = (ImageView) picModify.getChildAt(j);
            if (imageView != null) {
                imageView.getGlobalVisibleRect(rect);
                imgInfos.get(j).setBounds(rect);
            }
        }
        PrePictureActivity.start(this, imgInfos, currentIndex);
    }
}
