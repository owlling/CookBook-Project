package com.owlling.cookbook.community.post.callbacks;

import com.owlling.cookbook.community.model.PostBean;

import java.util.List;

public interface PostCallback {

    void onLoadPostList(List<PostBean> postList);

    void onFailure(String errMsg);

}
