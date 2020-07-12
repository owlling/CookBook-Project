package com.owlling.cookbook.community.mine.mypost;

import android.content.Context;

import com.owlling.cookbook.community.model.EmptyBean;
import com.owlling.cookbook.community.net.BaseSubscriber;
import com.owlling.cookbook.model.respository.CookRepository;
import com.owlling.cookbook.presenter.Presenter;

import java.util.List;

public class MyPostPresenter extends Presenter {

    public MyPostPresenter(Context context) {
        super(context);
    }

    public void delPost(final DelPostCallback callback, int postId, final int pos) {
        rxJavaExecuter.execute(CookRepository.getInstance().delPost(postId),
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

    public void loadMyPostList(final MyPostCallback callback) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().getMyPostList(),
                new BaseSubscriber<List<MyPostBean>>() {
                    @Override
                    public void next(List<MyPostBean> data) {
                        callback.onLoadMyPostList(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onLoadMyPostListFailure(msg);
                    }
                }
        );
    }

    @Override
    public void destroy() {

    }
}
