package com.owlling.cookbook.community.mine.mypost;

public interface DelPostCallback {

    void onDelSuccess(int pos);

    void onDelFailure(String msg);
}
