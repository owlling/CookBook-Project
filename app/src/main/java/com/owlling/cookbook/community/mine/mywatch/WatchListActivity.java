package com.owlling.cookbook.community.mine.mywatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.model.EmptyBean;
import com.owlling.cookbook.community.post.PostAdapter;
import com.owlling.cookbook.community.post.bean.UnWatchCallback;
import com.owlling.cookbook.community.utils.RecyclerViewItemDecoration;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;

public class WatchListActivity extends BaseActivity implements WatchListCallback, UnWatchCallback {
    private static final String TAG = "WatchListActivity";
    @Bind(R.id.commonToolbar)
    Toolbar commonToolbar;
    @Bind(R.id.rvWatch)
    RecyclerView rvWatch;

    private WatchPresenter presenter = new WatchPresenter(this);
    private WatchAdapter adapter;

    public static void start(Context context) {
        final Intent intent = new Intent(context, WatchListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        if (presenter != null) {
            presenter.loadWatchList(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_watch_list;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    //我的关注页面
    @Override
    protected void init(Bundle savedInstanceState) {
        commonToolbar.setTitle("我的关注");
        setSupportActionBar(commonToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        presenter.loadWatchList(this);

        adapter = new WatchAdapter(this);
        rvWatch.setLayoutManager(new LinearLayoutManager(this));
        rvWatch.addItemDecoration(new RecyclerViewItemDecoration(16));
        rvWatch.setAdapter(adapter);
        adapter.setUnwatchListener(new PostAdapter.OnWatchListener() {
            @Override
            public void onClick(int pos, boolean isWatch) {
                presenter.unwatch(WatchListActivity.this,
                        adapter.getItem(pos).getWatchedId(), pos);
            }
        });
    }

    @Override
    public void onLoad(List<WatchBean> list) {
        adapter.addDataSet(list);
    }

    @Override
    public void onLoadFailure(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onUnWatchSuccess(EmptyBean emptyBean, int position) {
        adapter.removeItemView(position);
    }

    @Override
    public void onUnWatchFailure(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
