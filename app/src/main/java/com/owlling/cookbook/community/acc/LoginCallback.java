package com.owlling.cookbook.community.acc;

public interface LoginCallback {

    void onSuccess(LoginBean loginBean);

    void onFailure(String msg);

}
