package com.owlling.cookbook.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.owlling.cookbook.R;
import com.owlling.cookbook.community.acc.LoginActivity;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.adapter.HomePageViewPageAdapter;
import com.owlling.cookbook.ui.fragment.SearchFragment;
import com.owlling.cookbook.utils.PrefUtil;
import com.owlling.cookbook.utils.ToastUtil;
import com.owlling.cookbook.widget.BottomNavigationViewEx;

import java.util.List;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    public static final int LOGIN_REQUEST_CODE = 113;

    private BottomNavigationViewEx bottomBar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setIconVisibility(false);
        bottomBar.setLargeTextSize(16);
        bottomBar.setSmallTextSize(14);
        final ViewPager viewPager = findViewById(R.id.vpHome);//设置4个pager左右可滑
        viewPager.setAdapter(new HomePageViewPageAdapter(getSupportFragmentManager()));//首页菜单项切换
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return onNavigationItemSelectedExt(menuItem);
            }
        });
        bottomBar.setupWithViewPager(viewPager);
    }

    final static int[] menuIds = new int[]{R.id.item_tab1, R.id.item_tab2, R.id.item_tab3, R.id.item_tab4};


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("bbb", "aaa" + resultCode + "   " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) return;
                int result = data.getIntExtra(LoginActivity.RETURN_TO_TAB, -1);
                if (result != -1) {
                    tapBottomBar(result);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    private void updateNavigationBarState(int actionId) {
        Menu menu = bottomBar.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    private boolean doubleBackToExitPressedOnce = false;

    private boolean onNavigationItemSelectedExt(MenuItem menuItem) {
        Log.i(TAG, "onNavigationItemSelected " + menuItem.getItemId());

        hideSearchView();

        if (!PrefUtil.isLogin()) {
            int index = 1;
            if (menuItem.getItemId() == menuIds[2]) {
                index = 2;
            } else if (menuItem.getItemId() == menuIds[3]) {
                index = 3;
            }
            if (index == 2 || index == 3) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra(LoginActivity.RETURN_TO_TAB, index);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
                return false;
            }
        } else {
            updateNavigationBarState(menuItem.getItemId());
        }
        return true;
    }

    private void hideSearchView() {
        List<Fragment> fs = getSupportFragmentManager().getFragments();
        final int size = fs.size();
        if (size > 0) {
            final String fName = fs.get(size - 1).getClass().getSimpleName();
            if (fName.equals(SearchFragment.class.getSimpleName())) {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            ToastUtil.showToast(this, R.string.toast_msg_oncemore_exit);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
//            case : R.id.activity_cook_category
        }

        return false;
    }

    public void tapBottomBar(int index) {
        bottomBar.setSelectedItemId(menuIds[index]);
    }


}
