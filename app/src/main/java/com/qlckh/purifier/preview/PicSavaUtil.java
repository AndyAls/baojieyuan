package com.qlckh.purifier.preview;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author 照片保存工具类
 */

public class PicSavaUtil {

    private static final String TAG = "PicSavaUtil";
    private static OnSavaSuccessListener mListener;

    /**
     * 检测文件是否可用
     */
    public static boolean checkFile(File f) {
        return f != null && f.exists() && f.canRead() && (f.isDirectory() || (f.isFile() && f.length() > 0));
    }


    public static void save(Context context,String filePath) {
        String path=getSavaPath(context);
        File targetFile = new File(path + "/" + System.currentTimeMillis() + ".jpg");
        File file = new File(filePath);
        if (checkFile(file)) {
            try {
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(targetFile);
                byte[] buffer = new byte[1024 * 1024];
                int n = 0;
                while (-1 != (n = fis.read(buffer))) {
                    fos.write(buffer, 0, n);
                }
                fis.close();
                fos.close();
                addMediaStore(context,targetFile,path);
                if (mListener!=null){
                    mListener.onSavaSuccess();
                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage() );
                if (mListener!=null){
                    mListener.onSavaFailed();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage() );
                if (mListener!=null){
                    mListener.onSavaFailed();
                }
            }
        }else {

            DownLoadTask task = new DownLoadTask(context);
            task.execute(filePath);
        }
    }

    private static String getSavaPath(Context context) {
        String rootPath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            rootPath=Environment.getExternalStorageDirectory().getPath();
        }else{
            rootPath= context.getFilesDir().getPath();
        }
        String path = rootPath + File.separator + context.getPackageName()+File.separator+"IM";
        File picDir = new File(path);
        if (!picDir.exists()) {
            picDir.mkdirs();
        }
        return path;
    }

    //遍历相册
    public static void scanleAlbum(Context context,String photoPath) {
        if (photoPath == null) {
            return;
        }
        File f = new File(photoPath);

        if (Build.VERSION.SDK_INT >= 19) {

            MediaScannerConnection.scanFile(context, new String[]{photoPath}, null, null);

        } else {
            if (f.isFile()) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(f));
                context.sendBroadcast(intent);
            }
        }
    }
    /**
     * @param context
     * @param targetFile 要保存的照片文件
     * @param path  要保存的照片的路径地址
     */
    private static void addMediaStore(Context context, File targetFile, String path) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues newValues = new ContentValues(5);
        newValues.put(MediaStore.Images.Media.DISPLAY_NAME, targetFile.getName());
        newValues.put(MediaStore.Images.Media.DATA, targetFile.getPath());
        newValues.put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000);
        newValues.put(MediaStore.Images.Media.SIZE, targetFile.length());
        newValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, newValues);
        scanleAlbum(context,path);
    }

    private static class DownLoadTask extends AsyncTask<String,Void,Bitmap>{

        @SuppressLint("StaticFieldLeak")
        private Context mContext;

        private DownLoadTask(Context context) {
            this.mContext=context;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return returnBitMap(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            saveImageToPhotos(bitmap,mContext);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mContext=null;
        }
    }

    /**
     * 保存图片到本地相册
     */
    private static void saveImageToPhotos(Bitmap bmp, Context context) {
        // 首先保存图片
        String savaPath = getSavaPath(context);
        File appDir = new File(savaPath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            if (mListener!=null){
                mListener.onSavaSuccess();
            }
        } catch (FileNotFoundException e) {
            if (mListener!=null){
                mListener.onSavaFailed();
            }
        } catch (IOException e) {
            if (mListener!=null){
                mListener.onSavaFailed();
            }
        }
        addMediaStore(context,file,savaPath);
    }
    /**
     * 将URL转化成bitmap形式
     *
     * @param url
     * @return bitmap type
     */
    private static Bitmap returnBitMap(String url) {
        URL myFileUrl;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, e.getMessage() );
        }
        return bitmap;
    }
    public interface OnSavaSuccessListener{

        void onSavaSuccess();
        void onSavaFailed();

    }
    public static void setOnSavaSuccessListener(OnSavaSuccessListener listener){
        mListener = listener;
    }
}
