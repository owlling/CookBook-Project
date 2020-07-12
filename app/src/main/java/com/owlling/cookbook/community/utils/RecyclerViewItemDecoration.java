package com.owlling.cookbook.community.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {


    private int space;

    public RecyclerViewItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view) != 0)
            outRect.top = space;
    }
}