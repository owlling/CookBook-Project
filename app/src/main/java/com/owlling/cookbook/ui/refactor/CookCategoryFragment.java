package com.owlling.cookbook.ui.refactor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.owlling.cookbook.R;
import com.owlling.cookbook.model.manager.CookCategoryManager;
import com.owlling.cookbook.presenter.Presenter;
import com.owlling.cookbook.ui.activity.CookCategoryActivity;
import com.owlling.cookbook.ui.activity.CookListActivity;
import com.owlling.cookbook.ui.adapter.CookCategoryFirAdapter;
import com.owlling.cookbook.ui.adapter.CookCategorySndAdapter;
import com.owlling.cookbook.ui.fragment.BaseFragment;

import butterknife.Bind;

public class CookCategoryFragment extends BaseFragment implements
        CookCategoryFirAdapter.OnCookCategoryFirListener
        , CookCategorySndAdapter.OnCookCategorySndListener {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.recyclerview_list_category)
    public RecyclerView recyclerListCategory;
    @Bind(R.id.recyclerview_list_content)
    public RecyclerView recyclerListContent;

    private CookCategoryFirAdapter cookCategoryFirAdapter;
    private CookCategorySndAdapter cookCategorySndAdapter;

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cook_category;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init(savedInstanceState);
    }

    protected void init(Bundle savedInstanceState) {

        cookCategoryFirAdapter = new CookCategoryFirAdapter(this);
        cookCategoryFirAdapter.setDataList(CookCategoryFirAdapter.createDatas(CookCategoryManager.getInstance().getCategoryFirDatas()));

        recyclerListCategory.setLayoutManager(new LinearLayoutManager(recyclerListCategory.getContext()));
        recyclerListCategory.setAdapter(cookCategoryFirAdapter);

        cookCategorySndAdapter = new CookCategorySndAdapter(this);
        cookCategorySndAdapter.setDataList(
                CookCategorySndAdapter.createDatas(
                        CookCategoryManager.getInstance().getCategorySndDatas(CookCategoryManager.getInstance().getCategoryFirDatas().get(0).getCtgId())
                )
        );
        recyclerListContent.setLayoutManager(new LinearLayoutManager(recyclerListCategory.getContext()));
        recyclerListContent.setAdapter(cookCategorySndAdapter);
    }

    @Override
    public void onCookCategoryFirClick(String ctgId) {
        cookCategorySndAdapter.setDataList(
                CookCategorySndAdapter.createDatas(
                        CookCategoryManager.getInstance().getCategorySndDatas(ctgId)
                )
        );
    }

    @Override
    public void onCookCategorySndClick(String ctgId, String name) {
        CookListActivity.startActivity(this.getActivity(), ctgId, name);
        //finish();
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, CookCategoryActivity.class);
        activity.startActivity(intent);
    }

}
