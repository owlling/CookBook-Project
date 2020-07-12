package com.owlling.cookbook.community.mine.mywatch;

import android.content.Context;

import com.owlling.cookbook.community.model.EmptyBean;
import com.owlling.cookbook.community.net.BaseSubscriber;
import com.owlling.cookbook.community.post.bean.UnWatchCallback;
import com.owlling.cookbook.community.post.bean.WatchCallback;
import com.owlling.cookbook.model.respository.CookRepository;
import com.owlling.cookbook.presenter.Presenter;

import java.util.List;

public class WatchPresenter extends Presenter {

    public WatchPresenter(Context context) {
        this.context = context;
    }

    public void loadWatchList(final WatchListCallback callback) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().getWatchList(),
                new BaseSubscriber<List<WatchBean>>() {
                    @Override
                    public void next(List<WatchBean> data) {
                        callback.onLoad(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onLoadFailure(msg);
                    }
                }
        );
    }

    public void unwatch(final UnWatchCallback callback, final int watchedId, final int pos) {
        rxJavaExecuter.execute(CookRepository.getInstance().unwatch(watchedId),
                new BaseSubscriber<EmptyBean>() {
                    @Override
                    public void next(EmptyBean data) {
                        callback.onUnWatchSuccess(data, pos);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onUnWatchFailure(msg);
                    }
                }
        );
    }

    public void watch(final WatchCallback callback, final int watchedId, final int postId) {
        rxJavaExecuter.execute(CookRepository.getInstance().watch(watchedId),
                new BaseSubscriber<EmptyBean>() {

                    @Override
                    public void next(EmptyBean data) {
                        callback.onWatchSuccess(data, postId);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onWatchFailure(msg);
                    }
                }
        );
    }

    @Override
    public void destroy() {

    }
}
