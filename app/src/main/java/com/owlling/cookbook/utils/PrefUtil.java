package com.owlling.cookbook.utils;

import android.content.SharedPreferences;

import com.owlling.cookbook.CookBookApp;

import static android.content.Context.MODE_PRIVATE;

public final class PrefUtil {

    private static String userFilePath = "shareprefer_file_cook_search";
    private static SharedPreferences shareUserFile;
    private static SharedPreferences.Editor editorUserFile;
    private static final String KEY_USER_ID = "key-user-id";

    static {
        shareUserFile = CookBookApp.getContext().getSharedPreferences(userFilePath, MODE_PRIVATE);
        editorUserFile = shareUserFile.edit();
    }

    public void set() {

    }

    public static void putString(final String key, final String v) {
        editorUserFile.putString(key, v);
    }

    public static String getString(final String key) {
        return getString(key, "");
    }

    public static String getString(final String key, String defValue) {
        return shareUserFile.getString(key, defValue);
    }

    public static boolean isLogin() {
        return shareUserFile.getInt(KEY_USER_ID, 0) != 0;
    }

    public static String getUid() {
        return shareUserFile.getInt(KEY_USER_ID, 0) + "";
    }

    public static void setUserId(int uid) {
        editorUserFile.putInt(KEY_USER_ID, uid).commit();
    }

    public static void setLogout() {
        editorUserFile.putInt(KEY_USER_ID, 0).commit();
    }

}
