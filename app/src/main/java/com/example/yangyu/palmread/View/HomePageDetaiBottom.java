package com.example.yangyu.palmread.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yangyu.palmread.Models.GetHomePageresult;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.NewsCollectDbUtils;
import com.example.yangyu.palmread.Util.ToastUtils;

/**
 * Created by yangyu on 2017/2/4.
 */

public class HomePageDetaiBottom extends LinearLayout {

    public ImageView mGoPerson;
    public ImageView mCollect;
    public ImageView mShare;
    private GetHomePageresult.PageData mData;
    private boolean isFavorite;
    public HomePageDetaiBottom(Context context) {
        this(context,null);
    }

    public HomePageDetaiBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_homepage_bottom,this);
        mGoPerson = (ImageView)findViewById(R.id.person);
        mCollect=(ImageView) findViewById(R.id.home_detail_bottom_shoucang);
        mShare=(ImageView) findViewById(R.id.home_detail_bottom_share);
        mGoPerson.setOnClickListener(mGoPersonListener);
        mCollect.setOnClickListener(mCollectListener);
    }
    private OnClickListener mGoPersonListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtils.TipToast(getContext(),"跳转到个人中心");
        }
    };

    private OnClickListener mCollectListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCollect.isSelected()){
                NewsCollectDbUtils.deleteDataNewCollect(getContext(),mData);
                ToastUtils.TipToast(getContext(),"取消收藏");
                mCollect.setSelected(false);
            }else{
                long raw= NewsCollectDbUtils.insertDataNewCollect(getContext(),mData);
                if (raw!=-1) {
                    ToastUtils.TipToast(getContext(), "收藏成功");
                    mCollect.setSelected(true);
                }else{
                    ToastUtils.TipToast(getContext(),"收藏失败");
                    mCollect.setSelected(false);
                }
            }
        }
    };

    private OnClickListener mShareListener=new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public void setmData(GetHomePageresult.PageData mData) {
        this.mData = mData;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
        if (isFavorite){
            mCollect.setSelected(true);
        }else {
            mCollect.setSelected(false);
        }
    }
}
