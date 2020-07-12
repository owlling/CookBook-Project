package com.owlling.cookbook.community.acc;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginBean implements Serializable {

    @SerializedName("uid")
    public int uid;

}