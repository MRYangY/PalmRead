package com.example.yangyu.palmread.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yangyu on 17/3/18.
 */

public class SharePreferenceUtils {

    public static void setStringData(Context context,String data,String tag){
        SharedPreferences sp = context.getSharedPreferences("PalmRead_SP", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(tag,data);
        edit.apply();
    }

    public static void setBoolean(Context context,boolean data,String tag){
        SharedPreferences sp = context.getSharedPreferences("PalmRead_SP", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(tag,data);
        edit.apply();
    }

    public static String getStringData(Context context,String tag){
        SharedPreferences sp = context.getSharedPreferences("PalmRead_SP", Context.MODE_PRIVATE);
        return sp.getString(tag,null);
    }

    public static boolean getBoolean(Context context,String tag){
        SharedPreferences sp = context.getSharedPreferences("PalmRead_SP", Context.MODE_PRIVATE);
        return sp.getBoolean(tag,false);
    }
}
