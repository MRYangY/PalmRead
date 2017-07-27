package com.example.yangyu.palmread.Logic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONType;
import com.example.yangyu.palmread.Models.GetHomePageresult;
import com.example.yangyu.palmread.Models.GetQQUserInfo;
import com.example.yangyu.palmread.Models.GetVideoResult;

import java.util.ArrayList;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by yangyu on 2017/1/16.
 */

public class HomeJsonToResult {
    public static GetHomePageresult HomePageParse(Class clazz,String result){
        if(clazz.isAnnotationPresent(JSONType.class)){
            Object object= parseObject(result,clazz);
            if(object!=null)return (GetHomePageresult)object;
            return null;
        }
        return null;
    }

    public static ArrayList<GetVideoResult> VideoResultParse(Class clazz,String result){
        Object object= JSON.parseArray(result,clazz);
        if(object!=null)return (ArrayList<GetVideoResult>)object;
        return null;
    }

    public static GetQQUserInfo QQUserInfoParse(Class clazz,String result){
        Object o = JSON.parseObject(result, clazz);
        if (o!=null) return (GetQQUserInfo)o;
        return null;
    }
}
