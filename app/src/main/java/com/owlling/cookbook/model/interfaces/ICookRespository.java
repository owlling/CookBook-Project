package com.owlling.cookbook.model.interfaces;

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
import com.owlling.cookbook.model.entity.cookentity.subscriberEntity.CategorySubscriberResultInfo;
import com.owlling.cookbook.model.entity.cookentity.subscriberEntity.SearchCookMenuSubscriberResultInfo;

import java.util.List;

import rx.Observable;

public interface ICookRespository {

    Observable<CategorySubscriberResultInfo> getCategoryQuery();

    Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByID(final String cid, final int page, final int size);

    Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByName(final String name, final int page, final int size);

    Observable<BaseBean<List<PostBean>>> getPostList(int page);

    Observable<BaseBean<PostBean>> getPostDetail(int postId);

    Observable<BaseBean<List<WatchBean>>> getWatchList();

    Observable<BaseBean<List<FansBean>>> getFansList();

    Observable<BaseBean<UserDetailBean>> getUserDetail(int uid);

    Observable<BaseBean<List<CommentBean>>> getCommentList(int postId);

    Observable<BaseBean<List<MyPostBean>>> getMyPostList();

    Observable<BaseBean<List<MyCommentBean>>> getMyCommentList();

    Observable<BaseBean<CreatePostBean>> createPost(String content, String img);

    Observable<BaseBean<CreateCommentBean>> createComment(int postId, String content);

    Observable<BaseBean<EmptyBean>> register(final String username, final String pwd);

    Observable<BaseBean<LoginBean>> login(String username, String pwd);

    Observable<BaseBean<EmptyBean>> updateInfo(String profile, String nickname, String pwd);

    Observable<BaseBean<UserInfoBean>> userInfo();

    Observable<BaseBean<ProfileBean>> putProfile(String uri);

    Observable<BaseBean<PostImgBean>> putPostImg(String uri);

    Observable<BaseBean<EmptyBean>> like(int postId);

    Observable<BaseBean<EmptyBean>> watch(int watchId);

    Observable<BaseBean<EmptyBean>> unwatch(int watchId);

    Observable<BaseBean<EmptyBean>> delPost(int postId);

    Observable<BaseBean<EmptyBean>> delComment(int commentId);
}
