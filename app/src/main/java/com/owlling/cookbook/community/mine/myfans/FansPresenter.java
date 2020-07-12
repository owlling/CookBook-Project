package com.owlling.cookbook.community.mine.myfans;

import android.content.Context;

import com.owlling.cookbook.community.net.BaseSubscriber;
import com.owlling.cookbook.model.respository.CookRepository;
import com.owlling.cookbook.presenter.Presenter;

import java.util.List;

public class FansPresenter extends Presenter {

    public FansPresenter(Context context) {
        this.context = context;
    }

    public void loadFansList(final FansListCallback callback) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().getFansList(),
                new BaseSubscriber<List<FansBean>>() {
                    @Override
                    public void next(List<FansBean> data) {
                        callback.onLoad(data);
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
