package com.owlling.cookbook.model.entity.cookentity.subscriberEntity;

import com.owlling.cookbook.model.entity.cookentity.CategoryResultInfo;

import java.io.Serializable;

public class CategorySubscriberResultInfo implements Serializable {

    private String msg;
    private String retCode;
    private CategoryResultInfo result;

    public CategorySubscriberResultInfo(){

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public CategoryResultInfo getResult() {
        return result;
    }

    public void setResult(CategoryResultInfo result) {
        this.result = result;
    }
}
