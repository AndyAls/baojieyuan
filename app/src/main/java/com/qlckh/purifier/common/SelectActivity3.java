package com.qlckh.purifier.common;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.activity.SendActivity;
import com.qlckh.purifier.adapter.FastAdapter;
import com.qlckh.purifier.adapter.SelectAdapter;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.dao.BaojieDao;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.CuntryDao;
import com.qlckh.purifier.dao.GuanDao;
import com.qlckh.purifier.dao.MixDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.impl.SelectPresenterImpl;
import com.qlckh.purifier.presenter.SelectPresenter;
import com.qlckh.purifier.utils.ScreenUtils;
import com.qlckh.purifier.view.LoadingView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/26 13:40
 * Desc:
 */
public class SelectActivity3 extends BaseMvpActivity<SelectPresenter> implements SelectPresenter.SelectView {
    public static final String GUAN_ID = "GUAN_ID";
    public static final String BAOJIE_NAME = "BAOJIE_NAME";
    public static final int GUAN_ITEM = 0x7533;
    public static final int JIE_ITEM = 0xc31d;
    @BindView(R.id.cun_list)
    ListView cunList;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bt_sure)
    Button btSure;
    private ArrayList<CuntryDao.Cuntry> cuntryList;
    private FastAdapter cunAdapter;
    private List<GuanDao.Guan> guans;
    private HashMap<String, List<MixDao>> dataMap = new HashMap<>();
    private List<MixDao> mixDaos;
    private ArrayList<MixDao> list;

    @Override
    protected SelectPresenter initPresenter() {
        return new SelectPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select3;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        setTitle("选择发送员");
        goBack();
        cunList.setOnItemClickListener((parent, view, position, id) -> {

            mPresenter.getGuanList(cuntryList.get(position).getName(), position);
            CuntryDao.Cuntry cuntry = cuntryList.get(position);
            if (cuntry.getSort() != -100) {
                cuntry.setSort(-100);
            } else {
                cuntry.setSort(0);
            }

            cunAdapter.notifyDataSetChanged();
        });

        initRecyclerView();
    }

    private void initRecyclerView() {

        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                int spacing = ScreenUtils.px2dp(SelectActivity3.this, 15);
                int column = position % 3;
                outRect.left = column * spacing / 3;
                outRect.right = spacing - (column + 1) * spacing / 3;
                if (position >= 4) {
                    outRect.top = ScreenUtils.px2dp(SelectActivity3.this, 60);
                }
            }
        });

    }


    @Override
    public void initDate() {
        cuntryList = getIntent().getParcelableArrayListExtra(SendActivity.CUNTRY_LIST);
        if (cuntryList == null) {
            cuntryList = new ArrayList<>();
            showError("获取村列表失败");
        }
        setCunAdapter();
    }

    private void setCunAdapter() {
        cunAdapter = new FastAdapter() {
            @Override
            public int getCount() {
                return cuntryList == null ? 0 : cuntryList.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(SelectActivity3.this).inflate(R.layout.select_list_item, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                CuntryDao.Cuntry cuntry = cuntryList.get(position);
                int statte = cuntry.getSort();
                holder.ivCheck.setVisibility(View.GONE);
                holder.tvName.setText(cuntry.getName());
                if (statte == -100) {
                    holder.llPrent.setBackgroundColor(Color.WHITE);
                    holder.tvName.setTextColor(Color.RED);
                } else {
                    holder.llPrent.setBackgroundColor(Color.TRANSPARENT);
                    holder.tvName.setTextColor(getResources().getColor(R.color.text_color_normal));
                }

                return convertView;
            }
        };
        cunList.setAdapter(cunAdapter);
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }

    @Override
    public void onGuanSuccess(GuanDao guanDao, int postion) {
        mixDaos = new ArrayList<>();
        guans = guanDao.getData();
        if (guans == null) {
            guans = new ArrayList<>();
            showError("没有村管理员");
        }
        int sort = cuntryList.get(postion).getSort();
        if (sort == -100) {
            for (int i = 0; i < guans.size(); i++) {
                GuanDao.Guan guan = guans.get(i);
                MixDao mixDao = new MixDao();
                mixDao.setId(guan.getId());
                mixDao.setAddress(guan.getAddress());
                mixDao.setTel(guan.getTel());
                mixDao.setFullName(guan.getFullname());
                mixDao.setAdduser(guan.getAdduser());
                mixDao.setTopFlag(guan.getTopflag());
                mixDao.setItemType(GUAN_ITEM);
                mixDaos.add(mixDao);
            }
        }

        mPresenter.getBaojie(cuntryList.get(postion).getName(), postion);
    }


    @Override
    public void onBaojieSuccess(BaojieDao baojieDao, int postion) {
        List<BaojieDao.BaoJie> baoJies = baojieDao.getData();
        if (baoJies == null) {
            baoJies = new ArrayList<>();
            showError("没有保洁员");
        }
        int sort = cuntryList.get(postion).getSort();

        if (sort == -100) {
            for (int i = 0; i < guans.size(); i++) {
                BaojieDao.BaoJie guan = baoJies.get(i);
                MixDao mixDao = new MixDao();
                mixDao.setId(guan.getId());
                mixDao.setAddress(guan.getAddress());
                mixDao.setTel(guan.getTel());
                mixDao.setFullName(guan.getFullname());
                mixDao.setAdduser(guan.getAdduser());
                mixDao.setTopFlag(guan.getUser_type());
                mixDao.setItemType(JIE_ITEM);
                mixDaos.add(mixDao);
            }
            dataMap.put(cuntryList.get(postion).getName(), mixDaos);
        } else {
            dataMap.remove(cuntryList.get(postion).getName());
        }
        setAdapter();
    }

    private void setAdapter() {

        list = new ArrayList<>();
        list.clear();
        for (String key :
                dataMap.keySet()) {
            List<MixDao> mixDaos = dataMap.get(key);
            list.addAll(mixDaos);
        }

        Collections.sort(list);
        if (list.size() != 0) {
            MixDao dao = new MixDao();
            dao.setHeader(true);
            dao.setHeadName("村管理员");
            dao.setItemType(GUAN_ITEM);
            list.add(0, dao);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getItemType() - list.get(i - 1).getItemType() != 0) {
                    MixDao dao1 = new MixDao();
                    dao1.setHeadName("采集员");
                    dao1.setHeader(true);
                    list.add(i, dao1);
                    break;
                }
            }
        }
        SelectAdapter adapter = new SelectAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.onAttachedToRecyclerView(recyclerView);

        adapter.setonItemClickListener((view, position, daos) -> {
            MixDao mixDao = list.get(position);
            if (!mixDao.isSelect()) {
                mixDao.setSelect(true);
            } else {
                mixDao.setSelect(false);
            }
            adapter.notifyDataSetChanged();

        });
    }


    @Override
    public void onSuccess(CommonDao dao) {

    }

    @Override
    public void showLoading() {

        LoadingView.showLoading(this, "", false);
    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }
private StringBuilder builder = new StringBuilder();
private StringBuilder builder1 = new StringBuilder();
    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        back();
    }
    private void back() {
        if (list!=null){
            for (int i = 0; i < list.size(); i++) {
                MixDao mixDao = list.get(i);
                if (mixDao.isSelect()){
                    builder.append(mixDao.getTel()).append(",");
                    builder1.append(mixDao.getFullName()).append(",");
                }
            }
        }
        Intent intent = new Intent();
        intent.putExtra(GUAN_ID, builder.toString());
        intent.putExtra(BAOJIE_NAME, builder1.toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private class ViewHolder {
        TextView tvName;
        ImageView ivCheck;
        LinearLayout llPrent;

        ViewHolder(View view) {
            tvName = view.findViewById(R.id.tv_name);
            ivCheck = view.findViewById(R.id.iv_check);
            llPrent = view.findViewById(R.id.ll_prent);
        }
    }
}
