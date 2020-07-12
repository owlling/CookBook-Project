package com.owlling.cookbook.community.post.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.post.bean.CreateCommentBean;
import com.owlling.cookbook.community.post.callbacks.CreateCommentCallback;
import com.owlling.cookbook.community.utils.RecyclerViewItemDecoration;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;

public class CommentListActivity extends BaseActivity implements CommentListCallback, CreateCommentCallback {

    private static final String INTENT_EXT_POST_ID = "intent_ext_post_id";
    private static final String TAG = "CommentListActivity";
    @Bind(R.id.commonToolbar)
    Toolbar commonToolbar;
    @Bind(R.id.rvComment)
    RecyclerView rvComment;

    private CommentPresenter presenter = new CommentPresenter(this);
    private CommentAdapter adapter;
    private int postId;

    public static void start(Context context, int postId) {
        final Intent intent = new Intent(context, CommentListActivity.class);
        intent.putExtra(INTENT_EXT_POST_ID, postId);
        Log.i(TAG, "post id " + postId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment_list;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    //回复列表模块
    @Override
    protected void init(Bundle savedInstanceState) {
        postId = getIntent().getIntExtra(INTENT_EXT_POST_ID, 0);
        commonToolbar.setTitle("回复列表");
        setSupportActionBar(commonToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        presenter.loadCommentList(this, postId);
        adapter = new CommentAdapter(this);
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.addItemDecoration(new RecyclerViewItemDecoration(5));
        rvComment.setAdapter(adapter);

    }

    @Override
    public void onLoadSuccess(List<CommentBean> list) {
        adapter.addDataSet(list);
    }

    @Override
    public void onLoadFailure(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onCreateCommentSuccess(CreateCommentBean bean) {
        ToastUtil.showToast(this, "评论成功");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateCommentFailure(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
