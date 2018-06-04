package com.qlckh.purifier.common;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.qlckh.purifier.activity.SanitationActivity;
import com.qlckh.purifier.dao.CompresDao;
import com.qlckh.purifier.utils.Base64Util;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * @author Andy
 * @date 2018/5/26 11:58
 * Desc: 图片异步压缩
 */
public class MyTask extends AsyncTask<File, Void, String> {
    public static final int COMPRESS_SUCCESS = 1000;
    private static final int COMPRESS_FAILURE = 1111;
    private final Handler mHander;
    private String imgPaths;
    private final CompresDao dao;


    public MyTask(Activity activity,String paths,Handler handler) {

        this.mHander=handler;
        dao = new CompresDao();
        this.imgPaths=paths;
    }

    @Override
    protected String doInBackground(File... files) {
        String ioEncode = Base64Util.ioEncode(files[0]);
        String s = "data:image/png;base64,".concat(ioEncode);
        XLog.e("+++", "doInBackground", ioEncode);
        String source = s.concat("分");
        XLog.e("+++", "source", source);
        return source;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        imgPaths += s;
        dao.setImgPaths(imgPaths);
        mHander.obtainMessage(COMPRESS_SUCCESS,imgPaths).sendToTarget();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mHander.sendEmptyMessage(COMPRESS_SUCCESS);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mHander.sendEmptyMessage(COMPRESS_FAILURE);
    }
}
