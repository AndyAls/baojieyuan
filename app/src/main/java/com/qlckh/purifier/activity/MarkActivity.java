package com.qlckh.purifier.activity;

import android.content.Intent;
import android.widget.Button;

import com.qlckh.purifier.R;
import com.qlckh.purifier.base.BaseScanActivity;
import com.qlckh.purifier.common.XLog;
import com.qlckh.purifier.dao.HomeDao;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.utils.JsonUtil;
import com.qlckh.purifier.view.HintDialog;
import com.zltd.industry.ScannerManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/18 10:56
 * Desc: 评分扫描
 */
public class MarkActivity extends BaseScanActivity {

    private static final String TAG = "Scrap1Activity";
    public static final String HOME_DAO = "HOME_DAO";
    @BindView(R.id.bt_scan)
    Button btScan;
    private int scanMode;
    private boolean inContinuousShoot = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_mark;
    }

    @Override
    public void initView() {

        setTitle("扫描");
        goBack();
        scanMode = mScannerManager.getScanMode();
        if (scanMode == ScannerManager.SCAN_KEY_HOLD_MODE) {
            btScan.setEnabled(false);
            HintDialog.showHintDialog(this, "提示", "请在扫描设置中设置单扫或连扫模式", "知道了",
                    null, true, null);
        } else {
            btScan.setEnabled(true);
        }

    }

    @Override
    public void initDate() {
        XLog.e(TAG, Thread.currentThread().getName());
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }

    @Override
    public void onScannerResultChanage(byte[] arg0) {


        String json = new String(arg0);
        mSoundUtils.success();
        if (scanMode == ScannerManager.SCAN_CONTINUOUS_MODE) {
            mScannerManager.stopContinuousScan();
        }
        if (!json.contains("Decode is interruptted or timeout")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showDialog(json);
                }
            });

        } else {
            runOnUiThread(() -> showShort("没有扫描结果,请对准二维码"));
        }
        XLog.e(TAG, json);

    }

    private void showDialog(String json) {

        HintDialog.showListDialog(this, new String[]{"综合评分", "环境卫生"}
                , (position, datas, view) -> {

                    switch (position) {
                        case 0:
                            toComposite(json);
                            break;
                        case 1:
                            toSanitation(json);
                            break;
                        default:
                    }
                });

    }

    private void toSanitation(String json) {
        HomeDao homeDao = JsonUtil.json2Object2(json, HomeDao.class);
//        if (!homeDao.getCaiid().equals(UserConfig.getUserid()+"")){
//            showShort("您没有权限评分");
//            return;
//        }
        Intent intent = new Intent(this,SanitationActivity.class);
        intent.putExtra(HOME_DAO,homeDao);
        overridePendingTransition(0,0);
        startActivity(intent);

    }

    private void toComposite(String json) {

        HomeDao homeDao = JsonUtil.json2Object2(json, HomeDao.class);
//        if (!homeDao.getCaiid().equals(UserConfig.getUserid()+"")){
//            showShort("您没有权限评分");
//            return;
//        }
        Intent intent = new Intent(this,CompositeActivity.class);
        intent.putExtra(HOME_DAO,homeDao);
        overridePendingTransition(0,0);
        startActivity(intent);

    }


    @OnClick(R.id.bt_scan)
    public void onViewClicked() {
        switch (scanMode) {
            case ScannerManager.SCAN_CONTINUOUS_MODE:
                if (!HintDialog.isReplayClick()) {
                    if (!inContinuousShoot) {
                        inContinuousShoot = true;
                        mScannerManager.startContinuousScan();
                    } else {
                        inContinuousShoot = false;
                        mScannerManager.stopContinuousScan();
                    }
                }

                break;
            case ScannerManager.SCAN_SINGLE_MODE:
                if (!HintDialog.isReplayClick()) {
                    mScannerManager.singleScan();
                }

                break;
            default:
        }
    }
}
