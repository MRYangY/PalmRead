package com.example.yangyu.palmread.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.yangyu.palmread.Fragment.HomeCollectionFragment;
import com.example.yangyu.palmread.Fragment.PersonFragment;
import com.example.yangyu.palmread.Fragment.VideoFragment;
import com.example.yangyu.palmread.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * Created by yangyu on 2017/1/9.
 */

public class HomePageActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    private BottomNavigationBar mBottonMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mBottonMenu = (BottomNavigationBar)findViewById(R.id.bottom_navigation);
        initData();
    }
    private void initData(){
        mBottonMenu.addItem(new BottomNavigationItem(R.drawable.menu_home_selector,"首页"))
                .addItem(new BottomNavigationItem(R.drawable.menu_video_selector,"短视频"))
                .addItem(new BottomNavigationItem(R.drawable.menu_person_selector,"我的"))
                .initialise();
        mBottonMenu.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        HomeCollectionFragment mHomeCollectionFragment = new HomeCollectionFragment();
        transaction.replace(R.id.content,mHomeCollectionFragment,HomeCollectionFragment.TAG);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm=this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch(position){
            case 0:
                HomeCollectionFragment mHomeCollectionFragment=(HomeCollectionFragment)fm
                        .findFragmentByTag(HomeCollectionFragment.TAG);
                if(mHomeCollectionFragment==null){
                    mHomeCollectionFragment=new HomeCollectionFragment();
                }
                ft.replace(R.id.content,mHomeCollectionFragment,HomeCollectionFragment.TAG);
                break;
            case 1:
                VideoFragment mVideofragment=(VideoFragment)fm.findFragmentByTag(VideoFragment.TAG);
                if(mVideofragment==null){
                    mVideofragment=new VideoFragment();
                }
                ft.replace(R.id.content,mVideofragment,VideoFragment.TAG);
                break;
            case 2:
                PersonFragment mPersonFragment=(PersonFragment)fm.findFragmentByTag(PersonFragment.TAG);
                if(mPersonFragment==null){
                    mPersonFragment=new PersonFragment();
                }
                ft.replace(R.id.content,mPersonFragment,PersonFragment.TAG);
                break;
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
