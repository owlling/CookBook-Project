package com.owlling.cookbook.IView;

import com.owlling.cookbook.model.entity.cookentity.CookDetail;

import java.util.ArrayList;



public interface ICookListView {

    public void onCookListUpdateRefreshSuccess(ArrayList<CookDetail> list);
    public void onCookListUpdateRefreshFaile(String msg);

    public void onCookListLoadMoreSuccess(ArrayList<CookDetail> list);
    public void onCookListLoadMoreFaile(String msg);
}
