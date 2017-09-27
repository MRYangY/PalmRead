package com.example.yangyu.palmread.Logic;

/**
 * Created by yangyu on 17/9/26.
 */

public class NdkHelper {


    static {
        System.loadLibrary("palmread-lib");
    }

    public static native String GetStringFromC(String str);
}
