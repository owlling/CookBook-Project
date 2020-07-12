package com.owlling.cookbook.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.owlling.cookbook.R;
import com.owlling.cookbook.model.entity.tb_cook.TB_CustomCategory;
import com.owlling.cookbook.model.manager.CustomCategoryManager;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.CookChannelActivity;
import com.owlling.cookbook.ui.adapter.MainPageViewPageAdapter;
import com.owlling.cookbook.ui.component.magicindicator.MagicIndicator;
import com.owlling.cookbook.ui.component.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.owlling.cookbook.ui.component.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.owlling.cookbook.ui.component.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.owlling.cookbook.ui.component.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.owlling.cookbook.ui.component.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import com.owlling.cookbook.ui.component.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.owlling.cookbook.ui.activity.CookChannelActivity.Request_Code_Channel;


public class MainPageFragment extends BaseFragment implements
        ViewPager.OnPageChangeListener {
    private List<TB_CustomCategory> customCategoryDatas;

    @Bind(R.id.magic_indicator)
    public MagicIndicator magicIndicator;
    @Bind(R.id.view_pager)
    public ViewPager viewPager;

    private CommonNavigator commonNavigator;
    private MainPageViewPageAdapter mainPageViewPageAdapter;

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_page;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initIndicatorView();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        magicIndicator.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        magicIndicator.onPageScrollStateChanged(state);
    }

    @OnClick(R.id.imgv_search)
    public void onClickImgvSearch() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.add(android.R.id.content, new SearchFragment(), "fragment_search");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commitAllowingStateLoss();
    }

    @OnClick(R.id.imgv_add)
    public void onClickChannelAManager() {
        final Intent intent = new Intent(getContext(), CookChannelActivity.class);
        this.startActivityForResult(intent, Request_Code_Channel);
//        CookChannelActivity.startActivity(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("bbb", "sss" + resultCode + "   " + requestCode);
        initIndicatorView();
        mainPageViewPageAdapter.notifyDataSetChanged();
    }

    private void initIndicatorView() {
        customCategoryDatas = CustomCategoryManager.getInstance().getDatas();

        commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return customCategoryDatas == null ? 0 : customCategoryDatas.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(customCategoryDatas.get(index).getName());
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#FB5352"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        mainPageViewPageAdapter = new MainPageViewPageAdapter(getFragmentManager(), customCategoryDatas);
        viewPager.addOnPageChangeListener(this);

        viewPager.setAdapter(mainPageViewPageAdapter);
    }

}
