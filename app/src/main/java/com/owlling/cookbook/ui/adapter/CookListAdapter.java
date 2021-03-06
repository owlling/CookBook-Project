package com.owlling.cookbook.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.model.entity.cookentity.CookDetail;
import com.owlling.cookbook.ui.activity.CookDetailActivity;
import com.owlling.cookbook.utils.GlideUtil;

import butterknife.Bind;

public class CookListAdapter extends BaseRecyclerAdapter<CookDetail>{

    private Activity activity;
    private GlideUtil glideUtil;

    public CookListAdapter(Activity activity){
        this.activity = activity;
        glideUtil = new GlideUtil();
    }

    @Override
    public CommonHolder<CookDetail> setViewHolder(ViewGroup parent) {
        return new CardHolder(parent.getContext(), parent);
    }

    class CardHolder extends CommonHolder<CookDetail> {

        @Bind(R.id.img_cook)
        ImageView imgvCook;

        @Bind(R.id.text_name)
        TextView textName;

        public CardHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_cook_list);
        }

        @Override
        public void bindData(final CookDetail cook) {
            textName.setText(cook.getName());

            if(cook.getRecipe() != null && cook.getRecipe().getImg() != null && (!TextUtils.isEmpty(cook.getRecipe().getImg()))){
                glideUtil.attach(imgvCook).injectImageWithNull(cook.getRecipe().getImg());
            }else{
//                Glide.clear(imgvCook);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CookDetailActivity.startActivity(activity, imgvCook, cook, true);
                }
            });
        }
    }

}
