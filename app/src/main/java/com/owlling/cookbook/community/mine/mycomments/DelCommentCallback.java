package com.owlling.cookbook.community.mine.mycomments;

public interface DelCommentCallback {

    void onDelSuccess(int pos);

    void onDelFailure(String msg);
}
