package com.owlling.cookbook.community.utils;

public final class TextUtil {


    public static String composeComment(String author, String comment) {
        return "<font color=\"#8393c5\">" + author + " : </font>" + comment;
    }

    public static String getMoreCommentStr(int commentCount) {
        return "查看全部" + commentCount + "条回复 > ";
    }

    public static String composeLikeAndComment(int likeCount, int commentCount) {
        return likeCount + " 点赞， " + commentCount + " 评论";
    }

    public static String composeFlourAndTime(int flour, int time) {
        return flour + "楼 " + TimeUtil.getOffsetTime(time);
    }

    public static String composeLikedAndWatcherCount(int likedCount, int watcherCount) {
        return "收到" + likedCount + "点赞 " + watcherCount + "粉丝";
    }


}
