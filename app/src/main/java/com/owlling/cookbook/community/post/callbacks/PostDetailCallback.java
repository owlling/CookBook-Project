package com.owlling.cookbook.community.post.callbacks;

import com.owlling.cookbook.community.model.PostBean;

public interface PostDetailCallback {

    void onLoadPost(PostBean post);

    void onFailure(String errMsg);

}
