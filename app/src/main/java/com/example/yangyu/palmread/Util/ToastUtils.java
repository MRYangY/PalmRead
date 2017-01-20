package com.example.yangyu.palmread.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yangyu on 2017/1/16.
 */

public class ToastUtils {
    public static void TipToast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
