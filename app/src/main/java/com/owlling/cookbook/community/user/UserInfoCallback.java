package com.owlling.cookbook.community.user;

public interface UserInfoCallback {
    void onSuccess(UserInfoBean userInfo);
    void onGetUserInfoFailure(String msg);
}
