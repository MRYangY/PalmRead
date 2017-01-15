package com.example.yangyu.palmread.Logic;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangyu on 2017/1/10.
 */

public class HomeCollectionLogic {
    public static String getNetData(String url){
        OkHttpClient mClient=new OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS).build();
        Request mRequest=new Request.Builder().url(url).build();
        try {
            Response response = mClient.newCall(mRequest).execute();
            if(response.isSuccessful()){
                return response.toString();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
