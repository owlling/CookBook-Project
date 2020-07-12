package com.owlling.cookbook.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.owlling.cookbook.presenter.Presenter;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        context = this;
        ButterKnife.bind(this);

        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        init(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(presenter == null && getPresenter() != null){
            presenter = getPresenter();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);

        if(presenter != null){
            presenter.destroy();
        }
    }

    public Context getContext(){
        return context;
    }

    protected abstract int getLayoutId();
    protected abstract Presenter getPresenter();
    protected abstract void init(Bundle savedInstanceState);

}
