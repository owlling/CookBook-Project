package com.owlling.cookbook.model.respository;

import android.util.Log;

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
import com.owlling.cookbook.model.interfaces.ICookRespository;
import com.owlling.cookbook.model.interfaces.ICookService;
import com.owlling.cookbook.model.net.RetrofitService;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

public class CookRepository implements ICookRespository {

    private static String TAG = "CookRepository";

    private static CookRepository Instance = null;

    public static CookRepository getInstance() {
        if (Instance == null)
            Instance = new CookRepository();

        return Instance;
    }

    private Gson gson;

    private CookRepository() {
        gson = new Gson();
    }

    @Override
    public Observable<CategorySubscriberResultInfo> getCategoryQuery() {
        ICookService iCookService = RetrofitService.getInstance().createApi(ICookService.class);
        return iCookService.getCategoryQuery(Constants.Key_MobAPI_Cook);
    }

    @Override
    public Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByID(final String cid, final int page, final int size) {
        ICookService iCookService = RetrofitService.getInstance().createApi(ICookService.class);
        return iCookService.searchCookMenuByID(Constants.Key_MobAPI_Cook, cid, page, size);
    }

    @Override
    public Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByName(final String name, final int page, final int size) {
        ICookService iCookService = RetrofitService.getInstance().createApi(ICookService.class);
        return iCookService.searchCookMenuByName(Constants.Key_MobAPI_Cook, name, page, size);
    }

    @Override
    public Observable<BaseBean<List<PostBean>>> getPostList(int page) {
        Log.i(TAG, "getPostList(" + page);
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.getPostList(page);
    }

    @Override
    public Observable<BaseBean<PostBean>> getPostDetail(int postId) {
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.getPostDetail(postId);
    }

    @Override
    public Observable<BaseBean<List<WatchBean>>> getWatchList() {
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.getWatchList();
    }

    @Override
    public Observable<BaseBean<List<FansBean>>> getFansList() {
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.getFansList();
    }

    @Override
    public Observable<BaseBean<UserDetailBean>> getUserDetail(int uid) {
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.getUserDetail(uid);
    }

    @Override
    public Observable<BaseBean<List<CommentBean>>> getCommentList(int postId) {
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.getCommentList(postId);
    }

    @Override
    public Observable<BaseBean<List<MyPostBean>>> getMyPostList() {
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.getMyPostList();
    }

    @Override
    public Observable<BaseBean<List<MyCommentBean>>> getMyCommentList() {
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.getMyCommentList();
    }

    @Override
    public Observable<BaseBean<CreatePostBean>> createPost(String content, String img) {
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.createPost(content, img);
    }

    @Override
    public Observable<BaseBean<CreateCommentBean>> createComment(int postId, String content) {
        toComApi();
        ICookService service = RetrofitService.getInstance().createApi(ICookService.class);
        return service.createComment(postId, content);
    }

    @Override
    public Observable<BaseBean<EmptyBean>> register(String username, String pwd) {
        toComApi();
        return getService().register(username, pwd);
    }

    @Override
    public Observable<BaseBean<LoginBean>> login(String username, String pwd) {
        toComApi();
        return getService().login(username, pwd);
    }

    @Override
    public Observable<BaseBean<EmptyBean>> updateInfo(String profile, String nickname, String pwd) {
        toComApi();
        return getService().updateInfo(profile, nickname, pwd);
    }

    @Override
    public Observable<BaseBean<UserInfoBean>> userInfo() {
        toComApi();
        return getService().userInfo();
    }

    @Override
    public Observable<BaseBean<ProfileBean>> putProfile(String uri) {
        toComApi();
        if (uri != null) {
            File file = new File(uri);
            file = new File(file.getAbsolutePath());
            Log.i(TAG, "file " + file.toString());
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            return getService().putProfile(imageBody);
        } else {
            Log.i(TAG, "uri is null!!");
            return null;
        }
    }

    @Override
    public Observable<BaseBean<PostImgBean>> putPostImg(String uri) {
        toComApi();
        if (uri != null) {
            File file = new File(uri);
            file = new File(file.getAbsolutePath());
            Log.i(TAG, "file " + file.toString());
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            return getService().putPostImg(imageBody);
        } else {
            Log.i(TAG, "uri is null!!");
            return null;
        }
    }

    @Override
    public Observable<BaseBean<EmptyBean>> like(int postId) {
        toComApi();
        return getService().like(postId);
    }

    @Override
    public Observable<BaseBean<EmptyBean>> watch(int watchId) {
        toComApi();
        return getService().watch(watchId);
    }

    @Override
    public Observable<BaseBean<EmptyBean>> unwatch(int watchId) {
        toComApi();
        return getService().unwatch(watchId);
    }

    @Override
    public Observable<BaseBean<EmptyBean>> delPost(int postId) {
        toComApi();
        return getService().delPost(postId);
    }

    @Override
    public Observable<BaseBean<EmptyBean>> delComment(int commentId) {
        toComApi();
        return getService().delComment(commentId);
    }

    private ICookService getService() {
        return RetrofitService.getInstance().createApi(ICookService.class);
    }


    private void toMobApi() {
        RetrofitUrlManager.getInstance().putDomain("mob", Constants.baseURL);
    }

    private void toComApi() {
//        RetrofitUrlManager.getInstance().startAdvancedModel(Constants.baseComUrl);
        RetrofitUrlManager.getInstance().putDomain("com", Constants.baseComUrl);
    }


}
