package com.owlling.cookbook.community.user.userdetail;

public interface UserDetailCallback {
    void onLoadDetail(UserDetailBean userDetail);

    void onLoadDetailFailure(String msg);
}
