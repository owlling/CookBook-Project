package com.owlling.cookbook.community.mine.myfans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FansBean implements Serializable {

    /**
     * id : 12
     * watcher_id : 10
     * watched_id : 9
     * act_time : 1558103433
     * c_fans : 1
     */

    private int id;

    @SerializedName("watcher_id")
    private int watcherId;
    @SerializedName("watched_id")
    private int watchedId;
    @SerializedName("act_time")
    private int actTime;
    @SerializedName("c_fans")
    private int cFans;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWatcherId() {
        return watcherId;
    }

    public void setWatcherId(int watcherId) {
        this.watcherId = watcherId;
    }

    public int getWatchedId() {
        return watchedId;
    }

    public void setWatchedId(int watchedId) {
        this.watchedId = watchedId;
    }

    public int getActTime() {
        return actTime;
    }

    public void setActTime(int actTime) {
        this.actTime = actTime;
    }

    public int getCFans() {
        return cFans;
    }

    public void setCFans(int cFans) {
        this.cFans = cFans;
    }

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}