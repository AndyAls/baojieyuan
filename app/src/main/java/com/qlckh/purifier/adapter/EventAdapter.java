package com.qlckh.purifier.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.activity.TaskWorkingAcitivty;
import com.qlckh.purifier.dao.EventListDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/5/29 13:25
 * Desc:
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.VH> {

    private final Context mContext;
    private final ArrayList<EventListDao.EventDao> mDatas;
    private onItemClickListener mListener;

    public EventAdapter(TaskWorkingAcitivty taskWorkingAcitivty, ArrayList<EventListDao.EventDao> datas) {
        this.mContext = taskWorkingAcitivty;
        this.mDatas = datas;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.event_list_item, parent, false);
        return new VH(inflate);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        EventListDao.EventDao eventDao = mDatas.get(position);
        holder.tvTitle.setText(eventDao.getTitle());
        holder.tvContent.setText(eventDao.getContent());
        int status = eventDao.getStatus();
        switch (status) {
            case 0:
                holder.tvState.setText("待处理");
                holder.tvState.setTextColor(mContext.getResources().getColor(R.color.status_orange));
                holder.tvState.setBackground(mContext.getResources().getDrawable(R.drawable.boc_check_status3));
                break;
            case 1:
                holder.tvState.setText("已处理");
                holder.tvState.setTextColor(mContext.getResources().getColor(R.color.status_red));
                holder.tvState.setBackground(mContext.getResources().getDrawable(R.drawable.boc_check_status4));
                break;
            case 2:
                holder.tvState.setText("已完成");
                holder.tvState.setTextColor(mContext.getResources().getColor(R.color.status_green));
                holder.tvState.setBackground(mContext.getResources().getDrawable(R.drawable.boc_check_status2));
                break;
            default:
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_content)
        TextView tvContent;

        VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemClick(v, VH.this.getAdapterPosition());
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

}
