
package com.qlckh.purifier.base;

import android.os.Bundle;

import com.qlckh.purifier.view.HintDialog;
import com.qlckh.purifier.utils.OnDialogClickListener;
import com.qlckh.purifier.utils.SoundUtils;
import com.zltd.decoder.Constants;
import com.zltd.industry.ScannerManager;

/**
 * @author Andy
 * @date   2018/5/23 17:57
 * Desc:    BaseScanActivity.java
 */
public abstract class BaseScanActivity extends BaseActivity implements
        ScannerManager.IScannerStatusListener {

    protected ScannerManager mScannerManager;
    protected SoundUtils mSoundUtils;
    protected int decoderType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mScannerManager = ScannerManager.getInstance();
        decoderType = mScannerManager.getDecoderType();
        mScannerManager.setScannerSoundEnable(true);
        mSoundUtils = SoundUtils.getInstance();
        mSoundUtils.init(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        int res = mScannerManager.connectDecoderSRV();
        mScannerManager.addScannerStatusListener(this);
        mScannerManager.addScannerStatusListenerEx(new ScannerManager.IScannerStatusListenerEx() {
            @Override
            public void onScannerStatusChanage(int i) {

            }

            @Override
            public void onScannerResultChanageEx(String s, Bundle bundle) {

            }
        });
        if(decoderType == Constants.DECODER_ONED_SCAN){
            if (!mScannerManager.getScannerEnable()) {
                HintDialog.showHintDialog(this, "提醒", "请在设置中打开扫描头",
                        "知道了", null, false, new OnDialogClickListener() {
                            @Override
                            public void onSureClick() {
                                closeSelf();
                            }

                            @Override
                            public void onCancleClick() {

                            }
                        }) ;
            }

        }
    }
    @Override
    public void onPause() {
        mScannerManager.removeScannerStatusListener(this);
        mScannerManager.disconnectDecoderSRV();
        super.onPause();
    }

    protected void closeSelf() {
        this.finish();
    }
    @Override
    public void onScannerResultChanage(byte[] arg0) {
        String data = new String(arg0);
    }

    @Override
    public void onScannerStatusChanage(int arg0) {
        // TODO Auto-generated method stub

    }
}
