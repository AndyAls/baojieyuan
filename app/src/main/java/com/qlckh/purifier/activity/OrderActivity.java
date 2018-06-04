package com.qlckh.purifier.activity;

import android.animation.PropertyValuesHolder;
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
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.common.GlideApp;
import com.qlckh.purifier.common.MyTask;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.CuntryDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.utils.IntentUtil;
import com.qlckh.purifier.impl.OrderPresenterImpl;
import com.qlckh.purifier.presenter.OrderPresenter;
import com.qlckh.purifier.preview.ImgInfo;
import com.qlckh.purifier.preview.PrePictureActivity;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.utils.ImgUtil;
import com.qlckh.purifier.view.LoadingView;
import com.qlckh.purifier.view.PicGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/22 18:40
 * Desc:
 */
public class OrderActivity extends BaseMvpActivity<OrderPresenter> implements OrderPresenter.OrderView {
    public static final String CUNTRY_LIST = "CUNTRY_LIST";
    private static final int SELECT_BAOJIE_REQUEST_CODE = 1000;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.picItems)
    PicGridView picModify;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.sumit)
    Button sumit;
    private ArrayList<ImgInfo> imgInfos = new ArrayList<>();
    private ArrayList<String> picFilePathList = new ArrayList<>();
    private boolean isDone = true;
    private String imgPath = "";
    private String photoPath = "";
    private static final int REQUEST_CODE_SELECT_PIC_FROM_CAMERA = 100;
    private static final int REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA = 120;
    private ArrayList<CuntryDao.Cuntry> cuntryList = new ArrayList<>();
    private int baojieId = -1;
    private int guanId = -1;


    @Override
    protected OrderPresenter initPresenter() {
        return new OrderPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        setTitle("下发任务");
        goBack();

    }

    @Override
    public void initDate() {

        mPresenter.getCuntryList();
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

    @OnClick({R.id.ll_select, R.id.sumit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select:
                Intent intent = new Intent(this, SelectActivity.class);
                intent.putParcelableArrayListExtra(CUNTRY_LIST, cuntryList);
                startActivityForResult(intent, SELECT_BAOJIE_REQUEST_CODE);
                break;
            case R.id.sumit:

                if (checkData()) {
                    if (isDone) {
                        mPresenter.orderSumbit(etTitle.getText().toString().trim(), etContent.getText().toString().trim(),
                                imgPath, guanId + "", baojieId + "");

                    } else {

                        showShort("等待图片上传,请稍后重试");
                    }
                }
                break;
            default:
        }
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
            photoPath = ImgUtil.getPicSavaPath(OrderActivity.this) + "/" + System.currentTimeMillis() + ".jpg";
            if (Build.VERSION.SDK_INT > 23) {
                startActivityForResult(IntentUtil.getGrantPicFromCameraIntent(OrderActivity.this, photoPath),
                        REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA);
            } else {
                startActivityForResult(IntentUtil.getPicFromCameraIntent(photoPath),
                        REQUEST_CODE_SELECT_PIC_FROM_CAMERA);
            }
        });
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


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
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

                case SELECT_BAOJIE_REQUEST_CODE:
                    baojieId = data.getIntExtra(SelectActivity.BAOJIE_ID, -1);
                    guanId = data.getIntExtra(SelectActivity.GUAN_ID, -1);
                    tvSelect.setVisibility(View.VISIBLE);
                    tvSelect.setText(String.format("%s-%s", data.getStringExtra(SelectActivity.GUAN_NAME),
                            data.getStringExtra(SelectActivity.BAOJIE_NAME)));
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

    @Override
    public void onCuntrySuccess(CuntryDao dao) {

        cuntryList = (ArrayList<CuntryDao.Cuntry>) dao.getName();
    }

    private boolean checkData() {

        if (isEmpty(etTitle.getText().toString().trim())) {
            showShort("请输入标题");
            return false;
        }
        if (isEmpty(etContent.getText().toString().trim())) {
            showShort("请输入内容");
            return false;
        }
        if (guanId == -1 || baojieId == -1) {
            showShort("请选择执行任务的采集员");
            return false;
        }
        if (imgPath.length()<=0){
            showShort("请至少上传一张拍照");
            return false;
        }
        return true;
    }


    @Override
    public void onSuccess(CommonDao dao) {
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
}
