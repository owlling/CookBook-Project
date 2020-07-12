package com.owlling.cookbook.community.acc;

import android.content.Context;
import android.util.Log;

import com.owlling.cookbook.community.net.BaseSubscriber;
import com.owlling.cookbook.community.model.EmptyBean;
import com.owlling.cookbook.community.user.ProfileBean;
import com.owlling.cookbook.community.user.PutProfileCallback;
import com.owlling.cookbook.community.user.UpdateInfoCallback;
import com.owlling.cookbook.community.user.UserInfoBean;
import com.owlling.cookbook.community.user.UserInfoCallback;
import com.owlling.cookbook.model.respository.CookRepository;
import com.owlling.cookbook.presenter.Presenter;

public class UserPresenter extends Presenter {

    private static final String TAG = "UserPresenter";

    public UserPresenter(){}

    public UserPresenter(Context context) {
        super(context);
    }

    void register(final RegisterCallback callback, final String username, final String pwd) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().register(username, pwd),
                new BaseSubscriber<EmptyBean>() {
                    @Override
                    public void next(EmptyBean data) {
                        callback.onSuccess();
                    }

                    @Override
                    public void error(String msg) {
                        callback.onFailure(msg);
                    }
                }
        );
    }

    void login(final LoginCallback callback, final String username, final String pwd) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().login(username, pwd),
                new BaseSubscriber<LoginBean>() {
                    @Override
                    public void next(LoginBean data) {
                        callback.onSuccess(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onFailure(msg);
                    }
                }
        );
    }

    public void updateInfo(final UpdateInfoCallback callback,
                           final String profile, final String nickname, final String pwd) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().updateInfo(profile, nickname, pwd),
                new BaseSubscriber<EmptyBean>() {

                    @Override
                    public void next(EmptyBean data) {
                        callback.onSuccess();
                    }

                    @Override
                    public void error(String msg) {
                        callback.onFailure(msg);
                    }
                }
        );
    }

    public void putProfile(final PutProfileCallback callback, String uri){
        Log.i(TAG, "uri as url " + uri);
        rxJavaExecuter.execute(
                CookRepository.getInstance().putProfile(uri),
                new BaseSubscriber<ProfileBean>() {
                    @Override
                    public void next(ProfileBean data) {
                        callback.onPutSuccess(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onPutFailure(msg);
                    }
                }
        );
    }
    public void userInfo(final UserInfoCallback callback){
        rxJavaExecuter.execute(
                CookRepository.getInstance().userInfo(),
                new BaseSubscriber<UserInfoBean>() {

                    @Override
                    public void next(UserInfoBean data) {
                        callback.onSuccess(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onGetUserInfoFailure(msg);
                    }
                }
        );
    }

    @Override
    public void destroy() {

    }
}
