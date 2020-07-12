package com.owlling.cookbook.community.acc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.owlling.cookbook.R;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.PrefUtil;
import com.owlling.cookbook.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginCallback {

    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.loginBack)
    ImageView loginBack;
    private UserPresenter presenter;
    public static final String RETURN_TO_TAB = "return_to_tab";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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

    public static void start(Context context) {
        final Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFailure(String msg) {
        ToastUtil.showToast(this, msg);
    }

//    登录模块

//    登录成功
    @Override
    public void onSuccess(LoginBean loginBean) {
        ToastUtil.showToast(this, "登录成功");
        PrefUtil.setUserId(loginBean.uid);
        final int tab = getIntent().getIntExtra(RETURN_TO_TAB, -1);
        if (tab != -1){
            final Intent intent = new Intent();
            intent.putExtra(RETURN_TO_TAB, tab);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

//    登录失败
    @OnClick({R.id.submit, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                final String u = etUsername.getText().toString();
                final String p = etPwd.getText().toString();
                if (TextUtils.isEmpty(u) || TextUtils.isEmpty(p)) {
                    ToastUtil.showToast(this, "错误的输入");
                } else {
                    presenter.login(this, u, p);
                }
                break;
            case R.id.register:
                RegisterActivity.start(this);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginBack)
    public void onViewClicked() {
        finish();
    }
}
