package com.owlling.cookbook.community.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileBean implements Serializable {

    @SerializedName("profile_name")
    String profileName;

}
