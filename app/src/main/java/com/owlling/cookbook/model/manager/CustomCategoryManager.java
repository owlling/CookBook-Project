package com.owlling.cookbook.model.manager;

import android.content.res.AssetManager;

import com.owlling.cookbook.CookBookApp;
import com.owlling.cookbook.model.entity.cookentity.CategoryChildInfo1;
import com.owlling.cookbook.model.entity.cookentity.CategoryChildInfo2;
import com.owlling.cookbook.model.entity.cookentity.CategoryInfo;
import com.owlling.cookbook.model.entity.tb_cook.TB_CustomCategory;
import com.owlling.cookbook.ui.component.tagComponent.ChannelItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CustomCategoryManager {

    private static CustomCategoryManager Instance = null;

    public static CustomCategoryManager getInstance(){
        if(Instance == null)
            Instance = new CustomCategoryManager();

        return Instance;
    }

    private List<TB_CustomCategory> datas;
    private List<TB_CustomCategory> otherDatas;

    private Gson gson;
    private CustomCategoryManager(){
        gson = new Gson();
    }

    public void initData(ArrayList<CategoryChildInfo1> categoryAllDatas){
        datas = DataSupport.findAll(TB_CustomCategory.class);

        if(datas == null || datas.size() < 1){
            datas = new ArrayList<>();

            try {
                StringBuilder buf = new StringBuilder();
                InputStream json = CookBookApp
                        .getContext().getAssets().open("default_cook_category.json"
                                , AssetManager.ACCESS_STREAMING);

                BufferedReader in =
                        new BufferedReader(new InputStreamReader(json, "UTF-8"));
                String str;
                while ((str = in.readLine()) != null) {
                    buf.append(str);
                }
                in.close();

                JSONArray jo = new JSONArray(buf.toString());
                datas = gson
                        .fromJson(
                                jo.toString()
                                , new TypeToken<List<TB_CustomCategory>>() {}.getType());
            }
            catch (Exception e){

            }

        }

        otherDatas = new ArrayList<>();

        if(categoryAllDatas == null || categoryAllDatas.size() < 1)
            return ;

        for(CategoryChildInfo1 item1 : categoryAllDatas){
            for(CategoryChildInfo2 item2 : item1.getChilds()){
                if(!isInDatas(item2.getCategoryInfo().getCtgId())){
                    otherDatas.add(categoryChildInfo2Tb(item2.getCategoryInfo()));
                }
            }
        }
    }

    private boolean isInDatas(String cid){
        for(TB_CustomCategory item : datas){
            if(item.getCtgId().equals(cid)){
                return true;
            }
        }

        return false;
    }

    private TB_CustomCategory categoryChildInfo2Tb(CategoryInfo categoryInfo){
        return new TB_CustomCategory(categoryInfo);
    }

    private TB_CustomCategory ChannelItem2Tb(ChannelItem channelItem){
        return new TB_CustomCategory(channelItem);
    }

    public List<TB_CustomCategory> getDatas(){
        return datas;
    }

    public List<TB_CustomCategory> getOtherDatas() {
        return otherDatas;
    }

    public void save(List<ChannelItem> channelItemDatas, List<ChannelItem> channelItemOtherDatas){
        this.datas.clear();
        this.otherDatas.clear();

        for(ChannelItem item : channelItemDatas)
            this.datas.add(ChannelItem2Tb(item));

        for(ChannelItem item : channelItemOtherDatas)
            this.otherDatas.add(ChannelItem2Tb(item));

        DataSupport.deleteAll(TB_CustomCategory.class);
        DataSupport.saveAll(this.datas);
    }
}
