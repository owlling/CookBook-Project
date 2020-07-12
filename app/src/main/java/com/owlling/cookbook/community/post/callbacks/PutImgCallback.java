package com.owlling.cookbook.community.post.callbacks;

import com.owlling.cookbook.community.post.bean.PostImgBean;

public interface PutImgCallback {
    void onPutSuccess(PostImgBean bean);
    void onPutFailure(String msg);
}
