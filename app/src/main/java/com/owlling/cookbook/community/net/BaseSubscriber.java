package com.owlling.cookbook.community.net;

import android.util.Log;

import com.owlling.cookbook.community.model.BaseBean;

import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<BaseBean<T>> {

    @Override
    public void onCompleted() {
        Log.i("aresult ", "onCompleted");

    }

    @Override
    public void onError(Throwable e) {
        error(e.getMessage());
    }

    @Override
    public void onNext(BaseBean<T> t) {
        if (t != null && t.errCode == 200) {
            next(t.data);
        } else {
            error(t == null || t.errMsg == null ? "网络错误" : t.errMsg);
        }
    }

    public abstract void next(T data);

    public abstract void error(String msg);


}
