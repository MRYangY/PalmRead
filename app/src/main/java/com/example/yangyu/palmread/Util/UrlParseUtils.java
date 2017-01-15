package com.example.yangyu.palmread.Util;

/**
 * Created by yangyu on 2017/1/10.
 */

public class UrlParseUtils {
    private static final String BASE_URl="http://v.juhe.cn/toutiao/index?type=%s&key=47cc8f967fe4d63c4ba46d41220b4367";
    public static String onStringParseUrl(String index){
        return String.format(BASE_URl,index);
    }
}
