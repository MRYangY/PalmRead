package com.example.yangyu.palmread.Util;

import android.os.Handler;
import android.os.Message;

import com.example.yangyu.palmread.Models.GetVideoResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by yangyu on 2017/1/20.
 */

public class JsoupParse{
    public static void htmlParseString(GetVideoResult result, final Handler handler){
        final String url=result.getUrl();
        final String[] content = {""};
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Document document=Jsoup.connect(url).get();
                        content[0] =document.select("meta[property=og:video:url]")
                                .get(0).attr("content");
                        Message message=  new Message();
                        message.obj=content[0];
                        message.what=02;
                        handler.sendMessage(message);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }

}
