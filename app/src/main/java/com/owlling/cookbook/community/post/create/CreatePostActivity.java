package com.owlling.cookbook.community.post.create;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.post.PostPresenter;
import com.owlling.cookbook.community.post.bean.CreatePostBean;
import com.owlling.cookbook.community.post.bean.PostImgBean;
import com.owlling.cookbook.community.post.callbacks.CreatePostCallback;
import com.owlling.cookbook.community.post.callbacks.PutImgCallback;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.GlideUtil;
import com.owlling.cookbook.utils.PathUtil;
import com.owlling.cookbook.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CreatePostActivity extends BaseActivity implements PutImgCallback,
        CreatePostCallback{

    private static final int REQUEST_CODE_CHOOSE = 112;
    private static final String TAG = "CreatePostActivity";

    @Bind(R.id.ivPostImg)
    ImageView ivPostImg;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.backButton)
    ImageView backButton;
    @Bind(R.id.tvPublish)
    TextView tvPublish;

    private PostPresenter presenter = new PostPresenter(this);
    private String postedImg = null;

    public static void start(Context ctx) {
        final Intent intent = new Intent(ctx, CreatePostActivity.class);//意图对象(上一页，下一页)
        ctx.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_post;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    //帖子发布反馈
    private void createPost() {
        final String content = etContent.getText().toString();
        Log.i(TAG, "createPost: " + content + " img : " + postedImg);
        if (!TextUtils.isEmpty(content)) {
            presenter.createPost(this, content, postedImg);
        } else {
            ToastUtil.showToast(this, "内容不能为空");
        }
    }

    @Override
    public void onPutSuccess(PostImgBean bean) {
        postedImg = bean.postImg;
        GlideUtil.loadPostImg(ivPostImg, postedImg);
    }

    @Override
    public void onPutFailure(String msg) {
        ToastUtil.showToast(this, "图片上传失败");
    }

    @Override
    public void onCreatePostSuccess(CreatePostBean bean) {
        ToastUtil.showToast(this, "发布成功");
        finish();
    }

    @Override
    public void onCreatePostFailure(String msg) {
        ToastUtil.showToast(this, "发布失败：" + msg);
    }

    @OnClick(R.id.ivPostImg)
    public void onViewClicked() {
        readStoragePermission();
    }

    private void readStoragePermission(){
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        showMatisse();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(context, "未获取到读取存储权限");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //图片选择器
    private void showMatisse() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            presenter.putPostImg(this, PathUtil.getRealPathFromURI(context, mSelected.get(0)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backButton, R.id.tvPublish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.tvPublish:
                createPost();
                break;
        }
    }
}
