package com.owlling.cookbook.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.owlling.cookbook.community.post.PostFragment;
import com.owlling.cookbook.community.user.UserFragment;
import com.owlling.cookbook.ui.fragment.MainPageFragment;
import com.owlling.cookbook.ui.refactor.CookCategoryFragment;

import java.util.ArrayList;
import java.util.List;
public class HomePageViewPageAdapter extends FragmentPagerAdapter {

    private static List<Fragment> fragments = new ArrayList<>();
    static {
        fragments.add(new MainPageFragment());
        fragments.add(new CookCategoryFragment());
        fragments.add(new PostFragment());
        fragments.add(new UserFragment());
    }

    public HomePageViewPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public long getItemId(int position) {
        int hashCode = fragments.get(position).hashCode();
        return hashCode;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }
}
