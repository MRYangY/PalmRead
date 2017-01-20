package com.example.yangyu.palmread.Logic;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangyu on 2017/1/10.
 */

public class HomeCollectionLogic {
    public static void getNetData(String url, Callback responseCallback,String key,Handler handler){
//        Log.e("Test", "getNetData: "+url);
        Object memoryCache = WebCache.loadMemoryCache(key);
        if(memoryCache!=null){
            Message message=new Message();
            message.obj=memoryCache;
            message.what=3;
            handler.sendMessage(message);
        }else{
            OkHttpClient mClient=new OkHttpClient.Builder()
//                .addNetworkInterceptor(new CacheIntercepter())
//                .cache(new Cache(context.getCacheDir(), 10240*1024))
                    .readTimeout(90, TimeUnit.SECONDS)
                    .build();
            Request mRequest=new Request.Builder().url(url).build();
            mClient.newCall(mRequest).enqueue(responseCallback);
        }

    }

    static class CacheIntercepter implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response proceed = chain.proceed(request);
            Response response1 = proceed.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    //cache for 30 days
                    .header("Cache-Control", "max-age=" + 3600)
                    .build();
            return response1;
        }
    }
}
