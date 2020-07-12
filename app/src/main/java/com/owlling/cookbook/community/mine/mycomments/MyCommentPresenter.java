package com.owlling.cookbook.community.mine.mycomments;

import android.content.Context;

import com.owlling.cookbook.community.model.EmptyBean;
import com.owlling.cookbook.community.net.BaseSubscriber;
import com.owlling.cookbook.model.respository.CookRepository;
import com.owlling.cookbook.presenter.Presenter;

import java.util.List;

public class MyCommentPresenter extends Presenter {

    public MyCommentPresenter(Context context) {
        super(context);
    }

    public void delComment(final DelCommentCallback callback, int commentId, final int pos) {
        rxJavaExecuter.execute(CookRepository.getInstance().delComment(commentId),
                new BaseSubscriber<EmptyBean>() {
                    @Override
                    public void next(EmptyBean data) {
                        callback.onDelSuccess(pos);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onDelFailure(msg);
                    }
                }
        );
    }

    public void loadMyCommentList(final MyCommentCallback callback) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().getMyCommentList(),
                new BaseSubscriber<List<MyCommentBean>>() {
                    @Override
                    public void next(List<MyCommentBean> data) {
                        callback.onLoadMyCommentList(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onLoadMyCommentListFailure(msg);
                    }
                }
        );
    }

    @Override
    public void destroy() {

    }
}
