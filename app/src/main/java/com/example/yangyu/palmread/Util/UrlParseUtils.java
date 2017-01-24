package com.example.yangyu.palmread.Util;

/**
 * Created by yangyu on 2017/1/10.
 */

public class UrlParseUtils {
    private static final String BASE_URl="http://v.juhe.cn/toutiao/index?type=%s&key=47cc8f967fe4d63c4ba46d41220b4367";
    private static final String BASE_URl_VIDEO="http://newapi.meipai.com/output/channels_topics_timeline.json?id=%d";
//    private String VIDEO_DETAIL_PAGE=BASE_URl_VIDEO+"&page=%d";
    public static String onStringParseUrl(String index){
        return String.format(BASE_URl,index);
    }
    public static String getVideoDetailUrl(int index){
        return String.format(BASE_URl_VIDEO,index);
    }
    public static String getVideoDetailUrlPage(int index,int page){
        String VIDEO_DETAIL_PAGE=getVideoDetailUrl(index)+"&page=%d";
        return String.format(VIDEO_DETAIL_PAGE,page);
    }
}
