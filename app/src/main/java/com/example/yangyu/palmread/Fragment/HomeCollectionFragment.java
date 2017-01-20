package com.example.yangyu.palmread.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.R;

/**
 * Created by yangyu on 2017/1/9.
 */

public class HomeCollectionFragment extends BaseFragment {
    public static final String TAG = HomeCollectionFragment.class.getCanonicalName();
    private View mLayout;
    private TabLayout mTabSort;
    private ViewPager mPager;
    private String[] mTabList;
    private String[] mTabIndex;
    private HomeCollectionAdaptor mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mLayout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_collection, container, false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTabList();
        mPager.setAdapter(mAdapter);
        mTabSort.setupWithViewPager(mPager);
    }

    private void initTabList() {
        mTabList = new String[]{
                "头条"
                , "社会"
                , "国内"
                , "国际"
                , "娱乐"
                , "体育"
                , "军事"
                , "科技"
                , "财经"
                , "时尚"
        };
        mTabIndex = new String[]{
                "top"
                , "shehui"
                , "guonei"
                , "guoji"
                , "yule"
                , "tiyu"
                , "junshi"
                , "keji"
                , "caijing"
                , "shishang"
        };
    }

    private class HomeCollectionAdaptor extends FragmentStatePagerAdapter {
        public HomeCollectionAdaptor(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            HomeFragment fragment = new HomeFragment();
            String index = mTabIndex[position];
            Bundle bundle = new Bundle();
            bundle.putString(ProjectContent.EXTRA_TAB_INDEX, index);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTabList == null ? 0 : mTabList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabList == null ? "" : mTabList[position];
        }
    }

    @Override
    protected void initView() {
        mTabSort = (TabLayout)mLayout.findViewById(R.id.tab_sort);
        mPager = (ViewPager)mLayout.findViewById(R.id.pager_content);
    }

    @Override
    protected void initData() {
        mAdapter = new HomeCollectionAdaptor(getChildFragmentManager());
    }
}
