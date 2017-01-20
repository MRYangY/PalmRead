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

public class VideoFragment extends BaseFragment {
    public static final String TAG = VideoFragment.class.getCanonicalName();
    //view
    private View mLayout;
    private TabLayout mTabLayout;
    private ViewPager mPager;

    //data
    private String[] mTab;
    private int[] mTabIdex;
    private VideoPagerAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(getActivity()!=null){
            mLayout=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video,container,false);
        }
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTabList();
        mPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mPager);
    }

    private void initTabList(){
        mTab=new String[]{
                "热门",
                "搞笑",
                "明星名人",
                "男神",
                "女神",
                "音乐",
                "舞蹈",
                "美食",
                "美妆",
                "宝宝",
                "萌宠",
                "手工",
                "穿秀",
                "吃秀"
        };
        mTabIdex=new int[]{
                1,13,16,
                31,19,62,
                63,59,27,
                18,6,450,
                460,423
        };
    }

    private class VideoPagerAdapter extends FragmentStatePagerAdapter{

        public VideoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTab==null?"":mTab[position];
        }

        @Override
        public Fragment getItem(int position) {
            int videoIndex=mTabIdex==null?-1:mTabIdex[position];
            VideoFragmentDetail fragmentDetail=new VideoFragmentDetail();
            Bundle bundle=new Bundle();
            bundle.putInt(ProjectContent.EXTRA_VIDEO_INDEX,videoIndex);
            fragmentDetail.setArguments(bundle);
            return fragmentDetail;
        }

        @Override
        public int getCount() {
            return mTab==null?0:mTab.length;
        }
    }
    @Override
    protected void initView() {
        mTabLayout = (TabLayout)mLayout.findViewById(R.id.video_sort);
        mPager = (ViewPager)mLayout.findViewById(R.id.video_content);
    }

    @Override
    protected void initData() {
        mAdapter = new VideoPagerAdapter(getChildFragmentManager());
    }
}
