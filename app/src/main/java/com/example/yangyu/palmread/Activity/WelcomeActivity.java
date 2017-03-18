package com.example.yangyu.palmread.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.yangyu.palmread.R;

import java.lang.ref.WeakReference;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by yangyu on 17/3/10.
 */

public class WelcomeActivity extends AppCompatActivity {
    private static final class MyHandler extends Handler{
        private final WeakReference<WelcomeActivity> mActivity;

        private MyHandler(WelcomeActivity mActivity) {
            this.mActivity = new WeakReference<WelcomeActivity>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }


    }
    private final MyHandler myHandler=new MyHandler(this);
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
            finish();
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ShareSDK.initSDK(this);
        myHandler.postDelayed(runnable,3000);
    }

}
