package com.qlckh.purifier.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.qlckh.purifier.R;
import com.qlckh.purifier.api.TestActivity;
import com.qlckh.purifier.base.BaseMvpActivity;
import com.qlckh.purifier.dao.HomeDao;
import com.qlckh.purifier.impl.LoginPresenterImpl;
import com.qlckh.purifier.presenter.LoginPresenter;
import com.qlckh.purifier.user.UseDo;
import com.qlckh.purifier.user.UserConfig;
import com.qlckh.purifier.utils.PhoneUtil;
import com.qlckh.purifier.view.BottomDialog;
import com.qlckh.purifier.view.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/15 16:53
 * Desc:
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginPresenter.LoginView {
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.ll_role)
    LinearLayout llRole;
    @BindView(R.id.tv_role)
    TextView tvRole;

    private int userType = 1;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        header.setVisibility(View.GONE);
        ImmersionBar.with(this).transparentBar()
                .keyboardEnable(true)
                .init();
    }

    @Override
    public void initDate() {

    }

    /**
     * 登录接口失败
     *
     * @param msg 错误信息
     */
    @Override
    public void showError(String msg) {
        showShort(msg);

    }

    @Override
    public void release() {
//        RxHttpUtils.cancelAllRequest();

    }

    @Override
    public void showLoading() {

        LoadingView.showLoading(this, "", false);
    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }

    /**
     * 登录接口成功
     *
     * @param info
     */
    @Override
    public void getUser(UseDo info) {
        String msg = info.getMsg();
        int status = info.getStatus();
        //登录成功
        if (status == 1) {
            UserConfig.savaLogin(true);
            UserConfig.savaUserName(phoneEdit.getText().toString().trim());
            UserConfig.savaType(userType);
            UserConfig.savaPwd(passwordEdit.getText().toString().trim());
            UserConfig.savaUserid(info.getData().getId());
            UserConfig.userInfo = info.getData();
            toMian();
        } else {
            showShort("请确定密码账号和用户角色选择是否正确");
            UserConfig.savaLogin(false);
        }

    }

    private void toMian() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick({R.id.ll_role, R.id.login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_role:
                showRolePup();
                break;
            case R.id.login_button:
//                test();
                login();
                break;
            default:
        }
    }

    private void test() {
        /**
         * caiid : 14
         * caiuser : 王菊莲
         * id : 774
         * username : 陈灿军
         * address : 6111
         * sheng : 浙江省
         * shi : 绍兴市
         * xiang : 新昌县
         * cun : 南苑
         * cunid : 35
         */
        Intent intent = new Intent(this, TestActivity.class);
        HomeDao homedao = new HomeDao();
        homedao.setCaiid("11");
        homedao.setId("110");
        homedao.setCaiuser("保洁员");
        homedao.setCaiid("774");
        homedao.setAddress("6111");
        homedao.setSheng("浙江省");
        homedao.setShi("hangzhou");
        homedao.setXiang("jianggan");
        homedao.setCun("三村");
        homedao.setCunid("35");
        ;
        intent.putExtra("HOME_DAO", homedao);
        startActivity(intent);

    }


    private void showRolePup() {
        final BottomDialog dialog = new BottomDialog.Builder(this).create();
        dialog.show();
        dialog.setonDialogItemClickListener((position, mDatas) -> {
            tvRole.setText(mDatas[position]);
            dialog.dismiss();
            switch (position) {
                case 0:
                    userType = 1;
                    break;
                case 1:
                    userType = 2;
                    break;
                case 2:
                    userType = 3;
                    break;
                default:
            }
        });

    }

    /**
     * 登录
     */
    private void login() {

        String name = phoneEdit.getText().toString().trim();
        String pwd = passwordEdit.getText().toString().trim();
        if (checkData()) {
            if (userType == 1) {
                if (!PhoneUtil.isN5s()) {
                    showShort("请选择正确的角色,采集员只能终端进行登录");
                    return;
                }
            }
            mPresenter.login(name, pwd, userType);
        }

    }

    private boolean checkData() {
        if (isEmpty(phoneEdit.getText().toString().trim())) {
            showShort("请输入用户名");
            return false;
        }
        if (isEmpty(passwordEdit.getText().toString().trim())) {
            showShort("请输入密码");
            return false;
        }
        return true;
    }

}
