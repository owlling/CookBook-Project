package com.owlling.cookbook.community.user;

public interface PutProfileCallback {
    void onPutSuccess(ProfileBean profile);
    void onPutFailure(String msg);
}
