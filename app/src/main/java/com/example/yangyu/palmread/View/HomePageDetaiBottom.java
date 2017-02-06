package com.example.yangyu.palmread.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.yangyu.palmread.R;

/**
 * Created by yangyu on 2017/2/4.
 */

public class HomePageDetaiBottom extends LinearLayout {

//    public ImageView mGoTop;

    public HomePageDetaiBottom(Context context) {
        this(context,null);
    }

    public HomePageDetaiBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_homepage_bottom,this);
//        mGoTop = (ImageView)findViewById(R.id.go_top);
    }
}
