package com.owlling.cookbook.community.mine.mywatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.post.PostAdapter;
import com.owlling.cookbook.community.user.userdetail.UserDetailActivity;
import com.owlling.cookbook.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class WatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<WatchBean> dataSet = new ArrayList<>();

    public WatchAdapter(Context context) {
        this.context = context;
    }

    public WatchBean getItem(int pos) {
        return dataSet.get(pos);
    }

    public void addDataSet(List<WatchBean> list) {
        dataSet.clear();
        notifyDataSetChanged();
        dataSet.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NormalVH(LayoutInflater.from(context).inflate(R.layout.item_watch, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof NormalVH) {
            final NormalVH vh = (NormalVH) viewHolder;
            final WatchBean watchBean = dataSet.get(i);
            vh.tvNickname.setText(watchBean.getNickname());
            vh.tvWatcherCount.setText("粉丝数：" + watchBean.getWatcherCount());
            vh.tvUnwatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unwatchListener.onClick(i, false);
                }
            });
            GlideUtil.loadProfile(vh.ivProfile, watchBean.getWatchedId());
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDetailActivity.start(context, watchBean.getWatchedId());
                }
            });
        }
    }

    private PostAdapter.OnWatchListener unwatchListener;

    public void setUnwatchListener(PostAdapter.OnWatchListener unwatchListener) {
        this.unwatchListener = unwatchListener;
    }

    public void removeItemView(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    static class NormalVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ivProfile)
        CircleImageView ivProfile;
        @Bind(R.id.tvNickname)
        TextView tvNickname;
        @Bind(R.id.tvWatcherCount)
        TextView tvWatcherCount;
        @Bind(R.id.tvUnwatch)
        TextView tvUnwatch;

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
