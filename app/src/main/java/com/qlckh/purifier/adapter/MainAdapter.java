package com.qlckh.purifier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.dao.MainDao;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.usercase.BadgeEvent;
import com.qlckh.purifier.usercase.MessageEvent;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/5/17 16:32
 * Desc:
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHodler> {
    private final Context mContext;
    private final List<MainDao> mDatas;
    private  BadgeEvent mBadge;
    private onItemClickListener mListener;
    private MessageEvent message;

    public MainAdapter(Context mainActivity, List<MainDao> datas, BadgeEvent b,MessageEvent event) {
        this.mContext = mainActivity;
        this.mDatas = datas;
        this.mBadge=b;
        this.message=event;

    }

    @NonNull
    @Override
    public MyViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_list_item, parent, false);
        return new MyViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodler holder, int position) {
        holder.icon.setImageDrawable(mContext.getResources().getDrawable(mDatas.get(position).getIcon()));
        holder.tvTitle.setText(mDatas.get(position).getTitle());
        holder.setIsRecyclable(false);
        setBadge(position,holder);

    }

    private void setBadge(int position, MyViewHodler holder) {
        if (mBadge != null) {

            if (UserConfig.getType() == 1&&position==2&&mDatas.get(position).getId()==2) {
                setEventBadge(holder, mBadge.getCaiBadge());

            }else if (UserConfig.getType()==2&&position==1&&mDatas.get(position).getId()==2){

                setEventBadge(holder, mBadge.getCunBadge());

            }
        }

        if (message!=null){

            if (UserConfig.getType() == 1&&position==5&&mDatas.get(position).getId()==5) {
                setEventBadge(holder, message.getUnRead());

            }else if (UserConfig.getType()==2&&position==2&&mDatas.get(position).getId()==5){

                setEventBadge(holder, message.getUnRead());

            }
        }
    }

    private void setEventBadge(MyViewHodler holder, int caiBadge) {
        if (caiBadge > 0) {
            holder.tvBadge.setVisibility(View.VISIBLE);
            holder.tvBadge.setText(String.format(Locale.SIMPLIFIED_CHINESE, "%d", caiBadge));
            if (caiBadge > 99) {
                holder.tvBadge.setText("99+");
            }
        } else {
            holder.tvBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_badge)
        TextView tvBadge;

        MyViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(v, MyViewHodler.this.getAdapterPosition());
                    }
                }
            });

        }
    }
    public void setmBadge(BadgeEvent badge){
        this.mBadge=badge;
    }
    public void setMsgBadge(MessageEvent event){
        this.message=event;
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

}
