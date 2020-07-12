package com.owlling.cookbook.community.post.comment;

import android.content.Context;

import com.owlling.cookbook.community.net.BaseSubscriber;
import com.owlling.cookbook.community.post.PostPresenter;
import com.owlling.cookbook.model.respository.CookRepository;

import java.util.List;

public class CommentPresenter extends PostPresenter {

    public CommentPresenter(Context context) {
        super(context);
        this.context = context;
    }

    public void loadCommentList(final CommentListCallback callback, int postId) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().getCommentList(postId),
                new BaseSubscriber<List<CommentBean>>() {

                    @Override
                    public void next(List<CommentBean> data) {
                        callback.onLoadSuccess(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onLoadFailure(msg);
                    }
                }
        );
    }

    @Override
    public void destroy() {

    }
}
