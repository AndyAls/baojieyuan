package com.qlckh.purifier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.dao.ScanListDao;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/6/12 17:36
 * Desc:
 */
public class PurifierAdapter extends RecyclerView.Adapter<PurifierAdapter.VH> {

    private final Context mContext;
    private  List<ScanListDao.ScanList> mDatas;

    public PurifierAdapter(Context context, List<ScanListDao.ScanList> datas) {

        this.mContext = context;
        this.mDatas = datas;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.purifier_list_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ScanListDao.ScanList scanList = mDatas.get(position);
        holder.tvName.setText(scanList.getUsername());
        if (position==0){
            holder.tvUnscanedCount.setText("未扫住户");
            holder.tvScanedCount.setText("已扫住户");
        }else {
            holder.tvUnscanedCount.setText(MessageFormat.format("{0}", scanList.getWs()));
            holder.tvScanedCount.setText(MessageFormat.format("{0}", scanList.getYisao()));
        }

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setNewData(List<ScanListDao.ScanList> data) {
        this.mDatas=data;
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_scaned_count)
        TextView tvScanedCount;
        @BindView(R.id.tv_unscaned_count)
        TextView tvUnscanedCount;
        VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
