package com.owlling.cookbook.community.mine.myfans;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.user.userdetail.UserDetailActivity;
import com.owlling.cookbook.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FansAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<FansBean> dataSet = new ArrayList<>();

    public FansAdapter(Context context) {
        this.context = context;
    }

    public FansBean getItem(int pos) {
        return dataSet.get(pos);
    }

    public void addDataSet(List<FansBean> list) {
        dataSet.clear();
        notifyDataSetChanged();
        dataSet.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NormalVH(LayoutInflater.from(context).inflate(R.layout.item_funs, viewGroup, false));
    }

    //显示粉丝数
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof NormalVH) {
            final NormalVH vh = (NormalVH) viewHolder;
            final FansBean fansBean = dataSet.get(i);
            vh.tvNickname.setText(fansBean.getNickname());
            vh.tvWatcherCount.setText("粉丝数：" + fansBean.getCFans());
            GlideUtil.loadProfile(vh.ivProfile, fansBean.getWatcherId());
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDetailActivity.start(context, fansBean.getWatcherId());
                }
            });
        }
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
