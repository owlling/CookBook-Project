package com.owlling.cookbook.community.post.bean;

import com.owlling.cookbook.community.model.EmptyBean;

public interface WatchCallback {
    void onWatchSuccess(EmptyBean emptyBean, int postId);

    void onWatchFailure(String msg);
}
