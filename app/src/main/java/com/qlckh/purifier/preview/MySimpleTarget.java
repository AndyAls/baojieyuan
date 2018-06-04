package com.qlckh.purifier.preview;


import android.graphics.drawable.Drawable;

/**
 * @author Andy
 * @date   2018/5/24 15:51
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    图片加载回调状态接口
 */
public interface MySimpleTarget<T> {
    /**
     * Callback when an image has been successfully loaded.
     * <p>
     * <strong>Note:</strong> You must not recycle the bitmap.
     * @param bitmap  内容
     */
    void onResourceReady(T bitmap);

    /**
     * Callback indicating the image could not be successfully loaded.
     * @param errorDrawabla 内容
     */
    void onLoadFailed(Drawable errorDrawabla);

    /**
     * Callback invoked right before your request is submitted.
     */
    void onLoadStarted();

    void onLoadCancled();

}
