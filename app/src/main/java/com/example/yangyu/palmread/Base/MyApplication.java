package com.example.yangyu.palmread.Base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by yangyu on 2017/1/16.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
