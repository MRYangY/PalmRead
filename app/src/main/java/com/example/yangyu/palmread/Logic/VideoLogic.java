package com.example.yangyu.palmread.Logic;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by yangyu on 2017/1/19.
 */

public class VideoLogic {
    public static void getVideoNoPage(String url, Callback responseCallback){
        OkHttpClient client=new OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90,TimeUnit.SECONDS)
                .build();
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(responseCallback);
    }
}
