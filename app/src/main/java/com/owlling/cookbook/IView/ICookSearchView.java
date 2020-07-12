package com.owlling.cookbook.IView;

import com.owlling.cookbook.model.entity.cookentity.CookDetail;

import java.util.ArrayList;



public interface ICookSearchView {

    public void onCookSearchSuccess(ArrayList<CookDetail> list, int totalPages);
    public void onCookSearchFaile(String msg);
    public void onCookSearchEmpty();

}
