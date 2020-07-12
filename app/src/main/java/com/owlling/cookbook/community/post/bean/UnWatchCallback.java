package com.owlling.cookbook.community.post.bean;

import com.owlling.cookbook.community.model.EmptyBean;

public interface UnWatchCallback {
    void onUnWatchSuccess(EmptyBean emptyBean, int postId);

    void onUnWatchFailure(String msg);
}
