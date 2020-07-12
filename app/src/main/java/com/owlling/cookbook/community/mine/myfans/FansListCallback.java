package com.owlling.cookbook.community.mine.myfans;

import java.util.List;

public interface FansListCallback {
    void onLoad(List<FansBean> list);
    void onLoadFailure(String msg);
}
