package com.owlling.cookbook.community.post.callbacks;

import com.owlling.cookbook.community.post.bean.CreatePostBean;

public interface CreatePostCallback {
    void onCreatePostSuccess(CreatePostBean bean);
    void onCreatePostFailure(String msg);
}
