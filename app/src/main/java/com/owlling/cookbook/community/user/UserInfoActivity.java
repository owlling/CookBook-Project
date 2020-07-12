package com.owlling.cookbook.community.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.acc.UserPresenter;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.BaseActivity;
import com.owlling.cookbook.utils.GlideUtil;
import com.owlling.cookbook.utils.PathUtil;
import com.owlling.cookbook.utils.PrefUtil;
import com.owlling.cookbook.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserInfoActivity extends BaseActivity
        implements UpdateInfoCallback,
        PutProfileCallback {
    private static final String TAG = "UserInfoActivity";
    private static final int REQUEST_CODE_CHOOSE = 111;

    @Bind(R.id.commonToolbar)
    Toolbar commonToolbar;
    @Bind(R.id.profile)
    CircleImageView profile;
    @Bind(R.id.etNickname)
    EditText etNickname;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.btnCommit)
    Button btnCommit;

    private UserPresenter presenter;
    private String updatingProfileName = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected Presenter getPresenter() {
        presenter = new UserPresenter(this);
        return presenter;
    }

    //用户资料修改模块
    @Override
    protected void init(Bundle savedInstanceState) {
        setSupportActionBar(commonToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("编辑资料");
        }
        updateProfile();
    }

    public static void start(Context context) {
        final Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    //修改资料触发的点击事件监听
    @OnClick({R.id.profile, R.id.btnCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile:
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

                break;
            case R.id.btnCommit:
                presenter.updateInfo(this, updatingProfileName, etNickname.getText().toString(), etPwd.getText().toString());
                break;
        }
    }

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
            presenter.putProfile(this, PathUtil.getRealPathFromURI(context, mSelected.get(0)));
            Log.i(TAG, "mSelected: " + mSelected);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSuccess() {
        Log.i(TAG, "onSuccess");
        updateProfile();
        ToastUtil.showToast(this, "个人资料修改成功");
    }

    private void updateProfile() {
        GlideUtil.loadProfile(profile, PrefUtil.getUid());
    }

    @Override
    public void onFailure(String msg) {
        Log.i(TAG, "onFailure : " + msg);
        ToastUtil.showToast(this, "更改信息失败： " + msg);
    }

    @Override
    public void onPutSuccess(ProfileBean profile) {
        updatingProfileName = profile.profileName;
        Log.i(TAG, "p nam " + profile.profileName);
        ToastUtil.showToast(this, "头像已上传, 点击确定即时更新");
    }

    @Override
    public void onPutFailure(String msg) {
        ToastUtil.showToast(this, "发生错误： " + msg);
    }
}
