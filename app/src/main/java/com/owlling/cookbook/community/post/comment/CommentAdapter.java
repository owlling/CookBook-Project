package com.owlling.cookbook.community.post.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.utils.TextUtil;
import com.owlling.cookbook.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;

    private List<CommentBean> dataSet = new ArrayList<>();

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public CommentBean getItem(int pos) {
        return dataSet.get(pos);
    }

    public void addDataSet(List<CommentBean> list) {
        dataSet.clear();
        notifyDataSetChanged();
        dataSet.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NormalVH(LayoutInflater.from(context).inflate(R.layout.item_comment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof NormalVH) {
            final NormalVH vh = (NormalVH) viewHolder;
            final CommentBean commentBean = dataSet.get(i);
            vh.tvNickname.setText(commentBean.getNickname());
            vh.tvCommentContent.setText(commentBean.getContent());
            GlideUtil.loadProfile(vh.ivProfile, commentBean.getAuthorId());
            vh.tvFlourTime.setText(TextUtil.composeFlourAndTime(commentBean.getFlour(), commentBean.getActTime()));
        }
    }

    static class NormalVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ivProfile)
        CircleImageView ivProfile;
        @Bind(R.id.tvNickname)
        TextView tvNickname;
        @Bind(R.id.tvFlourTime)
        TextView tvFlourTime;
        @Bind(R.id.tvCommentContent)
        TextView tvCommentContent;

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
