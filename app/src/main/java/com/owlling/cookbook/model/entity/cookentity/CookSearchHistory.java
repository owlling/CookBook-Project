package com.owlling.cookbook.model.entity.cookentity;
public class CookSearchHistory {

    private String name;

    public CookSearchHistory(){

    }

    public CookSearchHistory(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}