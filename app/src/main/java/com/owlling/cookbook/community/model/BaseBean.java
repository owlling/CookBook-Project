package com.owlling.cookbook.community.model;

import com.google.gson.annotations.SerializedName;

public class BaseBean<T> {

    @SerializedName("err_code")
    public int errCode;

    @SerializedName("err_msg")
    public String errMsg;

    @SerializedName("data")
    public T data;

}
