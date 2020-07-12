package com.owlling.cookbook.community.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfoBean implements Serializable {
    @SerializedName("nickname")
    String nickname;
    @SerializedName("username")
    String username;
    @SerializedName("profile_name")
    String profileName;

}
