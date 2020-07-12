package com.owlling.cookbook.community.mine.myfans;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.utils.RecyclerViewItemDecoration;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;

public class FansListActivity extends BaseActivity implements FansListCallback {
    private static final String TAG = "FansListActivity";
    @Bind(R.id.commonToolbar)
    Toolbar commonToolbar;
    @Bind(R.id.rvFans)
    RecyclerView rvFans;

    private FansPresenter presenter = new FansPresenter(this);
    private FansAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        if (presenter != null) {
            presenter.loadFansList(this);
        }
    }

    public static void start(Context context) {
        final Intent intent = new Intent(context, FansListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fan_list;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    //我的粉丝模块
    @Override
    protected void init(Bundle savedInstanceState) {
        commonToolbar.setTitle("我的粉丝");
        setSupportActionBar(commonToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        presenter.loadFansList(this);

        adapter = new FansAdapter(this);
        rvFans.setLayoutManager(new LinearLayoutManager(this));
        rvFans.addItemDecoration(new RecyclerViewItemDecoration(16));
        rvFans.setAdapter(adapter);
    }

    @Override
    public void onLoad(List<FansBean> list) {
        adapter.addDataSet(list);
    }

    @Override
    public void onLoadFailure(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
