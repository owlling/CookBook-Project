package com.owlling.cookbook.community.user.userdetail;

import android.content.Context;

import com.owlling.cookbook.community.net.BaseSubscriber;
import com.owlling.cookbook.model.respository.CookRepository;
import com.owlling.cookbook.presenter.Presenter;

public class UserDetailPresenter extends Presenter {

    public UserDetailPresenter(Context context) {
        this.context = context;
    }

    public void loadUserDetail(final UserDetailCallback callback, int uid) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().getUserDetail(uid),
                new BaseSubscriber<UserDetailBean>() {
                    @Override
                    public void next(UserDetailBean data) {
                        callback.onLoadDetail(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onLoadDetailFailure(msg);
                    }
                }
        );
    }

    @Override
    public void destroy() {

    }
}
