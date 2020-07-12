package com.owlling.cookbook.community.post;

import android.content.Context;
import android.util.Log;

import com.owlling.cookbook.community.model.EmptyBean;
import com.owlling.cookbook.community.model.PostBean;
import com.owlling.cookbook.community.net.BaseSubscriber;
import com.owlling.cookbook.community.post.bean.CreateCommentBean;
import com.owlling.cookbook.community.post.bean.CreatePostBean;
import com.owlling.cookbook.community.post.bean.PostImgBean;
import com.owlling.cookbook.community.post.bean.UnWatchCallback;
import com.owlling.cookbook.community.post.bean.WatchCallback;
import com.owlling.cookbook.community.post.callbacks.CreateCommentCallback;
import com.owlling.cookbook.community.post.callbacks.CreatePostCallback;
import com.owlling.cookbook.community.mine.mypost.MyPostCallback;
import com.owlling.cookbook.community.post.callbacks.PostCallback;
import com.owlling.cookbook.community.post.callbacks.PostDetailCallback;
import com.owlling.cookbook.community.post.callbacks.PutImgCallback;
import com.owlling.cookbook.model.respository.CookRepository;
import com.owlling.cookbook.presenter.Presenter;

import java.util.List;

public class PostPresenter extends Presenter {

    public PostPresenter(Context context) {
        super(context);
    }

    public void fetchData(final PostCallback callback) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().getPostList(1),
                new BaseSubscriber<List<PostBean>>() {
                    @Override
                    public void next(List<PostBean> data) {
                        Log.i("aresult ", data.get(0).getContent());
                        callback.onLoadPostList(data);
                    }

                    @Override
                    public void error(String msg) {
                        Log.i("aresult ", msg);
                        callback.onFailure(msg);
                    }
                }
        );
    }

    public void postDetail(final PostDetailCallback callback, int postId) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().getPostDetail(postId),
                new BaseSubscriber<PostBean>() {
                    @Override
                    public void next(PostBean data) {
                        callback.onLoadPost(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onFailure(msg);
                    }
                }
        );
    }

//    发帖模块
    public void createPost(final CreatePostCallback callback, String content, String imgName) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().createPost(content, imgName),
                new BaseSubscriber<CreatePostBean>() {
                    @Override
                    public void next(CreatePostBean data) {
                        callback.onCreatePostSuccess(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onCreatePostFailure(msg);
                    }
                }
        );
    }

    public void putPostImg(final PutImgCallback callback, String uri) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().putPostImg(uri),
                new BaseSubscriber<PostImgBean>() {

                    @Override
                    public void next(PostImgBean data) {
                        callback.onPutSuccess(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onPutFailure(msg);
                    }
                }
        );
    }

    public void createComment(final CreateCommentCallback callback, int postId, String content) {
        rxJavaExecuter.execute(
                CookRepository.getInstance().createComment(postId, content),
                new BaseSubscriber<CreateCommentBean>() {

                    @Override
                    public void next(CreateCommentBean data) {
                        callback.onCreateCommentSuccess(data);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onCreateCommentFailure(msg);
                    }
                }
        );
    }

    public void like(final int postId) {
        rxJavaExecuter.execute(CookRepository.getInstance().like(postId),
                new BaseSubscriber<EmptyBean>() {

                    @Override
                    public void next(EmptyBean data) {

                    }

                    @Override
                    public void error(String msg) {

                    }
                }
        );
    }

    public void watch(final WatchCallback callback, final int watchedId, final int postId) {
        rxJavaExecuter.execute(CookRepository.getInstance().watch(watchedId),
                new BaseSubscriber<EmptyBean>() {

                    @Override
                    public void next(EmptyBean data) {
                        callback.onWatchSuccess(data, postId);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onWatchFailure(msg);
                    }
                }
        );
    }

    public void unwatch(final UnWatchCallback callback, final int watchedId, final int postId) {
        rxJavaExecuter.execute(CookRepository.getInstance().unwatch(watchedId),
                new BaseSubscriber<EmptyBean>() {

                    @Override
                    public void next(EmptyBean data) {
                        callback.onUnWatchSuccess(data, postId);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onUnWatchFailure(msg);
                    }
                }
        );
    }

    public void myPostList(final MyPostCallback callback) {

    }

    @Override
    public void destroy() {
    }
}
