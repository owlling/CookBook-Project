package com.owlling.cookbook.community.post.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.dialog.CommentDialog;
import com.owlling.cookbook.community.model.PostBean;
import com.owlling.cookbook.community.post.PostPresenter;
import com.owlling.cookbook.community.post.bean.CreateCommentBean;
import com.owlling.cookbook.community.post.callbacks.CreateCommentCallback;
import com.owlling.cookbook.community.post.callbacks.PostDetailCallback;
import com.owlling.cookbook.community.post.comment.CommentListActivity;
import com.owlling.cookbook.community.user.userdetail.UserDetailActivity;
import com.owlling.cookbook.community.utils.TextUtil;
import com.owlling.cookbook.community.utils.TimeUtil;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.GlideUtil;
import com.owlling.cookbook.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailActivity extends BaseActivity implements PostDetailCallback, CreateCommentCallback {

    private static final String EXT_POST_ID = "ext_post_id";
    private static final String TAG = "PostDetailActivity";
    @Bind(R.id.ivProfile)
    CircleImageView ivProfile;
    @Bind(R.id.tvAuthor)
    TextView tvAuthor;
    @Bind(R.id.tvPostTime)
    TextView tvPostTime;
    @Bind(R.id.llUser)
    LinearLayout llUser;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.ivImg)
    ImageView ivImg;
    @Bind(R.id.tvCommentCount)
    TextView tvCommentCount;
    @Bind(R.id.tvLikedCount)
    TextView tvLikedCount;
    @Bind(R.id.tvComment1)
    TextView tvComment1;
    @Bind(R.id.tvComment2)
    TextView tvComment2;
    @Bind(R.id.tvCommentMore)
    TextView tvCommentMore;
    @Bind(R.id.cardComment)
    CardView cardComment;
    @Bind(R.id.commonToolbar)
    Toolbar commonToolbar;

    private PostPresenter presenter = new PostPresenter(this);

    public static void start(Context context, int postId) {
        if (postId <= 0) {
            return;
        }
        final Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(EXT_POST_ID, postId);
        context.startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setSupportActionBar(commonToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadPost();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private int pid;

    private void loadPost() {
        pid = getIntent().getIntExtra(EXT_POST_ID, -1);
        if (pid != -1) {
            presenter.postDetail(this, pid);
        } else {
            onFailure("获取动态详情失败！！");
        }
    }

    private void setupCommentView(PostBean post) {
        if (post.getComment() != null) {
            final List<PostBean.PreCommentBean> comments = post.getComment();
            cardComment.setVisibility(post.getCommentCount() == 0 ? View.GONE : View.VISIBLE);
            if (post.getCommentCount() == 1) {
                tvComment1.setVisibility(View.VISIBLE);
                tvComment1.setText(Html.fromHtml(TextUtil.composeComment(comments.get(0).getNickname(), comments.get(0).getContent())));
                tvComment2.setVisibility(View.GONE);
                tvCommentMore.setVisibility(View.GONE);
            }
            if (post.getCommentCount() > 2) {
                tvComment1.setVisibility(View.VISIBLE);
                tvComment2.setVisibility(View.VISIBLE);
                tvComment1.setText(Html.fromHtml(TextUtil.composeComment(comments.get(0).getNickname(), comments.get(0).getContent())));
                tvComment2.setText(Html.fromHtml(TextUtil.composeComment(comments.get(1).getNickname(), comments.get(1).getContent())));
                tvCommentMore.setVisibility(View.VISIBLE);
                tvCommentMore.setText(TextUtil.getMoreCommentStr(post.getCommentCount()));
            } else if (post.getCommentCount() == 2) {
                tvComment1.setVisibility(View.VISIBLE);
                tvComment2.setVisibility(View.VISIBLE);
                tvComment1.setText(Html.fromHtml(TextUtil.composeComment(comments.get(0).getNickname(), comments.get(0).getContent())));
                tvComment2.setText(Html.fromHtml(TextUtil.composeComment(comments.get(1).getNickname(), comments.get(1).getContent())));
                tvCommentMore.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoadPost(final PostBean post) {
        setupCommentView(post);

        tvAuthor.setText(post.getNickname());
        tvContent.setText(post.getContent());
        GlideUtil.loadProfile(ivProfile, post.getAuthorId());
        GlideUtil.loadPostImg(ivImg, post.getPostImg());
        tvLikedCount.setText(String.valueOf(post.getLikedCount()));
        tvLikedCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.isLiked = true;
                post.setLikedCount(post.getLikedCount() + 1);
                int tvLikeColor = R.color.colorPrimary;
                tvLikedCount.setText(String.valueOf(post.getLikedCount()));
                setTextViewDrawableColor(tvLikedCount, tvLikeColor);
                presenter.like(pid);
            }
        });
        // tvLikedColor
        int tvLikeColor = post.isLiked ? R.color.colorPrimary : R.color.black_alpha_12;
        setTextViewDrawableColor(tvLikedCount, tvLikeColor);

        tvCommentCount.setText(String.valueOf(post.getCommentCount()));
        tvPostTime.setText(TimeUtil.getOffsetTime(post.getPostTime()));
        tvCommentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDialog(post);
            }
        });

        tvCommentMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMoreCommentClick();
            }
        });

        llUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileClick(post.getAuthorId());
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileClick(post.getAuthorId());
            }
        });
    }

    private void onProfileClick(int authorId) {
        UserDetailActivity.start(context, authorId);
    }

    private void onMoreCommentClick() {
        CommentListActivity.start(context, pid);
    }

    private void commentDialog(PostBean post) {
        final CommentDialog commentDialog = CommentDialog.newInst(post.getNickname());
        commentDialog.setConfirmListener(new CommentDialog.ConfirmListener() {
            @Override
            public void onClick(String content) {
                createComment(pid, content);
            }
        });
        commentDialog.show(getSupportFragmentManager(), "comment dialog");
    }

    public void createComment(int postId, String content) {
        presenter.createComment(this, postId, content);
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        textView.setTextColor(ContextCompat.getColor(context, color));
        Drawable d = textView.getCompoundDrawablesRelative()[0];
        if (d != null) {
            d = d.mutate();
            d.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
        }
    }

    @Override
    public void onFailure(String errMsg) {
        ToastUtil.showToast(context, errMsg);
    }

    @Override
    public void onCreateCommentSuccess(CreateCommentBean bean) {
        loadPost();
        ToastUtil.showToast(this.getContext(), "评论成功");
    }

    @Override
    public void onCreateCommentFailure(String msg) {
        ToastUtil.showToast(this.getContext(), msg);
    }
}
