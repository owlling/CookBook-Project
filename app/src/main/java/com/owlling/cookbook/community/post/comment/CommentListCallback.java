package com.owlling.cookbook.community.post.comment;

import java.util.List;

public interface CommentListCallback {
    void onLoadSuccess(List<CommentBean> list);
    void onLoadFailure(String msg);
}
