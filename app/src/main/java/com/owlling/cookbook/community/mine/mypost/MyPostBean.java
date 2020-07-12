package com.owlling.cookbook.community.mine.mypost;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyPostBean implements Serializable {

    /**
     * id : 2
     * content : gggg
     * c_liked : 1
     * c_comment : 5
     */

    @SerializedName("id")
    private int postId;
    private String content;
    @SerializedName("c_liked")
    private int likedCount;

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    private int commentCount;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }
}
