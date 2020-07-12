package com.owlling.cookbook.community.post.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateCommentBean implements Serializable {
    @SerializedName("comment_id")
    int commentId;
}
