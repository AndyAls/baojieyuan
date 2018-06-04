package com.qlckh.purifier.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.URIParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.qlckh.purifier.R;
import com.qlckh.purifier.dao.HomeDao;
import com.qlckh.purifier.http.utils.ToastUtils;
import com.qlckh.purifier.utils.JsonUtil;

public class ScanActivity extends Activity {
    private static final String TAG = "ScanActivity";
    private ScannerView scannerView;
    private ImageView ivDeng;
    private boolean isOpen=false;

    public static final void start(Activity activity) {
        Intent intent = new Intent(activity, ScanActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_capture);
        scannerView = (ScannerView) findViewById(R.id.scanner_view);
        ivDeng = findViewById(R.id.iv_deng);
        scannerView.setOnScannerCompletionListener(this::resolve);
        config();
    }

    private void config() {
        ScannerOptions.Builder builder = new ScannerOptions.Builder();
        builder.setFrameCornerColor(Color.WHITE);
        builder.setLaserStyle(ScannerOptions.LaserStyle.RES_LINE, R.drawable.scan_line);
        builder.setTipText("对准二维码到框内即可自动扫描");
        scannerView.setScannerOptions(builder.build());

        ivDeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen=!isOpen;
                scannerView.toggleLight(isOpen);
                if (isOpen){
                    ivDeng.setImageResource(R.drawable.deng_selected);
                }else {
                    ivDeng.setImageResource(R.drawable.deng);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        scannerView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        scannerView.onPause();
        super.onPause();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        isOpen=false;
    }

    public void resolve(Result result, ParsedResult parsedResult, Bitmap bitmap) {
        if (parsedResult == null) {
            return;
        }
        String resultString = "";
        ParsedResultType type = parsedResult.getType();
        switch (type) {
            case ADDRESSBOOK:
            case URI:
                URIParsedResult uriParsedResult = (URIParsedResult) parsedResult;
                resultString = uriParsedResult.getURI();
                Intent intent = new Intent(ScanActivity.this,WebViewActivity.class);
                intent.putExtra("url",resultString);
                startActivity(intent);

                break;
            case TEXT:
                if (result != null) {
                    resultString = result.getText();
                    Intent it=new Intent(ScanActivity.this,ScrapActivity.class);
                    HomeDao homeDao = JsonUtil.json2Object2(resultString, HomeDao.class);
                    it.putExtra("HOME_DAO",homeDao);
                    startActivity(it);
                }else {
                    ToastUtils.showToast("扫描无内容,请重试");
                }
                break;
            default:
//                if (result != null) {
//                    resultString = result.getText();
//                    Intent it=new Intent(ScanActivity.this,ScrapActivity.class);
//                    HomeDao homeDao = JsonUtil.json2Object2(resultString, HomeDao.class);
//                    it.putExtra("HOME_DAO",homeDao);
//                    startActivity(it);
//                }else {
//                    ToastUtils.showToast("扫描无结果");
//                }
                ToastUtils.showToast("扫描无结果,请重试");
                break;
        }
        ScanActivity.this.finish();
    }
}
