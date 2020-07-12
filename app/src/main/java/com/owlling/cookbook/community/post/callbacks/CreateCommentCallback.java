package com.owlling.cookbook.community.post.callbacks;

import com.owlling.cookbook.community.post.bean.CreateCommentBean;

public interface CreateCommentCallback {
    void onCreateCommentSuccess(CreateCommentBean bean);
    void onCreateCommentFailure(String msg);
}
