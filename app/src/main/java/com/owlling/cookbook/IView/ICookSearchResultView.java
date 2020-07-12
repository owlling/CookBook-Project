package com.owlling.cookbook.IView;

import com.owlling.cookbook.model.entity.cookentity.CookDetail;

import java.util.ArrayList;



public interface ICookSearchResultView {

    public void onCookSearchLoadMoreSuccess(ArrayList<CookDetail> list);
    public void onCookSearchLoadMoreFaile(String msg);
    public void onCookSearchLoadMoreNoData();

}
