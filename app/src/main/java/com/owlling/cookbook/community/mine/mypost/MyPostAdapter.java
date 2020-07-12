package com.owlling.cookbook.community.mine.mypost;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.post.detail.PostDetailActivity;
import com.owlling.cookbook.community.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;

    private List<MyPostBean> dataSet = new ArrayList<>();

    public MyPostAdapter(Context context) {
        this.context = context;
    }

    public MyPostBean getItem(int pos) {
        return dataSet.get(pos);
    }

    public void addDataSet(List<MyPostBean> list) {
        dataSet.clear();
        notifyDataSetChanged();
        dataSet.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NormalVH(LayoutInflater.from(context).inflate(R.layout.item_my_post, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof NormalVH) {
            final NormalVH vh = (NormalVH) viewHolder;
            final MyPostBean myPostBean = dataSet.get(i);
            vh.tvContent.setText(myPostBean.getContent());
            vh.tvCount.setText(TextUtil.composeLikeAndComment(myPostBean.getLikedCount(), myPostBean.getCommentCount()));
            vh.tvDelPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delPostListener.onClick(myPostBean.getPostId(), i);
                }
            });
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostDetailActivity.start(context, myPostBean.getPostId());
                }
            });
        }
    }

    public interface OnDelPostListener {
        void onClick(int postId, int pos);
    }

    private OnDelPostListener delPostListener;

    public void setOnDelPostListener(OnDelPostListener listener) {
        this.delPostListener = listener;
    }

    public void removeItemView(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    static class NormalVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.tvCount)
        TextView tvCount;
        @Bind(R.id.tvDelPost)
        TextView tvDelPost;

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
