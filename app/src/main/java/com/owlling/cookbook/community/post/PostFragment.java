package com.owlling.cookbook.community.post;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.dialog.CommentDialog;
import com.owlling.cookbook.community.model.EmptyBean;
import com.owlling.cookbook.community.model.PostBean;
import com.owlling.cookbook.community.post.bean.CreateCommentBean;
import com.owlling.cookbook.community.post.bean.UnWatchCallback;
import com.owlling.cookbook.community.post.bean.WatchCallback;
import com.owlling.cookbook.community.post.callbacks.CreateCommentCallback;
import com.owlling.cookbook.community.post.callbacks.PostCallback;
import com.owlling.cookbook.community.post.create.CreatePostActivity;
import com.owlling.cookbook.community.utils.RecyclerViewItemDecoration;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.fragment.BaseFragment;
import com.owlling.cookbook.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostFragment extends BaseFragment implements PostCallback,
        CreateCommentCallback,
        WatchCallback, UnWatchCallback {

    private static final String TAG = "PostFragment";

    @Bind(R.id.rvPost)
    RecyclerView rvPost;
    @Bind(R.id.fabPost)
    FloatingActionButton fabPost;
    @Bind(R.id.postToolbar)
    Toolbar commonToolbar;
    @Bind(R.id.sflPostList)
    SwipeRefreshLayout sflPostList;

    private PostPresenter mPresenter = new PostPresenter(this.getContext());

    private PostAdapter mAdapter;

    @Override
    protected Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    //动态页初始化页面init；加载数据；添加事件等等
    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        commonToolbar.setTitle("动态");
        loadData();//加载数据
        mAdapter = new PostAdapter(getContext());
        rvPost.setAdapter(mAdapter);
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPost.addItemDecoration(new RecyclerViewItemDecoration(16));
        //某个帖子
        mAdapter.setItemClickListener(new PostAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, final int pos) {
                Log.i(TAG, "onClick( post " + pos);
                final CommentDialog commentDialog = CommentDialog.newInst(mAdapter.getItem(pos).getNickname());
                commentDialog.setConfirmListener(new CommentDialog.ConfirmListener() {
                    @Override
                    public void onClick(String content) {
                        createComment(mAdapter.getItem(pos).getId(), content);
                    }
                });
                commentDialog.show(getFragmentManager(), "comment dialog");
            }
        });
        //被点赞；
        mAdapter.setOnLikeClickListener(new PostAdapter.OnLikeClickListener() {
            @Override
            public void onClick(int pos) {

                mPresenter.like(mAdapter.getItem(pos).getId());
            }
        });

        mAdapter.setWatchListener(new PostAdapter.OnWatchListener() {
            @Override
            public void onClick(int pos, boolean isWatch) {
                if (isWatch) {
                    mPresenter.watch(PostFragment.this, mAdapter.getItem(pos).getAuthorId(),
                            mAdapter.getItem(pos).getId());
                } else {
                    mPresenter.unwatch(PostFragment.this, mAdapter.getItem(pos).getAuthorId(),
                            mAdapter.getItem(pos).getId());
                }
            }
        });
        sflPostList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData() {
        mPresenter.fetchData(this);//抓取数据
    }

    public void createComment(int postId, String content) {
        mPresenter.createComment(this, postId, content);
    }

    @Override
    public void onLoadPostList(List<PostBean> postList) {
        Log.i(TAG, "onLoadPostList(size " + postList.size());
        sflPostList.setRefreshing(false);
        mAdapter.addDataSet(postList);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onFailure(String errMsg) {
        sflPostList.setRefreshing(false);
        Log.i(TAG, errMsg);
        ToastUtil.showToast(this.getContext(),"数据获取失败");
    }

    //发布按钮
    @OnClick(R.id.fabPost)
    public void onViewClicked() {
        CreatePostActivity.start(this.getContext());
    }

    @Override
    public void onCreateCommentSuccess(CreateCommentBean bean) {
        loadData();
        ToastUtil.showToast(this.getContext(), "评论成功");
    }

    @Override
    public void onCreateCommentFailure(String msg) {
        ToastUtil.showToast(this.getContext(), msg);
    }

    @Override
    public void onUnWatchSuccess(EmptyBean emptyBean, int postId) {
        ToastUtil.showToast(getContext(), "取消关注成功");
        loadData();
    }

    @Override
    public void onUnWatchFailure(String msg) {
        ToastUtil.showToast(getContext(), "取消关注失败");
    }

    //关注模块
    @Override
    public void onWatchSuccess(EmptyBean emptyBean, int postId) {
        ToastUtil.showToast(getContext(), "关注成功");
        loadData();
    }

    @Override
    public void onWatchFailure(String msg) {
        ToastUtil.showToast(getContext(), "关注失败");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
