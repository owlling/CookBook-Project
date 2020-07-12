package com.owlling.cookbook.community.acc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.owlling.cookbook.R;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterCallback {

    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etPwd1)
    EditText etPwd1;
    @Bind(R.id.etPwd2)
    EditText etPwd2;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.registerBack)
    ImageView registerBack;
    @Bind(R.id.tvToLogin)
    TextView tvToLogin;

    private UserPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected Presenter getPresenter() {
        presenter = new UserPresenter(this);
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bgLogin)
                .init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(Context context) {
        final Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit)

//    注册模块
    public void onViewClicked() {

        final String n = etUsername.getText().toString();
        final String p1 = etPwd1.getText().toString();
        final String p2 = etPwd2.getText().toString();
        if (TextUtils.isEmpty(n) || TextUtils.isEmpty(p1) || TextUtils.isEmpty(p2)) {
            ToastUtil.showToast(this, "注册信息填写有误");
        } else if (!p1.equals(p2)) {
            ToastUtil.showToast(this, "密码不一致");
        } else {
            presenter.register(this, n, p1);
        }
    }

    @Override
    public void onSuccess() {
        // to login
        ToastUtil.showToast(this, "注册成功， 请登录");
        finish();
        Log.i("on success ", "register success and to login..");
    }

    @Override
    public void onFailure(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @OnClick({R.id.registerBack, R.id.tvToLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.registerBack:
                finish();
                break;
            case R.id.tvToLogin:
                finish();
                break;
        }
    }
}
