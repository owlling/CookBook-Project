package com.owlling.cookbook.community.mine.mywatch;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WatchBean implements Serializable {

    public int getWatcherId() {
        return watcherId;
    }

    public void setWatcherId(int watcherId) {
        this.watcherId = watcherId;
    }

    /**
     * watcher_id : 1
     * id : 2
     * nickname : 111ababa
     * profile_name : 111ababa
     * watcher_count : 1
     */

    @SerializedName("watcher_id")
    private int watcherId;

    @SerializedName("id")
    private int watchedId;
    private String nickname;
    @SerializedName("profile_name")
    private String profileName;
    @SerializedName("watcher_count")
    private int watcherCount;

    public int getWatchedId() {
        return watchedId;
    }

    public void setWatchedId(int watchedId) {
        this.watchedId = watchedId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getWatcherCount() {
        return watcherCount;
    }

    public void setWatcherCount(int watcherCount) {
        this.watcherCount = watcherCount;
    }
}