package com.owlling.cookbook.community.mine.mywatch;

import java.util.List;

public interface WatchListCallback {
    void onLoad(List<WatchBean> list);
    void onLoadFailure(String msg);
}
