package com.qlckh.purifier.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author Andy
 * @date   2018/5/17 10:04
 * Desc:    ScreenUtils.java
 */
public class ScreenUtils {
	/**
	 * * @方法说明:获取DisplayMetrics对象 * @方法名称:getDisPlayMetrics * @param context * @return
	 * * @返回值:DisplayMetrics
	 */
	public static DisplayMetrics getDisPlayMetrics(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		if (null != context) {
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metric);
		}
		return metric;
	}

	/**
	 * * @方法说明:获取屏幕的宽度（像素） * @方法名称:getScreenWidth * @param context * @return *
	 * 
	 * @返回值:int
	 */
	public static int getScreenWidth(Context context) {
		int width = getDisPlayMetrics(context).widthPixels;
		return width;
	}

	/**
	 * * @方法说明:获取屏幕的高（像素） * @方法名称:getScreenHeight * @param context * @return *
	 * 
	 * @返回值:int
	 */
	public static int getScreenHeight(Context context) {
		int height = getDisPlayMetrics(context).heightPixels;
		return height;
	}

	/**
	 * * @方法说明:屏幕密度(0.75 / 1.0 / 1.5) * @方法名称:getDensity * @param context * @return
	 * * @返回 float
	 */
	public static float getDensity(Context context) {
		return getDisPlayMetrics(context).density;
	}

	/**
	 * * @方法说明:屏幕密度DPI(120 / 160 / 240) * @方法名称:getDensityDpi * @param context * @return
	 * * @返回 int
	 */
	public static int getDensityDpi(Context context) {
		int densityDpi = getDisPlayMetrics(context).densityDpi;
		return densityDpi;
	}

	public static int px2dp(Context context, float pxValue){
		final float scale = getDensity(context);
		return (int) (pxValue / scale + 0.5f);
	}


	public static int dp2px(Context context, float dpValue){
		final float scale = getDensity(context);
		return (int) (dpValue * scale + 0.5f);
	}

	public static int getStatusBarHeight(Activity activity){
		int result = 0;
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = activity.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
}