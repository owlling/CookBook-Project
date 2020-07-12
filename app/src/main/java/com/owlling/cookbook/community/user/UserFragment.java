package com.owlling.cookbook.community.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.acc.UserPresenter;
import com.owlling.cookbook.community.dialog.ConfirmDialog;
import com.owlling.cookbook.community.mine.mycomments.MyCommentActivity;
import com.owlling.cookbook.community.mine.myfans.FansListActivity;
import com.owlling.cookbook.community.mine.mypost.MyPostActivity;
import com.owlling.cookbook.community.mine.mywatch.WatchListActivity;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.AboutActivity;
import com.owlling.cookbook.ui.activity.CookCollectionListActivity;
import com.owlling.cookbook.ui.activity.MainActivity;
import com.owlling.cookbook.ui.fragment.BaseFragment;
import com.owlling.cookbook.utils.GlideUtil;
import com.owlling.cookbook.utils.PrefUtil;
import com.owlling.cookbook.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends BaseFragment implements UserInfoCallback {
    @Bind(R.id.profile)
    CircleImageView profile;
    @Bind(R.id.cardUserInfo)
    FrameLayout cardUserInfo;
    @Bind(R.id.llPost)
    LinearLayout llPost;
    @Bind(R.id.llLike)
    LinearLayout llLike;
    @Bind(R.id.llStar)
    LinearLayout llStar;
    @Bind(R.id.commonToolbar)
    Toolbar commonToolbar;
    @Bind(R.id.tvNickname)
    TextView tvNickname;
    @Bind(R.id.tvUsername)
    TextView tvUsername;
    @Bind(R.id.llFans)
    LinearLayout llFuns;
    @Bind(R.id.llComments)
    LinearLayout llComments;
    @Bind(R.id.llAboutMe)
    LinearLayout llAboutMe;
    @Bind(R.id.llLogout)
    LinearLayout llLogout;
    private UserPresenter presenter = new UserPresenter();

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.userInfo(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter.userInfo(this);
        commonToolbar.setTitle("我的");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //资料修改成功显示内容
    @Override
    public void onSuccess(UserInfoBean userInfo) {
        GlideUtil.loadProfile(profile, PrefUtil.getUid());
        tvNickname.setText(userInfo.nickname);
        tvUsername.setText("ID : " + userInfo.username);
    }

    @Override
    public void onGetUserInfoFailure(String msg) {
        if (getUserVisibleHint()) {
            ToastUtil.showToast(this.getContext(), "获取用户信息失败，请登录");
        }
    }

    @OnClick({R.id.llFans, R.id.llComments, R.id.llAboutMe, R.id.llLogout, R.id.cardUserInfo, R.id.llPost, R.id.llLike, R.id.llStar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardUserInfo:
                UserInfoActivity.start(getContext());
                break;
            case R.id.llPost:
                MyPostActivity.start(this.getActivity());
                break;
            case R.id.llLike:
                WatchListActivity.start(this.getActivity());
                break;
            case R.id.llStar:
                CookCollectionListActivity.startActivity(this.getActivity());
                break;
            case R.id.llFans:
                FansListActivity.start(this.getActivity());
                break;
            case R.id.llComments:
                MyCommentActivity.start(this.getActivity());
                break;
            case R.id.llAboutMe:
                AboutActivity.startActivity(getContext());
                break;
            case R.id.llLogout:
                final ConfirmDialog commentDialog = ConfirmDialog.newInst("确定退出吗");
                commentDialog.setConfirmListener(new ConfirmDialog.ConfirmListener() {
                    @Override
                    public void onClick() {
                        PrefUtil.setLogout();//退出
                        ((MainActivity) getActivity()).tapBottomBar(0);//回到首页
                    }
                });
                commentDialog.show(getFragmentManager(), "comment dialog");
                break;
        }
    }
}
