package com.owlling.cookbook;

import android.content.Context;

import com.owlling.cookbook.constants.Constants;
import com.owlling.cookbook.utils.Logger.LogLevel;
import com.owlling.cookbook.utils.Logger.Logger;

import org.litepal.LitePalApplication;

public class CookBookApp extends LitePalApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        //Logger设置
        if(BuildConfig.DEBUG) {
            Logger.init(Constants.Common_Tag).logLevel(LogLevel.FULL);
        }
        else{
            Logger.init(Constants.Common_Tag).logLevel(LogLevel.NONE);
        }

    }

    public static Context getContext() {
        return mContext;
    }

}
