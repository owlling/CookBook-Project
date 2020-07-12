package com.owlling.cookbook.community.mine.mycomments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyCommentBean implements Serializable {

    /**
     * id : 8
     * comment_id : 11
     * post_content : 计时开始快开学免洗面膜吃嘛吃嘛吃嘛嘛
     * comment_content : 加大力度考察课没错没错麻辣错啦错啦充卡吃卡卡充卡充卡可擦
     * act_time : 1558101201
     */

    private int id; // post id
    @SerializedName("comment_id")
    private int commentId;
    @SerializedName("post_content")
    private String postContent;
    @SerializedName("comment_content")
    private String commentContent;
    @SerializedName("act_time")
    private int actTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getActTime() {
        return actTime;
    }

    public void setActTime(int actTime) {
        this.actTime = actTime;
    }
}
