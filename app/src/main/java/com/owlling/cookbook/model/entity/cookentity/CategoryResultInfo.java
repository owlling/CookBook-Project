package com.owlling.cookbook.model.entity.cookentity;

import java.util.ArrayList;



public class CategoryResultInfo {

    private CategoryInfo categoryInfo;
    private ArrayList<CategoryChildInfo1> childs;

    public CategoryResultInfo(){

    }

    public CategoryInfo getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public ArrayList<CategoryChildInfo1> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<CategoryChildInfo1> childs) {
        this.childs = childs;
    }
}
