package com.owlling.cookbook.community.post;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.model.PostBean;
import com.owlling.cookbook.community.post.comment.CommentListActivity;
import com.owlling.cookbook.community.post.detail.PostDetailActivity;
import com.owlling.cookbook.community.user.userdetail.UserDetailActivity;
import com.owlling.cookbook.community.utils.TextUtil;
import com.owlling.cookbook.community.utils.TimeUtil;
import com.owlling.cookbook.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "PostAdapter";
    private List<PostBean> dataSet = new ArrayList<>();

    private Context context;

    PostAdapter(Context context) {
        this.context = context;
    }

    public PostBean getItem(int pos) {
        return dataSet.get(pos);
    }

    public void addDataSet(List<PostBean> list) {
        dataSet.clear();//清空旧数据
        notifyDataSetChanged();//刷新数据
        dataSet.addAll(list);//添加新数据；调用onbindview
        notifyDataSetChanged();//刷新页面
    }

    public void updateWatchStatus(int postId, boolean isWatched) {
        for (PostBean post : dataSet) {
            if (post.getId() == postId) {
                post.setIsWatched(isWatched);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NormalVH(LayoutInflater.from(context).inflate(R.layout.item_post_normal, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof NormalVH) {
            final NormalVH vh = (NormalVH) viewHolder;
            final PostBean entity = dataSet.get(i);
            vh.tvContent.setText(entity.getContent());
            vh.tvAuthor.setText(entity.getNickname());
            GlideUtil.loadProfile(vh.ivProfile, entity.getAuthorId());
            GlideUtil.loadPostImg(vh.ivImg, entity.getPostImg());
            if (entity.getComment() != null) {
                final List<PostBean.PreCommentBean> comments = entity.getComment();
                vh.cardComment.setVisibility(entity.getCommentCount() == 0 ? View.GONE : View.VISIBLE);
                if (entity.getCommentCount() == 1) {
                    vh.tvComment1.setVisibility(View.VISIBLE);
                    vh.tvComment1.setText(Html.fromHtml(TextUtil.composeComment(comments.get(0).getNickname(), comments.get(0).getContent())));
                    vh.tvComment2.setVisibility(View.GONE);
                    vh.tvCommentMore.setVisibility(View.GONE);
                }
                if (entity.getCommentCount() > 2) {
                    vh.tvComment1.setVisibility(View.VISIBLE);
                    vh.tvComment2.setVisibility(View.VISIBLE);
                    vh.tvComment1.setText(Html.fromHtml(TextUtil.composeComment(comments.get(0).getNickname(), comments.get(0).getContent())));
                    vh.tvComment2.setText(Html.fromHtml(TextUtil.composeComment(comments.get(1).getNickname(), comments.get(1).getContent())));
                    vh.tvCommentMore.setVisibility(View.VISIBLE);
                    vh.tvCommentMore.setText(TextUtil.getMoreCommentStr(entity.getCommentCount()));
                } else if (entity.getCommentCount() == 2) {
                    vh.tvComment1.setVisibility(View.VISIBLE);
                    vh.tvComment2.setVisibility(View.VISIBLE);
                    vh.tvComment1.setText(Html.fromHtml(TextUtil.composeComment(comments.get(0).getNickname(), comments.get(0).getContent())));
                    vh.tvComment2.setText(Html.fromHtml(TextUtil.composeComment(comments.get(1).getNickname(), comments.get(1).getContent())));
                    vh.tvCommentMore.setVisibility(View.GONE);
                }
            }

            //点赞数+1
            vh.tvLikedCount.setText(String.valueOf(entity.getLikedCount()));
            vh.tvLikedCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    entity.isLiked = true;
                    entity.setLikedCount(entity.getLikedCount() + 1);
                    int tvLikeColor = R.color.colorPrimary;
                    vh.tvLikedCount.setText(String.valueOf(entity.getLikedCount()));
                    setTextViewDrawableColor(vh.tvLikedCount, tvLikeColor);
                    likeClickListener.onClick(i);
                }
            });
            // 点赞改变颜色
            int tvLikeColor = entity.isLiked ? R.color.colorPrimary : R.color.black_alpha_12;
            setTextViewDrawableColor(vh.tvLikedCount, tvLikeColor);

            vh.tvCommentCount.setText(String.valueOf(entity.getCommentCount()));
            vh.tvPostTime.setText(TimeUtil.getOffsetTime(entity.getPostTime()));
            vh.tvCommentCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentDialog(vh.itemView, i);
                }
            });

            vh.tvCommentMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMoreCommentClick(entity.getId());
                }
            });
            Log.i(TAG, "watched " + entity.getIsWatched());

            //绑定事件Listener监听；关注改变颜色
            if (entity.getIsWatched()) {
                vh.tvWatch.setBackgroundResource(R.drawable.bg_like);
                vh.tvWatch.setText("已关注");
            } else {
                vh.tvWatch.setBackgroundResource(R.drawable.bg_unlike);
                vh.tvWatch.setText("关注");
            }

            vh.tvWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchListener.onClick(i, !entity.getIsWatched());
                }
            });

            vh.llUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProfileClick(entity.getAuthorId());
                }
            });

            vh.ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProfileClick(entity.getAuthorId());
                }
            });

            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostDetailActivity.start(context, entity.getId());
                }
            });
        }
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        textView.setTextColor(ContextCompat.getColor(context, color));
        Drawable d = textView.getCompoundDrawablesRelative()[0];
        if (d != null){
            d = d.mutate();
            d.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
        }
    }

    private void onProfileClick(int authorId) {
        UserDetailActivity.start(context, authorId);
    }

    private void onMoreCommentClick(int postId) {
        CommentListActivity.start(context, postId);
    }

    public interface OnLikeClickListener {
        void onClick(int pos);
    }

    public interface OnWatchListener {
        void onClick(int pos, boolean isWatch);
    }

    private OnWatchListener watchListener;

    public void setWatchListener(OnWatchListener l) {
        this.watchListener = l;
    }

    private OnLikeClickListener likeClickListener;

    public void setOnLikeClickListener(OnLikeClickListener l) {
        this.likeClickListener = l;
    }

    private void commentDialog(View view, int pos) {
        if (itemClickListener != null) {
            itemClickListener.onClick(view, pos);
        }
    }

    public void setItemClickListener(ItemClickListener l) {
        this.itemClickListener = l;
    }

    interface ItemClickListener {
        void onClick(View view, int pos);
    }

    private ItemClickListener itemClickListener;

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class NormalVH extends RecyclerView.ViewHolder {

        @Bind(R.id.tvAuthor)
        TextView tvAuthor;
        @Bind(R.id.tvPostTime)
        TextView tvPostTime;
        @Bind(R.id.tvWatch)
        TextView tvWatch;
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
        @Bind(R.id.llUser)
        LinearLayout llUser;

        @Bind(R.id.ivProfile)
        CircleImageView ivProfile;
        @Bind(R.id.cardComment)
        CardView cardComment;

        NormalVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
