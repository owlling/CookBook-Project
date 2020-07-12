package com.owlling.cookbook.constants;


public final class Constants {
    //Tag
    public final static String Common_Tag = "CookBook";

    public final static String Key_MobAPI_Cook = "2a9cc0b08f1d8";

    public final static int Per_Page_Size = 20;

    public final static String baseURL = "https://apicloud.mob.com/";

    //菜谱数据获取；菜谱API；
//    public final static String baseComUrl = "http://192.168.123.198:8081/";
    public final static String baseComUrl = "http://118.89.233.206:9891/";
    public final static String baseComProfileUrl = baseComUrl + "my_profile?uid=";
    public final static String baseComPostImgUrl = baseComUrl + "post_img?post_img=";
    public final static String Cook_Service_CategoryQuery = "v1/cook/category/query";//查询菜谱的所有分类
    public final static String Cook_Service_MenuSearch = "v1/cook/menu/search";//根据标签ID/菜谱名称查询菜谱详情
    public final static String Cook_Service_MenuQuery = "v1/cook/menu/query";//根据菜谱ID查询菜谱详情

    public final static String Cook_Parameter_Key = "key";//MobAPI 开发者Key
    public final static String Cook_Parameter_Cid = "cid";//
    public final static String Cook_Parameter_Name = "name";//
    public final static String Cook_Parameter_Page = "page";//
    public final static String Cook_Parameter_Size = "size";//

}
