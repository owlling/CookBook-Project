package com.owlling.cookbook.community.user.userdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.model.EmptyBean;
import com.owlling.cookbook.community.post.bean.UnWatchCallback;
import com.owlling.cookbook.community.post.bean.WatchCallback;
import com.owlling.cookbook.community.utils.RecyclerViewItemDecoration;
import com.owlling.cookbook.community.utils.TextUtil;
import com.owlling.cookbook.community.mine.mywatch.WatchPresenter;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.GlideUtil;
import com.owlling.cookbook.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends BaseActivity implements UserDetailCallback, UnWatchCallback, WatchCallback {

    private static final String INTENT_EXT_UID = "intent_ext_uid";
    private static final String TAG = "UserDetailAct";
    @Bind(R.id.commonToolbar)
    Toolbar commonToolbar;
    @Bind(R.id.ivProfile)
    CircleImageView ivProfile;
    @Bind(R.id.tvNickname)
    TextView tvNickname;
    @Bind(R.id.tvLikedAndWatchCount)
    TextView tvLikedAndWatchCount;
    @Bind(R.id.rvPrePost)
    RecyclerView rvPrePost;
    @Bind(R.id.tvWatch)
    TextView tvWatch;

    private UserDetailPresenter presenter = new UserDetailPresenter(this);
    private WatchPresenter watchPresenter = new WatchPresenter(this);
    private UserDetailAdapter adapter;
    private int uid;

    public static void start(Context context, int uid) {
        final Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra(INTENT_EXT_UID, uid);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_detail;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        uid = getIntent().getIntExtra(INTENT_EXT_UID, 0);
        commonToolbar.setTitle("");
        setSupportActionBar(commonToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        presenter.loadUserDetail(this, uid);
        adapter = new UserDetailAdapter(this);
        rvPrePost.setLayoutManager(new LinearLayoutManager(this));
        rvPrePost.addItemDecoration(new RecyclerViewItemDecoration(5));
        rvPrePost.setAdapter(adapter);
    }

    @Override
    public void onLoadDetail(UserDetailBean userDetail) {
        Log.i(TAG, userDetail.toString());
        currUserDetail = userDetail;
        setupTvWatch(userDetail.getIsWatched());
        tvNickname.setText(userDetail.getNickname());
        tvLikedAndWatchCount.setText(TextUtil.composeLikedAndWatcherCount(userDetail.getTotalLiked(), userDetail.getTotalWatcher()));
        GlideUtil.loadProfile(ivProfile, uid);
        adapter.setUid(uid);
        adapter.setNickname(userDetail.getNickname());
        adapter.addDataSet(userDetail.getPosts());
    }

    private UserDetailBean currUserDetail = null;

    private void setupTvWatch(boolean isWatched) {
        final int bg = isWatched ? R.drawable.bg_like_transition : R.drawable.bg_like;
        final String watchText = isWatched ? "已关注" : "关注";
        tvWatch.setText(watchText);
        tvWatch.setBackgroundResource(bg);
    }

    @Override
    public void onLoadDetailFailure(String msg) {
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

    @OnClick(R.id.tvWatch)
    public void onViewClicked() {
        Log.i(TAG, "on watch click " + currUserDetail.toString());
        if (currUserDetail == null) {
            return;
        }
        if (currUserDetail.getIsWatched()) {
            watchPresenter.unwatch(this, currUserDetail.getId(), -1);
        } else {
            watchPresenter.watch(this, currUserDetail.getId(), -1);
        }
    }

    @Override
    public void onUnWatchSuccess(EmptyBean emptyBean, int postId) {
        currUserDetail.setIsWatched(false);
        setupTvWatch(false);
    }

    @Override
    public void onUnWatchFailure(String msg) {
        ToastUtil.showToast(context, msg);
    }

    @Override
    public void onWatchSuccess(EmptyBean emptyBean, int postId) {
        currUserDetail.setIsWatched(true);
        setupTvWatch(true);
    }

    @Override
    public void onWatchFailure(String msg) {
        ToastUtil.showToast(context, msg);
    }
}
