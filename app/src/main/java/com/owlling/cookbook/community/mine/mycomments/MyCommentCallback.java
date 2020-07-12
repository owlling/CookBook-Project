package com.owlling.cookbook.community.mine.mycomments;

import java.util.List;

public interface MyCommentCallback {
    void onLoadMyCommentList(List<MyCommentBean> list);

    void onLoadMyCommentListFailure(String msg);
}
