package com.owlling.cookbook.community.utils;

import android.text.TextUtils;
import android.util.Log;

import com.owlling.cookbook.model.entity.cookentity.CookDetail;

import java.util.List;

public class CookUtils {
    private static final String TAG  = "CookUtil";

    public static void filterCook(List<CookDetail> list) {
        Log.i(TAG, "former size " + list.size());
        for (CookDetail cookDetail : list) {
            if (TextUtils.isEmpty(cookDetail.getRecipe().getImg())){
                list.remove(cookDetail);
            }
        }
        Log.i(TAG, "after filter size " + list.size());
    }
}
