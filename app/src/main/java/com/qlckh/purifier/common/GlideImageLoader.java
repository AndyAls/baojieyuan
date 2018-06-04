package com.qlckh.purifier.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qlckh.purifier.R;
import com.youth.banner.loader.ImageLoader;

/**
 * @author Andy
 * @date 2018/5/17 11:09
 * Desc:全局配置glide图片加载
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        GlideApp.with(context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .encodeFormat(Bitmap.CompressFormat.JPEG)
                .error(R.drawable.error)
                .placeholder(R.drawable.error)
                .centerCrop()
                .into(imageView);

    }
}
