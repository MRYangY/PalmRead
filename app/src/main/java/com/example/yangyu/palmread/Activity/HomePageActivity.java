package com.example.yangyu.palmread.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.Fragment.HomeCollectionFragment;
import com.example.yangyu.palmread.Fragment.PersonFragment;
import com.example.yangyu.palmread.Fragment.VideoFragment;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.ToastUtils;

import java.lang.ref.WeakReference;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * Created by yangyu on 2017/1/9.
 */

public class HomePageActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    public BottomNavigationBar mBottonMenu;
    private static boolean isLogout=false;
    private boolean isToPerson=false;
    private static final class MyHandler extends Handler{
        private final WeakReference<HomePageActivity> mActivity;

        private MyHandler(HomePageActivity mActivity) {
            this.mActivity = new WeakReference<HomePageActivity>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isLogout=false;
        }
    }
    private MyHandler mHandler=new MyHandler(this);

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case ProjectContent.EXTRA_TO_PERSON:
                    isToPerson=intent.getBooleanExtra("to_person",false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mBottonMenu = (BottomNavigationBar)findViewById(R.id.bottom_navigation);
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter=new IntentFilter();
        filter.addAction(ProjectContent.EXTRA_TO_PERSON);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isToPerson){
            mBottonMenu.selectTab(2);
            isToPerson=false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
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
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyAction=event.getAction();
        int keyCode=event.getKeyCode();
        if (keyAction==KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if (!isLogout) {
                        ToastUtils.TipToast(this,getString(R.string.exit_app));
                        isLogout=true;
                        mHandler.sendEmptyMessageDelayed(0,3000);
                        return false;
                    }else{
                        finish();
                        return true;
                    }
            }
        }
        return super.dispatchKeyEvent(event);
    }

//    private void onCreateExitDialog(){
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setTitle("掌上新闻");
//        builder.setMessage("是否退出掌上新闻？");
//        builder.setCancelable(false);
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                finish();
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.show();
//    }
}
