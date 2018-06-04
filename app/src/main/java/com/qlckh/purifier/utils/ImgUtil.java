package com.qlckh.purifier.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;


/**
 * @author Andy
 * @date   2018/5/21 10:25
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    ImgUtil.java
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class ImgUtil {
	
	final static private String TAG = ImgUtil.class.getName();

    /**
     *
     * @param file
     * @param quality
     * @param picSizeLimit
     * @return
     */
	final public static File compress(File file, int quality, int picSizeLimit){
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(file.getName(), options);
			File compressedImg = null;
			int hasCompressTimes = 0;
			while (file.length()>picSizeLimit && hasCompressTimes < 5){
				compressedImg = new File(file.getParentFile(), file.getPath().hashCode()+".jpg");
				Bitmap bitmap = getLimitedBitmap(file.getAbsolutePath(), (int) (file.length()*4));
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(compressedImg.getPath(), false));
				bitmap.compress(CompressFormat.JPEG, quality, bos);
				bos.flush();
				bos.close();
				bitmap.recycle();
				if(hasCompressTimes > 0){
					file.delete();
				}
				file = compressedImg;
				hasCompressTimes++;
			}
			return file;
		}catch (Exception e){
		}
		return file;
	}

	public static long getFileSizes(Context context, File f) {

		long s = 0;
		try {
			if (f.exists()) {
				FileInputStream fis = null;
				fis = new FileInputStream(f);
				s = fis.available();
				fis.close();

			} else {
				f.createNewFile();
				Toast.makeText(context,"文件不存在", Toast.LENGTH_SHORT).show();
			}
		} catch (IOException e) {
			Toast.makeText(context,"文件不存在", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		return s;
	}

	/**
	 * 
	 * @param bitmap
	 * @param path
	 * @return true if success 
	 */
	final public static boolean save(Bitmap bitmap, String path) {
		BufferedOutputStream bos;
		try {
			File file = new File(new File(path).getParent());
			if(!file.exists()){
				file.mkdirs();
			}
			bos = new BufferedOutputStream(new FileOutputStream(path, false), 20480);
			bitmap.compress(CompressFormat.PNG, 100, bos);
			bos.flush();
			bos.close();
			return true;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} 
		return false;
	}
	/**
	 * 比流存入到文件里
	 * @param path 文件路径
	 * @return 存储成功返回true，失败返回false
	 */
	final public static boolean save(InputStream inStream, String path) {
		byte [] buffer = new byte[20480];
		FileOutputStream fos = null;
		try {
			File file = new File(path).getParentFile();
			if(!file.exists()){
				file.mkdirs();
			}
			fos = new FileOutputStream(path);
			int n = 0;
			while(n!=-1){
				n = inStream.read(buffer);
				fos.write(buffer);
				fos.flush();
			}
			fos.close();
			inStream.close();
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Image saving process failed");
		} 
		return false;
	}
	/**
	 * 比byte数组数据存入到文件里
	 * @param bytes
	 * @param path 文件路径
	 * @return 存储成功返回true，失败返回false
	 */
	final public static boolean save(byte[] bytes, String path) {
		try {
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file, false);
			if(!file.exists()){
				new File(file.getPath()).mkdirs();
			}
			fos.write(bytes);
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return false;
	}
	
	/**
	 * @param targetWidth 目标图片的宽
	 * @param targetHeight 目标图片的高
	 * @param res 图片的原文件路径
	 * @return inJustDecodeBounds = false;返回最佳的缩放比例Options。小图片会被放大
	 */
	final public static BitmapFactory.Options getOptions(String res, int targetWidth, int targetHeight) throws FileNotFoundException {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(res, options);
		int bw = options.outWidth;
		int bh = options.outHeight;
		if(bw*bh==0){//文件不存在或已经损坏
			throw new FileNotFoundException("获取的图片宽高均为0，请检查是否申请SD卡文件读写权限，检查文件是否存在");
		}
		options.inSampleSize = targetHeight*bw < bh*targetWidth ? (bw+targetWidth/2)/targetWidth : (bh+targetHeight/2)/targetHeight;
		options.inJustDecodeBounds = false;
		return options;
	}
	
	/**
	 * 
	 * @param res
	 * @param maxAreaPxSquare
	 * @return 缩放比例满足小于等于此面积
	 */
	private static BitmapFactory.Options getOptions(String res, int maxAreaPxSquare){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(res, options);
		options.inSampleSize = options.outWidth*options.outHeight/maxAreaPxSquare;
		if(options.outWidth*options.outHeight%maxAreaPxSquare!=0){
			options.inSampleSize++;
		}
		options.inJustDecodeBounds = false;
		return options;
	}
	
	/**
	 * 
	 * @param res
	 * @return 图片的宽度，不考虑exif中的旋转信息
	 */
	final public static int getWidth(String res){
		BitmapFactory.Options widthOptions = new BitmapFactory.Options();
		widthOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(res, widthOptions);
		if(widthOptions.outWidth*widthOptions.outHeight==0){
			Log.e(TAG, "获取的图片宽高均为0，请检查是否申请SD卡文件读写权限，检查文件是否存在");
			return 0;
		}else{
			return widthOptions.outWidth;
		}
	}
	/**
	 * 
	 * @param res
	 * @return  图片的高度，不考虑exif中的旋转信息
	 */
	final public static int getHeight(String res){
		BitmapFactory.Options heightOptions = new BitmapFactory.Options();
		heightOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(res, heightOptions);
		if(heightOptions.outWidth*heightOptions.outHeight==0){
			Log.e(TAG, "获取的图片宽高均为0，请检查是否申请SD卡文件读写权限，检查文件是否存在");
			return 0;
		}else{
			return heightOptions.outHeight;
		}
	}
	
	/**
	 * @param res 
	 * @return 获取一个含有图片尺寸的BitmapFactory.Options
	 */
	final public static BitmapFactory.Options getSizeOptions(String res){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(res, options);
		options.inJustDecodeBounds = false;
		return options;
	}
	
	final public static Bitmap getLimitedBitmap(String res, int limitWidth, int limitHeight) throws FileNotFoundException {
		Bitmap bitmap = null;
		try{
			bitmap = BitmapFactory.decodeFile(res, getOptions(res, limitWidth, limitHeight));
		}catch(OutOfMemoryError e){
			notifyOutOfMemory(e);
		}
		return bitmap;
	}
	
	private static Bitmap getLimitedBitmap(String res, int area) throws FileNotFoundException {
		Bitmap resultBitmap = null;
		try{
			Bitmap bitmap = null;
			bitmap = BitmapFactory.decodeFile(res, getOptions(res, area));
			if(null==bitmap || bitmap.getWidth()*bitmap.getHeight()==0){
//				new File(res).delete();
				throw new FileNotFoundException();
			}
			int degree = getImgRotateDegree(res);
			if(0!=degree){//对图片的横竖进行调整
				Matrix m = new Matrix();
				m.postRotate(degree);
				resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
				if(bitmap!=resultBitmap){
					bitmap.recycle();
				}
			}else{
				resultBitmap = bitmap;
			}
		}catch(OutOfMemoryError e){
			notifyOutOfMemory(e);
		}
		return resultBitmap;
	}

	/**
	 * 
	 * @param imgPath
	 * @return 获取图片的exif信息，返回图片被旋转的角度
	 */
	final public static int getImgRotateDegree(String imgPath){
		ExifInterface exif = null;
		int degree = 0;
        try {  
            exif = new ExifInterface(imgPath);
            if (exif != null) {  
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,  ExifInterface.ORIENTATION_UNDEFINED);
                switch (ori) {  
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;  
                    break;  
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;  
                    break;  
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;  
                    break;  
                default:  
                    degree = 0;  
                    break;  
                }  
            }  
        } catch (IOException e) {
        	Log.e(TAG, e.getMessage());
        }  
       
		return degree;
	}
	

	
	/**
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return 返回的Bitmap可能为原Bitmap（大小正好为目标大小，做Bitmap回收时要检查原Bitmap和返回的Bitmap是否是同一个实例
	 */
	final public static Bitmap getBitmap(Bitmap bitmap, int w, int h){
		Bitmap result = null;
		try {
			int bw = bitmap.getWidth();
			int bh = bitmap.getHeight();
			float scale = 1;
			if(bw*h==w*bh){//宽高比相等
				scale = 1.0f*h/bh;
				if(scale==1){//尺寸完全一样
					result = bitmap;
				}else{
					Matrix m = new Matrix();
					m.setScale(scale, scale);
					result = Bitmap.createBitmap(bitmap, 0, 0, bw, bh, m, false);
				}
			}else if(bw*h>w*bh){//更宽，需要切去宽
				Matrix m = new Matrix();
				scale = 1.0f*h/bh;
				m.setScale(scale, scale);
				result = Bitmap.createBitmap(bitmap, (int)((bw-1f*w*bh/h)/2f), 0, bh*w/h, bh, m, false);
			}else{
				Matrix m = new Matrix();
				scale = 1.0f*w/bw;//更高，需要切去高
				m.setScale(scale, scale);
				result = Bitmap.createBitmap(bitmap, 0,(int)((bh-1f*h*bw/w)/2f), bw, bw*h/w, m, false);
			}
		} catch (OutOfMemoryError e) {
			notifyOutOfMemory(e);
		}
		return result;
	}
	
	final public static Bitmap getScaledBitmap(String resPath, int height) throws IOException {
		BitmapFactory.Options options = getSizeOptions(resPath);
		int width = 0;
		int w = options.outWidth;
		int h = options.outHeight;
		if(w*h==0){
			throw new FileNotFoundException(resPath);
		}
		return getBitmap(resPath, width, height);
	}
	
	final public static Bitmap getScaledBitmap(int width, String resPath) throws IOException {
		int height = 0;
		BitmapFactory.Options options = getSizeOptions(resPath);
		int w = options.outWidth;
		int h = options.outHeight;
		if(w*h==0){
			throw new IOException(resPath);
		}
		height = h*width/w;
		return getBitmap(resPath, width, height);
	}
	
	
	/**
	 * @param w 
	 * @param h 
	 * @return 大小为w,宽度为h的Bitmap，对长宽比不符合要求的部分进行最大余量剪切
	 * @throws IOException
	 */
	final public static Bitmap getBitmap(String resPath, int w, int h) throws IOException {
		checkMemory();
		Bitmap resBitmap = null;
		try {
			Bitmap bitmap = null;
			int degree = getImgRotateDegree(resPath);
			if(degree==90 || degree==270){
				int tmp = w;
				w = h;
				h = tmp;
			}
			BitmapFactory.Options options = getOptions(resPath, w, h);
			BitmapRegionDecoder brd = BitmapRegionDecoder.newInstance(resPath, false);
			if(null==brd){
				bitmap = BitmapFactory.decodeFile(resPath, options);
			}else{
                int left, top, right, bottom;
                int width = brd.getWidth();
                int height = brd.getHeight();
                if(width*h==w*height){
                    left = 0;
                    top = 0;
                    right = width;
                    bottom = height;
                }else if(width*h>w*height){//目标图片比较高
                    left = (int) (0.5f*width-0.5f*height*w/h);
                    top = 0;
                    right = width-left;
                    bottom = height;
                }else{
                    left = 0;
                    top = (int) (0.5f*height-0.5f*width*h/w);
                    right = width;
                    bottom = height-top;
                }

                left = 0;
                Rect rect = new Rect(left, top, right, bottom);
                bitmap = brd.decodeRegion(rect, options);
            }
			if(null!=bitmap){
				resBitmap = getBitmap(bitmap, w, h);
				if(resBitmap!=bitmap){
					bitmap.recycle();
				}
				
				if(degree==90||degree==270){
					Matrix m = new Matrix();
					m.postRotate(degree);
					Bitmap bm = Bitmap.createBitmap(resBitmap, 0, 0, w, h, m, true);
					if(bm!=resBitmap){
						resBitmap.recycle();
					}
					resBitmap = bm;
				}
				
			}
		} catch (OutOfMemoryError e) {
			notifyOutOfMemory(e);
			throw new IOException();
		}
		return resBitmap;
	}
	
	
	/**
	 * 
	 * @param bitmap
	 * @return 从Bitmap获取JPEG格式编码后的byte数组
	 */
	final public static byte[] getBytes(Bitmap bitmap){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
	}
	
	
	
	
	/**
	 * @param resPath 图片文件路径
	 * @param size 生成的正方形Bitmap的size
	 * @param cornerRadius 圆角半径
	 * @return 返回长宽为size的带圆角的正方形Bitmap
	 * @throws IOException
	 */
	final public static Bitmap getSquareBitmapWithCorner(String resPath, int size, int cornerRadius) throws IOException {
		Bitmap resBitmap = null;
		try{
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			Xfermode modeIn = new PorterDuffXfermode(Mode.SRC_IN);
			Xfermode modeAll = new PorterDuffXfermode(Mode.SRC);
			RectF rect = new RectF(0, 0, size, size);
			
			Bitmap bitmap = null;
			bitmap = getBitmap(resPath, size, size);
			resBitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setXfermode(modeAll);
			canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
			paint.setXfermode(modeIn);
			canvas.drawBitmap(bitmap, null, rect, paint );
		}catch(OutOfMemoryError e){
			notifyOutOfMemory(e);
			throw new IOException();
		}
		return resBitmap;
	}
	
	
	
	/**
	 * 
	 * @return 返回正方形的Bitmap，对长方形进行最大余量剪切保留最大面积的居中
	 * @throws OutOfMemoryError
	 */
	final public static Bitmap getSquareBitmap(Bitmap bitmap){
		Bitmap resBitmap = null;
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		if(height>width){
			resBitmap = getBitmap(bitmap, width, width);
		}else if(height<width){
			resBitmap = getBitmap(bitmap, height, height);
		}else{
			resBitmap = bitmap;
		}
		return resBitmap;
	}
	
	
	/**
	 * @param resPath
	 * @return 获取正方形Bitmap，对编码后的Bitmap进行保留最大面积的居中余量剪切
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws OutOfMemoryError
	 */
	final public static Bitmap getSquareBitmap(String resPath) throws IOException {
		BitmapFactory.Options options = getSizeOptions(resPath);
		int size = options.outHeight < options.outWidth ? options.outHeight : options.outWidth;
		return getBitmap(resPath, size, size);
	}
	
	/**
	 * 
	 * @param resPath
	 * @return 异常或错误返回null,成功返回原始大小的Bitmap
	 * @throws IOException
	 */
	final public static Bitmap getBitmap(String resPath) throws IOException {
		checkMemory();
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeFile(resPath);
		} catch (OutOfMemoryError e){
			notifyOutOfMemory(e);
			throw new IOException();
		}
		return bitmap;
	}
	
	/**
	 * 调用第三方图库获取图片后，从返回的Intent中获取图片的路径
	 * @param resolver
	 * @param intent
	 * @return
	 */
	final public static String getImagePath(ContentResolver resolver, Intent intent){
		Uri originalUri = intent.getData();
		String[] proj = {MediaStore.Images.Media.DATA};
		Cursor cursor = resolver.query(originalUri, proj, null, null, null);
		int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		String path = null;
		if(cursor.moveToFirst()){
			path = cursor.getString(columnIndex);
		}
		cursor.close();
		return path;
	}
	
	/**
	 * 注意，此方法中Listener的回调代码一般是在非UI线程执行
	 * @param listener
	 */
	public static void registMemoryAlarm(MemoryListener listener){
		listenerSet.add(listener);
	}
	
	private static void notifyOutOfMemory(OutOfMemoryError e){
		e.printStackTrace();
		for(MemoryListener l : listenerSet){
			l.onOutOfMemory();
		}
	}
	
	public interface MemoryListener{
		void onOutOfMemory();
		/**
		 * 总内存使用已经超过80%
		 */
		void onLowMemory();
	}
	
	private static Paint circlePaint ;
	private static Paint bitmapPaint;
	private static Canvas c = new Canvas();
	/**
	 * @param bitmap
	 * @return 返回一个圆形的Bitmap
	 */
	public final static Bitmap getCircleBitmap(Bitmap bitmap){
		int size = 0;
		if(bitmap.getWidth() != bitmap.getHeight()){
			size = bitmap.getWidth()<bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
			bitmap = getBitmap(bitmap, size, size);
		}else{
			size = bitmap.getWidth();
		}
		if(null==circlePaint){
			circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			bitmapPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		}
		Bitmap out = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		c.setBitmap(out);
		int radius = size/2;
		c.drawCircle(radius, radius, radius, circlePaint);
		c.drawBitmap(bitmap, 0, 0, bitmapPaint);
		return out;
	}
	
	final static void checkMemory(){
		Runtime r = Runtime.getRuntime();
		if(r.totalMemory()-r.freeMemory()>MEMORY_BOUNDARY){
			Log.e(ImgUtil.TAG, "Memory Low");
			for(MemoryListener l : listenerSet){
				l.onLowMemory();
			}
		}
	}
	final private static long MEMORY_BOUNDARY = (long) (Runtime.getRuntime().maxMemory()*0.8f);
	private static HashSet<MemoryListener> listenerSet = new HashSet<MemoryListener>();

	public static String getPicSavaPath(Context context) {
		File sdcardDir;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			sdcardDir = Environment.getExternalStorageDirectory();
		} else {
			sdcardDir = context.getCacheDir();
		}

		String path = sdcardDir.getPath() + File.separator + context.getPackageName();
		File picDir = new File(path);
		if (!picDir.exists()) {
			picDir.mkdirs();
		}
		return path;
	}
}
