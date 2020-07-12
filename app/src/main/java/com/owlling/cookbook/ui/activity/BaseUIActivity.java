package com.owlling.cookbook.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.owlling.cookbook.presenter.Presenter;

import butterknife.ButterKnife;

public abstract class BaseUIActivity extends AppCompatActivity {
    protected Context context;
    protected Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(getLayoutId());
        context = this;
        ButterKnife.bind(this);

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

    protected abstract Presenter getPresenter();
    protected abstract int getLayoutId();
    protected abstract void init(Bundle savedInstanceState);

}
