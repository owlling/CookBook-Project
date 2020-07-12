package com.owlling.cookbook.model.interfaces;

import android.support.annotation.Nullable;

import com.owlling.cookbook.community.mine.mycomments.MyCommentBean;
import com.owlling.cookbook.community.mine.myfans.FansBean;
import com.owlling.cookbook.community.model.BaseBean;
import com.owlling.cookbook.community.model.PostBean;
import com.owlling.cookbook.community.model.EmptyBean;
import com.owlling.cookbook.community.acc.LoginBean;
import com.owlling.cookbook.community.post.bean.CreateCommentBean;
import com.owlling.cookbook.community.post.bean.CreatePostBean;
import com.owlling.cookbook.community.post.bean.PostImgBean;
import com.owlling.cookbook.community.post.comment.CommentBean;
import com.owlling.cookbook.community.mine.mypost.MyPostBean;
import com.owlling.cookbook.community.user.ProfileBean;
import com.owlling.cookbook.community.user.UserInfoBean;
import com.owlling.cookbook.community.user.userdetail.UserDetailBean;
import com.owlling.cookbook.community.mine.mywatch.WatchBean;
import com.owlling.cookbook.constants.Constants;
import com.owlling.cookbook.model.entity.cookentity.subscriberEntity.CategorySubscriberResultInfo;
import com.owlling.cookbook.model.entity.cookentity.subscriberEntity.SearchCookMenuSubscriberResultInfo;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface ICookService {

    @Headers("Domain-name: mob")
    @GET(Constants.Cook_Service_CategoryQuery)
    Observable<CategorySubscriberResultInfo> getCategoryQuery(@Query(Constants.Cook_Parameter_Key) String key);

    @Headers("Domain-name: mob")
    @GET(Constants.Cook_Service_MenuSearch)
    Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByID(
            @Query(Constants.Cook_Parameter_Key) String key
            , @Query(Constants.Cook_Parameter_Cid) String cid
            , @Query(Constants.Cook_Parameter_Page) int page
            , @Query(Constants.Cook_Parameter_Size) int size);

    @Headers("Domain-name: mob")
    @GET(Constants.Cook_Service_MenuSearch)
    Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByName(
            @Query(Constants.Cook_Parameter_Key) String key
            , @Query(Constants.Cook_Parameter_Name) String name
            , @Query(Constants.Cook_Parameter_Page) int page
            , @Query(Constants.Cook_Parameter_Size) int size);

    @Headers("Domain-name: com")
    @GET("post_list")
    Observable<BaseBean<List<PostBean>>> getPostList(@Query("page") int page);

    @Headers("Domain-name: com")
    @GET("post_detail")
    Observable<BaseBean<PostBean>> getPostDetail(@Query("pid") int postId);

    @Headers("Domain-name: com")
    @GET("watch_list")
    Observable<BaseBean<List<WatchBean>>> getWatchList();

    @Headers("Domain-name: com")
    @GET("my_fans")
    Observable<BaseBean<List<FansBean>>> getFansList();

    @Headers("Domain-name: com")
    @GET("user_detail")
    Observable<BaseBean<UserDetailBean>> getUserDetail(@Query("uid") int uid);

    @Headers("Domain-name: com")
    @GET("comment_list")
    Observable<BaseBean<List<CommentBean>>> getCommentList(@Query("post_id") int postId);

    @Headers("Domain-name: com")
    @GET("my_post_list")
    Observable<BaseBean<List<MyPostBean>>> getMyPostList();

    @Headers("Domain-name: com")
    @GET("my_comments")
    Observable<BaseBean<List<MyCommentBean>>> getMyCommentList();

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("create_user")
    Observable<BaseBean<EmptyBean>> register(
            @Field("username") String username,
            @Field("password") String pwd
    );

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("login")
    Observable<BaseBean<LoginBean>> login(
            @Field("username") String username,
            @Field("password") String pwd
    );

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("update_info")
    Observable<BaseBean<EmptyBean>> updateInfo(
            @Nullable @Field("profile") String profile,
            @Nullable @Field("nickname") String nickname,
            @Nullable @Field("password") String pwd
    );

    @Headers("Domain-name: com")
    @GET("user_info")
    Observable<BaseBean<UserInfoBean>> userInfo();

    @Headers("Domain-name: com")
    @Multipart
    @POST("put_profile")
    Observable<BaseBean<ProfileBean>> putProfile(
            @Part("avatar\"; filename=\"avatar.png\"") RequestBody img
    );

    @Headers("Domain-name: com")
    @Multipart
    @POST("create_post_img")
    Observable<BaseBean<PostImgBean>> putPostImg(
            @Part("postimg\"; filename=\"postimg.png\"") RequestBody img
    );

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("create_post")
    Observable<BaseBean<CreatePostBean>> createPost(
            @Field("content") String content,
            @Nullable @Field("post_img") String img
    );

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("create_comment")
    Observable<BaseBean<CreateCommentBean>> createComment(
            @Field("post_id") int postId,
            @Field("content") String content
    );

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("like")
    Observable<BaseBean<EmptyBean>> like(
            @Field("post_id") int postId);

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("watch")
    Observable<BaseBean<EmptyBean>> watch(
            @Field("watched_id") int watchedId);

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("unwatch")
    Observable<BaseBean<EmptyBean>> unwatch(
            @Field("watched_id") int watchedId);

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("del_post")
    Observable<BaseBean<EmptyBean>> delPost(
            @Field("post_id") int postId);

    @Headers("Domain-name: com")
    @FormUrlEncoded
    @POST("del_comment")
    Observable<BaseBean<EmptyBean>> delComment(
            @Field("comment_id") int commentId);

}
