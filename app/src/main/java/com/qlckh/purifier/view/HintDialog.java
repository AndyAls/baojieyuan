package com.qlckh.purifier.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.adapter.FastAdapter;
import com.qlckh.purifier.utils.OnDialogClickListener;

import java.util.Calendar;

/**
 * @author Andy
 * @date 2018/5/19 15:14
 * @link {http://blog.csdn.net/andy_l1}
 * Desc:    HintDialog.java
 */
public class HintDialog {
    private static long lastTime = 0;

    public synchronized static boolean isReplayClick() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        boolean isClick;
        isClick = currentTime - lastTime < 2000;
        lastTime = currentTime;
        return isClick;
    }


    private static OnDialogItemClickListener mListener;

    public static void showHintDialog(Activity context, String title, String content, String sureText,
                                      String cancleText, boolean ifCancleView, final OnDialogClickListener listener) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_pic_save);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_hint_item);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        attributes.width = (int) (display.getWidth() * 0.8);
        attributes.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(attributes);
        TextView tvContent = (TextView) dialog.getWindow().findViewById(R.id.tv_content);
        TextView tvSure = (TextView) dialog.getWindow().findViewById(R.id.tv_sure);
        TextView tvTitle = (TextView) dialog.getWindow().findViewById(R.id.tv_title);
        TextView tvCancle = (TextView) dialog.getWindow().findViewById(R.id.tv_cancel);
        View view = dialog.getWindow().findViewById(R.id.view);
        if (title != null && !title.trim().equals("")) {
            tvTitle.setText(title);
        }
        if (content != null && !content.trim().equals("")) {
            tvContent.setText(content);
        }
        if (sureText != null && !sureText.trim().equals("")) {
            tvSure.setText(sureText);
        }
        if (cancleText != null && !cancleText.trim().equals("")) {
            tvCancle.setText(cancleText);
        }
        if (ifCancleView) {
            tvCancle.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        } else {
            tvCancle.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

        tvCancle.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancleClick();
            }
            dialog.dismiss();
        });
        tvSure.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSureClick();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    public static void showListDialog(Activity context, String[] datas, OnDialogItemClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_pic_save);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.choose_dialog_cotent);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        attributes.width = (int) (display.getWidth() * 0.8);
        attributes.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(attributes);
        ListView listView = dialog.getWindow().findViewById(R.id.list);
        listView.setDividerHeight(2);
        listView.setAdapter(new FastAdapter() {
            @Override
            public int getCount() {
                return datas == null ? 0 : datas.length;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (null == convertView) {
                    convertView = context.getLayoutInflater().inflate(R.layout.item_dealer, parent, false);
                    //防止影响OnItemClickListener工作
                    convertView.setClickable(false);
                }
                TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                tvContent.setText(datas[position]);

                return convertView;
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (listener != null) {
                listener.onItemClick(position, datas, view);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public interface OnDialogItemClickListener {
        void onItemClick(int position, String[] datas, View view);
    }
}
