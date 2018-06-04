package com.qlckh.purifier.view.richtextview;

import android.graphics.Bitmap;

import java.io.IOException;
/**
 * @author Andy
 * @date   2018/5/31 16:58
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    ImageLoader.java
 */

public interface ImageLoader {
    Bitmap getBitmap(String url) throws IOException;
}
