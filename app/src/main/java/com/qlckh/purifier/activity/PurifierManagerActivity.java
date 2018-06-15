package com.qlckh.purifier.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qlckh.purifier.R;
import com.qlckh.purifier.adapter.PurifierAdapter;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.CunListDao;
import com.qlckh.purifier.dao.ScanListDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.impl.PurifierManagerPersenterImpl;
import com.qlckh.purifier.presenter.PurifierManagerPersenter;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.view.DatePickerView;
import com.qlckh.purifier.view.HintDialog;
import com.qlckh.purifier.view.LoadingView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/6/12 10:46
 * Desc:
 */
public class PurifierManagerActivity extends BaseMvpActivity<PurifierManagerPersenter> implements PurifierManagerPersenter.PurifierView {
    @BindView(R.id.tv_cun)
    TextView tvCun;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String cunid;
    private String startTime;
    private List<CunListDao.Cundao> cundaos;
   private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
   private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日", Locale.SIMPLIFIED_CHINESE);
    private DatePickerView datePickerView;
    private List<ScanListDao.ScanList> mDatas;
    private PurifierAdapter adapter;

    @Override
    protected PurifierManagerPersenter initPresenter() {
        return new PurifierManagerPersenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_purifier_manager;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void onCunSuccess(CunListDao dao) {

        if (dao!=null&&dao.getStatus()==1&&dao.getData().size()>0){

            String cunid=dao.getData().get(0).getId()+"";
            cundaos = dao.getData();
            if (UserConfig.getType()==2){
                mPresenter.scanList("33",tvData.getText().toString(),cunid);
                tvCun.setText(dao.getData().get(0).getName());
            }else {
                mPresenter.scanList("33",tvData.getText().toString(),"");
                tvCun.setText("请选择村");

            }
        }else {
            showShort("获取村失败");
        }


    }

    @Override
    public void onScanSuccess(ScanListDao dao) {
        if (dao!=null){
            mDatas = dao.getData();
        }
        setAdapter();
    }

    private void setAdapter() {

        ScanListDao.ScanList scanList = new ScanListDao.ScanList();
        scanList.setUsername("姓名");
        scanList.setWs(0);
        scanList.setYisao(0);
        if (mDatas!=null){
            mDatas.add(0,scanList);
        }
        if (adapter==null){
            adapter = new PurifierAdapter(this,mDatas);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.setNewData(mDatas);
        }

    }

    @Override
    public void onSuccess(CommonDao dao) {

    }

    @Override
    public void showLoading() {
        LoadingView.showLoading(this,"",false);

    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }

    @Override
    public void initView() {

        setTitle("保洁员管理");
        goBack();
        if (UserConfig.getType()==2){
            tvCun.setClickable(false);
            tvCun.setCompoundDrawables(null,null,null,null);
            tvCun.setCompoundDrawablePadding(0);
        }else {
            tvCun.setClickable(true);
        }
        tvData.setText(sdf.format(System.currentTimeMillis()));
        startTime=tvData.getText().toString();
        datePickerView = new DatePickerView(this);
        initRecyclerView();
    }
    private void initRecyclerView() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }


    @Override
    public void initDate() {

        mPresenter.getCunList();
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }


    @OnClick({R.id.tv_cun, R.id.tv_data, R.id.bt_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cun:
                showCunDialog();
                break;
            case R.id.tv_data:
                showDataDialog();
                break;
            case R.id.bt_query:
                startTime=tvData.getText().toString();
                mPresenter.scanList("33",startTime,cunid);
                break;
                default:
        }
    }

    private void showDataDialog() {
        String s = tvData.getText().toString();
        String format="";
        try {
            Date parse = sdf.parse(s);
            format = sdf2.format(parse);
        } catch (ParseException e) {
            format=sdf2.format(new Date());
            e.printStackTrace();
        }
        datePickerView.dateTimePicKDialog(tvData,format);
    }

    private void showCunDialog() {

        ArrayList<String> datas  = new ArrayList<>();
        String [] a = new String[]{};
        if (cundaos!=null){
            for (int i = 0; i < cundaos.size(); i++) {
                datas.add(cundaos.get(i).getName());
            }
        }
        HintDialog.showListDialog(this, datas.toArray(a), (position, datas1, view) -> {

            tvCun.setText(datas1[position]);
            cunid=cundaos.get(position).getId()+"";
        });

    }
}
