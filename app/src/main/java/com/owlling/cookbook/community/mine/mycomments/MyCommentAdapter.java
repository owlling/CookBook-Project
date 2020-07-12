package com.owlling.cookbook.community.mine.mycomments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.post.detail.PostDetailActivity;
import com.owlling.cookbook.community.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<MyCommentBean> dataSet = new ArrayList<>();

    public MyCommentAdapter(Context context) {
        this.context = context;
    }

    public MyCommentBean getItem(int pos) {
        return dataSet.get(pos);
    }

    public void addDataSet(List<MyCommentBean> list) {
        dataSet.clear();
        notifyDataSetChanged();
        dataSet.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NormalVH(LayoutInflater.from(context).inflate(R.layout.item_my_comment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof NormalVH) {
            final NormalVH vh = (NormalVH) viewHolder;
            final MyCommentBean myCommentBean = dataSet.get(i);
            vh.tvCommentContent.setText(myCommentBean.getCommentContent());
            vh.tvPostContent.setText("# " + myCommentBean.getPostContent());
            vh.tvTime.setText(TimeUtil.toTimeStr(myCommentBean.getActTime()));
            vh.tvDelComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delCommentListener.onClick(myCommentBean.getCommentId(), i);
                }
            });
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostDetailActivity.start(context, myCommentBean.getId());
                }
            });

        }
    }

    public interface OnDelCommentListener {
        void onClick(int postId, int pos);
    }

    private OnDelCommentListener delCommentListener;

    public void setOnDelPostListener(OnDelCommentListener listener) {
        this.delCommentListener = listener;
    }

    public void removeItemView(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    static class NormalVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tvCommentContent)
        TextView tvCommentContent;
        @Bind(R.id.tvPostContent)
        TextView tvPostContent;
        @Bind(R.id.tvTime)
        TextView tvTime;
        @Bind(R.id.tvDelComment)
        TextView tvDelComment;

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
