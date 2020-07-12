package com.owlling.cookbook.community.user.userdetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.post.detail.PostDetailActivity;
import com.owlling.cookbook.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public void setUid(int uid) {
        this.uid = uid;
    }

    private int uid;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String nickname;

    private List<UserDetailBean.PrePostsBean> dataSet = new ArrayList<>();

    public UserDetailAdapter(Context context) {
        this.context = context;
    }

    public UserDetailBean.PrePostsBean getItem(int pos) {
        return dataSet.get(pos);
    }

    public void addDataSet(List<UserDetailBean.PrePostsBean> list) {
        dataSet.clear();
        notifyDataSetChanged();
        dataSet.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NormalVH(LayoutInflater.from(context).inflate(R.layout.item_user_detail, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof NormalVH) {
            final NormalVH vh = (NormalVH) viewHolder;
            final UserDetailBean.PrePostsBean prePostEntity = dataSet.get(i);
            vh.tvContent.setText(prePostEntity.getContent());
            GlideUtil.loadProfile(vh.ivProfile, uid);
            GlideUtil.loadPostImg(vh.ivImg, prePostEntity.getPostImg());
            vh.tvCommentCount.setText(String.valueOf(prePostEntity.getCommentCount()));
            vh.tvLikedCount.setText(String.valueOf(prePostEntity.getLikedCount()));
            vh.tvAuthor.setText(nickname);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostDetailActivity.start(context, prePostEntity.getPostId());
                }
            });
        }
    }

    static class NormalVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ivProfile)
        CircleImageView ivProfile;
        @Bind(R.id.tvAuthor)
        TextView tvAuthor;
        @Bind(R.id.tvPostTime)
        TextView tvPostTime;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.ivImg)
        ImageView ivImg;
        @Bind(R.id.tvCommentCount)
        TextView tvCommentCount;
        @Bind(R.id.tvLikedCount)
        TextView tvLikedCount;

        NormalVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
