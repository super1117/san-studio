package com.pdk.re.specified.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 首页fragment适配器
 *
 * @author 郑龙
 */
public class MainActivityFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public MainActivityFragmentAdapter(FragmentManager fm, List<Fragment> mFragment) {

        super(fm);
        this.mFragments = mFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
