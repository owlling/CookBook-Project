package com.owlling.cookbook.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.owlling.cookbook.IView.ISplashView;
import com.owlling.cookbook.presenter.SplashPresenter;

public class SplashActivity extends Activity implements ISplashView {

    private SplashPresenter splashPresenter;

    @Override //开始
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashPresenter = new SplashPresenter(this, this); //创建Sp*调用initData
        splashPresenter.initData(); //准备下一个页面数据

    }

    @Override
    public void onSplashInitData() {
        startMainActivity();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("deprecation")
    private void startMainActivity() {
        MainActivity.startActivity(this);
        overridePendingTransition(0, 0);
        finish();

    }

}
