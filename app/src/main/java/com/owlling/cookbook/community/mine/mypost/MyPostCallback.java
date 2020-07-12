package com.owlling.cookbook.community.mine.mypost;

import java.util.List;

public interface MyPostCallback {
    void onLoadMyPostList(List<MyPostBean> list);

    void onLoadMyPostListFailure(String msg);
}
