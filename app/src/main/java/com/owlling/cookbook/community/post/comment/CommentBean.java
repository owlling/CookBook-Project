package com.owlling.cookbook.community.post.comment;

import com.google.gson.annotations.SerializedName;

public class CommentBean {

    /**
     * content : 好好好
     * author_id : 2
     * flour : 7
     * act_time : 1555252085
     * nickname : nick
     * profile_name : UVuK8YxoWp1555170109
     */

    private String content;
    @SerializedName("author_id")
    private int authorId;
    private int flour;
    @SerializedName("act_time")
    private int actTime;
    private String nickname;
    @SerializedName("profile_name")
    private String profileName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getFlour() {
        return flour;
    }

    public void setFlour(int flour) {
        this.flour = flour;
    }

    public int getActTime() {
        return actTime;
    }

    public void setActTime(int actTime) {
        this.actTime = actTime;
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
}
