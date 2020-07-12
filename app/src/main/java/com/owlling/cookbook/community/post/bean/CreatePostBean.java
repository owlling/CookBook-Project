package com.owlling.cookbook.community.post.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreatePostBean implements Serializable {
    @SerializedName("post_id")
    int postId;
}
