package com.owlling.cookbook.community.mine.mypost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.dialog.ConfirmDialog;
import com.owlling.cookbook.community.utils.RecyclerViewItemDecoration;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;

public class MyPostActivity extends BaseActivity implements MyPostCallback, DelPostCallback {
    @Bind(R.id.commonToolbar)
    Toolbar commonToolbar;
    @Bind(R.id.rvMyPost)
    RecyclerView rvMyPost;

    private MyPostAdapter adapter;
    private MyPostPresenter presenter = new MyPostPresenter(this);

    public static void start(Context context) {
        final Intent intent = new Intent(context, MyPostActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_post;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    //我的动态模块
    @Override
    protected void init(Bundle savedInstanceState) {
        commonToolbar.setTitle("我的动态");
        setSupportActionBar(commonToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        adapter = new MyPostAdapter(this);
        presenter.loadMyPostList(this);
        rvMyPost.setAdapter(adapter);
        rvMyPost.setLayoutManager(new LinearLayoutManager(this));
        rvMyPost.addItemDecoration(new RecyclerViewItemDecoration(16));

        adapter.setOnDelPostListener(new MyPostAdapter.OnDelPostListener() {
            @Override
            public void onClick(final int postId, final int pos) {
                final ConfirmDialog commentDialog = ConfirmDialog.newInst("确定删除吗？");
                commentDialog.setConfirmListener(new ConfirmDialog.ConfirmListener() {
                    @Override
                    public void onClick() {
                        presenter.delPost(MyPostActivity.this, postId, pos);
                    }
                });
                commentDialog.show(getSupportFragmentManager(), "comment dialog");
            }
        });
    }

    @Override
    public void onLoadMyPostList(List<MyPostBean> list) {
        adapter.addDataSet(list);
    }

    @Override
    public void onLoadMyPostListFailure(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onDelSuccess(int pos) {
        ToastUtil.showToast(this, "删除成功");
        adapter.removeItemView(pos);
    }

    @Override
    public void onDelFailure(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
