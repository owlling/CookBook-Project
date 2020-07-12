package com.owlling.cookbook.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.owlling.cookbook.IView.ICookListView;
import com.owlling.cookbook.R;
import com.owlling.cookbook.model.entity.cookentity.CookDetail;
import com.owlling.cookbook.model.entity.tb_cook.TB_CustomCategory;
import com.owlling.cookbook.presenter.CookListPresenter;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.AboutActivity;
import com.owlling.cookbook.ui.activity.CookCategoryActivity;
import com.owlling.cookbook.ui.activity.CookCollectionListActivity;
import com.owlling.cookbook.ui.adapter.CookListAdapter;
import com.owlling.cookbook.ui.component.fab_transformation.FabTransformation;
import com.owlling.cookbook.ui.component.twinklingrefreshlayout.Footer.BottomProgressView;
import com.owlling.cookbook.ui.component.twinklingrefreshlayout.PeRefreshLayout.PeRefreshLayout;
import com.owlling.cookbook.ui.component.twinklingrefreshlayout.PeRefreshLayout.PeRefreshLayoutListener;
import com.owlling.cookbook.ui.component.twinklingrefreshlayout.RefreshListenerAdapter;
import com.owlling.cookbook.ui.component.twinklingrefreshlayout.TwinklingRefreshLayout;
import com.owlling.cookbook.ui.component.twinklingrefreshlayout.header.bezierlayout.BezierLayout;
import com.owlling.cookbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class CookListFragment extends BaseFragment implements
        ICookListView
        , PeRefreshLayoutListener {

    @Bind(R.id.refreshLayout_data)
    public PeRefreshLayout peRefreshLayout;

    @Bind(R.id.view_overlay)
    public View viewOverlay;
    @Bind(R.id.fab_app)
    public FloatingActionButton floatingActionButton;
    @Bind(R.id.view_sheet)
    public View viewSheet;

    public TwinklingRefreshLayout twinklingRefreshLayout;
    public RecyclerView recyclerList;
    private CookListAdapter cookListAdapter;

    private TB_CustomCategory customCategoryData;
    private CookListPresenter cookListPresenter;

    @Override
    protected Presenter getPresenter() {
        return cookListPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cook_list;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        peRefreshLayout.setPeRefreshLayoutListener(this);
        twinklingRefreshLayout = peRefreshLayout.getTwinklingRefreshLayout();
        recyclerList = peRefreshLayout.getRecyclerView();
        // 首页feed流卡片显示数量
        final GridLayoutManager glm = new GridLayoutManager(this.getContext(), 2);
        recyclerList.setLayoutManager(glm);
        cookListAdapter = new CookListAdapter(getActivity());
        recyclerList.setAdapter(cookListAdapter);

        BezierLayout headerView = new BezierLayout(getActivity());
        headerView.setRoundProgressColor(getResources().getColor(R.color.colorPrimary));
        headerView.setWaveColor(getResources().getColor(R.color.main_indicator_bg));
        headerView.setRippleColor(getResources().getColor(R.color.white));
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setWaveHeight(140);

        BottomProgressView bottomProgressView = new BottomProgressView(twinklingRefreshLayout.getContext());
        bottomProgressView.setAnimatingColor(getResources().getColor(R.color.colorPrimary));
        twinklingRefreshLayout.setBottomView(bottomProgressView);
        twinklingRefreshLayout.setOverScrollBottomShow(true);

        final ArrayList<CookDetail> datas = new ArrayList<>();
        cookListAdapter.setDataList(datas);

        cookListPresenter = new CookListPresenter(getActivity(), this);
        if (customCategoryData != null){
            cookListPresenter.updateRefreshCookMenuByID(customCategoryData.getCtgId());
        }

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                cookListPresenter.updateRefreshCookMenuByID(customCategoryData.getCtgId());
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                cookListPresenter.loadMoreCookMenuByID(customCategoryData.getCtgId());
            }
        });
    }

    @OnClick(R.id.fab_app)
    public void onClickFabApp() {
        if (floatingActionButton.getVisibility() == View.VISIBLE) {
            FabTransformation.with(floatingActionButton).setOverlay(viewOverlay).transformTo(viewSheet);
        }
    }

    @OnClick(R.id.view_overlay)
    public void onClickOverlay() {
        closeFabMenu();
    }

    @OnClick(R.id.relative_category)
    public void onClickCategory() {
        CookCategoryActivity.startActivity(getActivity());
        onClickOverlay();
    }

    @OnClick(R.id.relative_collection)
    public void onClickCollection() {

        CookCollectionListActivity.startActivity(getActivity());
        onClickOverlay();
    }

    @OnClick(R.id.relative_about)
    public void onClickAbout() {

        AboutActivity.startActivity(getActivity());
        onClickOverlay();
    }

    @Override
    public void onCookListUpdateRefreshSuccess(ArrayList<CookDetail> list) {

//        CookUtils.filterCook(list);
        if (peRefreshLayout.isShowDataView()) {
            peRefreshLayout.setModeList();
        }

        twinklingRefreshLayout.finishRefreshing();
        cookListAdapter.setDataList(conversion(list));
        cookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCookListUpdateRefreshFaile(String msg) {

        if (peRefreshLayout.isShowDataView()) {
            peRefreshLayout.setModeException(msg);
            return;
        }

        twinklingRefreshLayout.finishRefreshing();
        ToastUtil.showToast(getActivity(), msg);
    }

    @Override
    public void onCookListLoadMoreSuccess(ArrayList<CookDetail> list) {
        twinklingRefreshLayout.finishLoadmore();
        cookListAdapter.addItems(conversion(list));
    }

    @Override
    public void onCookListLoadMoreFaile(String msg) {
        twinklingRefreshLayout.finishLoadmore();
        ToastUtil.showToast(getActivity(), msg);
    }

    @Override
    public void onPeRefreshLayoutClick() {
        cookListPresenter.updateRefreshCookMenuByID(customCategoryData.getCtgId());
    }

    private List<CookDetail> conversion(ArrayList<CookDetail> list) {
        List<CookDetail> datas = new ArrayList<>();
        for (CookDetail item : list) {
            datas.add(item);
        }

        return datas;
    }

    public boolean closeFabMenu() {
        if (floatingActionButton.getVisibility() != View.VISIBLE) {
            FabTransformation.with(floatingActionButton).setOverlay(viewOverlay).transformFrom(viewSheet);
            return true;
        }

        return false;
    }

    public void setCustomCategoryData(TB_CustomCategory customCategoryData) {
        this.customCategoryData = customCategoryData;
    }
}
