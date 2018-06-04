package com.qlckh.purifier.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MainThread;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qlckh.purifier.App;
import com.qlckh.purifier.R;

import java.lang.ref.WeakReference;


/**
 * @author Toast封装
 *         showShort()
 *         showLong()如果没有重载config参数,使用默认自定义样式toast,重载config 可以自定义toast样式
 *         <p>
 *         config 通过 Builder来构建
 */

public class IToast {

    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
    private static int DEFAULT_GRAVITY = Gravity.CENTER;
    private static int DEFAULT_XOFFSET = 0;
    private static int DEFAULT_YOFFSET = 0;
    private static int DEFAULT_TEXT_SIZE = 14;
    private static int DEFAULT_CORNER_RADIUS=6;
    private static int DEFAULT_BG_COLOR = Color.parseColor("#BF000000");
    private static Toast mToast;
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Runnable run = new Runnable() {
        @Override
        public void run() {
            cancle();
        }
    };
    private IToast() {
        throw new UnsupportedOperationException("IToast can't be instantiated ...");
    }

    public static void showShort(Context context, CharSequence s) {
        show(context, s, null, false, false, Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, CharSequence s, Config config) {
        show(context, s, config, false, false, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, CharSequence s) {
        show(context, s, null, false, false, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, CharSequence s, Config config) {
        show(context, s, config, false, false, Toast.LENGTH_LONG);
    }

    public static void showSuccess(Context context, CharSequence s) {
        show(context, s, null, true, true, Toast.LENGTH_SHORT);
    }

    public static void showError(Context context, CharSequence s) {
        show(context, s, null, false, true, Toast.LENGTH_SHORT);
    }

    @MainThread
    private static void show(Context context, CharSequence s, Config config, boolean isShowSuccess, boolean isCustom, int duration) {
        handler.removeCallbacks(run);
        cancle();
        mToast = new Toast(context);
        //显示失败或成功的Toast
        if (isCustom) {
            showSuccessOrError(context, s, isShowSuccess, duration);
        } else {
            if (config == null) {
                showNormal(context, s, duration);
            } else {
                //如果设置了cofig 调用setView后会只显示View,其他设置无效
                if (config.viewWeakReference != null) {
                    final View view = config.viewWeakReference.get();
                    if (view != null) {
                        mToast.setDuration(duration);
                        mToast.setGravity(config.gravity, config.xOffset, config.yOffset);
                        mToast.setView(view);
                    }
                } else {
                    showCustom(context, s, config, duration);
                }
            }
        }
        int delayMills = 0;
        if (duration == Toast.LENGTH_SHORT) {
            delayMills = 1000;
        } else if (duration == Toast.LENGTH_LONG) {
            delayMills = 3000;
        }
        mToast.show();
        handler.postDelayed(run, delayMills);
    }

    private static void showCustom(Context context, CharSequence s, Config config, int duration) {
        TextView textView = new TextView(context);
        if (config.bgRes == -1) {
            textView.setBackgroundResource(R.drawable.toast_success_bg);
            setDrawableColor(config, textView);
        } else {
            textView.setBackgroundResource(config.bgRes);
            if (config.bgColor != DEFAULT_BG_COLOR) {
                setDrawableColor(config, textView);
            }

        }

        textView.setTextColor(config.textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,config.textSize);
        textView.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
        textView.setGravity(Gravity.CENTER);
        textView.setText(s);
        mToast.setGravity(config.gravity, config.xOffset, config.yOffset);
        mToast.setDuration(duration);
        mToast.setView(textView);
    }

    private static void showSuccessOrError(Context context, CharSequence s, boolean isShowSuccess, int duration) {
        View toastView;
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(duration);
        toastView = LayoutInflater.from(context).inflate(R.layout.toast_success_view, null);
        TextView tvMessage = toastView.findViewById(R.id.tv_message);
        ImageView ivIcon = toastView.findViewById(R.id.iv_icon);
        tvMessage.setText(s);
        if (isShowSuccess) {
            ivIcon.setBackgroundResource(R.drawable.toast_success_icon);
        } else {
            ivIcon.setBackgroundResource(R.drawable.toast_error_icon);
        }
        mToast.setView(toastView);
    }
    private static int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, App.getAppContext().getResources().getDisplayMetrics());
    }
    private static void showNormal(Context context, CharSequence s, int duration) {
        TextView textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.toast_success_bg);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        textView.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
        textView.setGravity(Gravity.CENTER);
        textView.setText(s);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(textView);
    }

    private static void setDrawableColor(Config config, TextView textView) {
        GradientDrawable drawable = (GradientDrawable) textView.getBackground().mutate();
        drawable.setColor(config.bgColor);
        if (config.cornerRadius!=DEFAULT_CORNER_RADIUS){
            drawable.setCornerRadius(config.cornerRadius);
        }
            textView.setBackgroundDrawable(drawable);
    }

    public static class Config {
        private int textColor = DEFAULT_TEXT_COLOR;
        private int gravity = DEFAULT_GRAVITY;
        private int xOffset = DEFAULT_XOFFSET;
        private int yOffset = DEFAULT_YOFFSET;
        private int textSize = DEFAULT_TEXT_SIZE;
        private int bgColor = DEFAULT_BG_COLOR;
        private int bgRes = -1;
        private int cornerRadius=DEFAULT_CORNER_RADIUS;
        private WeakReference<View> viewWeakReference;

        private Config() {
        }

    }

    public static class Builder {
        private Config config;

        public Builder() {
            config = new Config();
        }

        /**
         * @param color 设置字体颜色
         * @return this
         */
        @CheckResult
        public Builder setMessageColor(@ColorInt int color) {
            this.config.textColor = color;
            return this;
        }

        /**
         * @param gravity Gravity.CENTER 等
         * @param xOffset 相对x偏移
         * @param yOffset 相对y偏移
         * @return this
         */
        @CheckResult
        public Builder setGravity(int gravity, int xOffset, int yOffset) {

            this.config.gravity = gravity;
            this.config.xOffset = xOffset;
            this.config.yOffset = yOffset;
            return this;
        }

        /**
         * @param textSize 设置字体大小
         * @return this
         */
        @CheckResult
        public Builder setTextSize(int textSize) {
            this.config.textSize = textSize;
            return this;
        }

        /**
         * @param bgColor 设置toast背景色
         * @return this
         */
        @CheckResult
        public Builder setBgColor(@ColorInt int bgColor) {
            this.config.bgColor = bgColor;
            return this;
        }

        /**
         * * @param bgRes 设置toast的背景资源
         *
         * @return this
         */
        @CheckResult
        public Builder setBgRes(@DrawableRes int bgRes) {
            this.config.bgRes = bgRes;
            return this;
        }

        /**
         * @param radius 设置toast的圆角
         * @return this
         */
        @CheckResult
        public Builder setCornerRadius(int radius){
            this.config.cornerRadius=radius;
            return this;
        }
        /**
         * @param layoutId 设置toast直接显示的view 资源
         * @return this
         */
        @CheckResult
        public Builder setView(@LayoutRes int layoutId, Context context) {
            View view = LayoutInflater.from(context).inflate(layoutId, null);
            this.config.viewWeakReference = new WeakReference<>(view);
            return this;
        }


        /**
         * @param view 设置toast直接显示的view
         * @return this
         */
        @CheckResult
        public Builder setView(View view) {
            this.config.viewWeakReference = view == null ? null : new WeakReference<>(view);
            return this;
        }

        /**
         * @return 创建cofig对象
         */
        public Config build() {
            return config;
        }
    }


    /**
     * 退出Toast
     */
    private static void cancle() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
