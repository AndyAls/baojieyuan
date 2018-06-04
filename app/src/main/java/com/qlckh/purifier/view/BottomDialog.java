package com.qlckh.purifier.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qlckh.purifier.R;

/**
 * @author Andy
 * @date 2018/5/16 14:05
 * Desc:
 */
public class BottomDialog extends Dialog {

    private Context mContext;
    private static onDialogItemClickListener listener;

    public BottomDialog(@NonNull Context context) {
        super(context);
        this.mContext=context;
    }

    public static class Builder{

        private Activity mContext;
        private String [] mDatas;
        private int selectPos=-1;

        public Builder(Activity context) {
            this.mContext=context;
            mDatas = new String[]{"采集员","村管理员","街道管理员"};
        }

        public BottomDialog create(){

            BottomDialog dialog = new BottomDialog(mContext);

            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(R.layout.dialog_contacts);
            WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
            WindowManager windowManager = mContext.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            attributes.width= (int) (display.getWidth()*0.9);
            attributes.gravity = Gravity.BOTTOM;
            dialog.getWindow().setAttributes(attributes);
            dialog.getWindow().setDimAmount(0.8f);
            dialog.getWindow().setWindowAnimations(R.style.dialog_animation);
            ListView lv = (ListView) dialog.getWindow().findViewById(R.id.lv);

            final DialogAdapter adapter = new DialogAdapter(mDatas);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener((parent, view, position, id) -> {

                if (listener !=null){
                    listener.onDialogItemClick(position,mDatas);
                    selectPos=position;
                    adapter.notifyDataSetChanged();
                }
            });
            return dialog;
        }

        private class DialogAdapter extends BaseAdapter{
            private  String[] datas;

            public DialogAdapter(String[] mDatas) {
                this.datas= mDatas;
            }

            @Override
            public int getCount() {
                return datas.length;
            }

            @Override
            public Object getItem(int position) {
                return datas[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;

                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_contacts_item, parent, false);
                    holder.tv = (TextView) convertView.findViewById(R.id.text1);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.tv.setText(mDatas[position]);
                if (selectPos != -1 && selectPos == position) {
                    holder.tv.setTextColor(mContext.getResources().getColor(R.color.text_blue_color));
                }else{
                    holder.tv.setTextColor(mContext.getResources().getColor(R.color.black));
                }
                return convertView;
            }
        }

        class ViewHolder {
            TextView tv;
        }
    }

    public interface onDialogItemClickListener{
        void onDialogItemClick(int position,String [] mDatas);
    }
    public void setonDialogItemClickListener(onDialogItemClickListener listenerr){
        listener = listenerr;
    }
}
