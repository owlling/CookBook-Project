package com.owlling.cookbook.community.mine.mycomments;

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
import com.owlling.cookbook.ui.activity.MainActivity;
import com.owlling.cookbook.utils.PrefUtil;
import com.owlling.cookbook.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyCommentActivity extends BaseActivity implements MyCommentCallback, DelCommentCallback {
    @Bind(R.id.commonToolbar)
    Toolbar commonToolbar;
    @Bind(R.id.rvMyComment)
    RecyclerView rvMyComment;

    private MyCommentAdapter adapter;
    private MyCommentPresenter presenter = new MyCommentPresenter(this);

    public static void start(Context context) {
        final Intent intent = new Intent(context, MyCommentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_comment;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    //我的评论模块
    @Override
    protected void init(Bundle savedInstanceState) {
        commonToolbar.setTitle("我的评论");
        setSupportActionBar(commonToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        adapter = new MyCommentAdapter(this);
        presenter.loadMyCommentList(this);
        rvMyComment.setAdapter(adapter);
        rvMyComment.setLayoutManager(new LinearLayoutManager(this));
        rvMyComment.addItemDecoration(new RecyclerViewItemDecoration(16));

        adapter.setOnDelPostListener(new MyCommentAdapter.OnDelCommentListener() {
            @Override
            public void onClick(final int postId, final int pos) {
                final ConfirmDialog commentDialog = ConfirmDialog.newInst("确定删除吗？");
                commentDialog.setConfirmListener(new ConfirmDialog.ConfirmListener() {
                    @Override
                    public void onClick() {
                        presenter.delComment(MyCommentActivity.this, postId, pos);
                    }
                });
                commentDialog.show(getSupportFragmentManager(), "comment dialog");
            }
        });
    }

    @Override
    public void onLoadMyCommentList(List<MyCommentBean> list) {
        adapter.addDataSet(list);
    }

    @Override
    public void onLoadMyCommentListFailure(String msg) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
