package com.owlling.cookbook.model.entity.tb_cook;

import com.owlling.cookbook.model.entity.cookentity.CategoryInfo;
import com.owlling.cookbook.ui.component.tagComponent.ChannelItem;

import org.litepal.crud.DataSupport;

public class TB_CustomCategory extends DataSupport{

    private String ctgId;
    private String name;

    public TB_CustomCategory(){

    }

    public TB_CustomCategory(CategoryInfo categoryInfo){
        this.ctgId = categoryInfo.getCtgId();
        this.name = categoryInfo.getName();
    }

    public TB_CustomCategory(ChannelItem channelItem){
        this.ctgId = channelItem.getId();
        this.name = channelItem.getName();
    }
    public String getCtgId() {
        return ctgId;
    }

    public void setCtgId(String ctgId) {
        this.ctgId = ctgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
