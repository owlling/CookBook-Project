package com.owlling.cookbook.community.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class TimeUtil {

    private static final String TAG = "TimeUtil";

    public static String toTimeStr(int time) {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm", Locale.CHINESE);
        return dateFormat.format(new Date(Long.parseLong(time + "000")));
    }

    //帖子发布时间计算
    public static final String getOffsetTime(int time) {
        final long cur = System.currentTimeMillis();

        long postTime = 1000;
        postTime *= time;
        final long offset = (cur - postTime) / 1000 / 60;
        if (offset < 1) {
            return "刚刚";
        } else if (offset < 60) {
            return offset + "分钟前";
        } else if (offset < 60 * 24) {
            return (offset / 60) + "小时前";
        } else if (offset < 60 * 24 * 31) {
            return (offset / (60 * 24 * 31)) + "天前";
        } else if (offset < 60 * 24 * 31 * 12) {
            return (offset / (60 * 24 * 31 * 12)) + "个月前";
        } else {
            return String.valueOf(time);
        }
    }

}
