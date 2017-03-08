package com.example.yangyu.palmread.View;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yangyu.palmread.Activity.VideoDetailActivity;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.Models.GetVideoResult;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.ToastUtils;
import com.example.yangyu.palmread.Util.VideoCollectDbUtils;
import com.example.yangyu.palmread.Util.VideoHistoryDbUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by yangyu on 2017/1/18.
 */

public class VideoItemBottom extends RelativeLayout implements View.OnClickListener {

    public SimpleDraweeView mUserPhoto;
    public TextView mUserName;
    public TextView mLikeCount;
    public TextView mCommentsCount;
    public ImageView mTool;

    public VideoItemBottom(Context context) {
        super(context);
        initView(context);
    }

    public VideoItemBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_videoitem_bottom,this);
        mUserPhoto = (SimpleDraweeView) findViewById(R.id.user_photo);
        mUserName = (TextView) findViewById(R.id.user_name);
        mLikeCount = (TextView) findViewById(R.id.like_count);
        mCommentsCount = (TextView) findViewById(R.id.comments_count);
        mTool = (ImageView) findViewById(R.id.tool);
        mLikeCount.setOnClickListener(this);
        mCommentsCount.setOnClickListener(this);
        mTool.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch(v.getId()){
            case R.id.like_count:
                likeCommentAction(v);
                break;
            case R.id.comments_count:
                likeCommentAction(v);
                break;
            case R.id.tool:
                PopupMenu popupMenu=new PopupMenu(getContext(),mTool);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.collect:
                                GetVideoResult result=(GetVideoResult)v.getTag();
                                long raw= VideoCollectDbUtils.insertDataVideoCollect(getContext(),result);
                                if (raw!=-1){
                                    ToastUtils.TipToast(getContext(),"收藏成功");
                                }else{
                                    ToastUtils.TipToast(getContext(),"收藏失败，可能已经收藏过了哦~");
                                }
                                break;
                            case R.id.share:
                                ToastUtils.TipToast(getContext(),"分享");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                break;
        }
    }

    private void likeCommentAction(View v){
        GetVideoResult result=(GetVideoResult)v.getTag();
        VideoHistoryDbUtils.insertDataVideoHistory(getContext(),result);
        Intent intent=new Intent(getContext(), VideoDetailActivity.class);
        intent.putExtra(ProjectContent.EXTRA_VIDEO_URL,result.getUrl());
        intent.putExtra(ProjectContent.EXTRA_VIDEO_USER_NAME,result.getScreen_name());
        getContext().startActivity(intent);
    }
//    private void initData(){
//        initRect();
//    }

//    private void setRect() {
//        mToolRect.right=mViewWidth-mPaddingMiddle;
//        mToolRect.left=mToolRect.right-mToolWidth;
//        mToolRect.top=(mViewHeight-mToolHeight)/2;
//        mToolRect.bottom=mToolRect.top+mToolHeight;
//
//        mCommentsCountRect.right=mToolRect.left-mPaddingMiddle;
//        mCommentsCountRect.left=mCommentsCountRect.right-mCommentsCountWidth;
//        mCommentsCountRect.top=(mViewHeight-mCommentsCountHeight)/2;
//        mCommentsCountRect.bottom=mCommentsCountRect.top+mCommentsCountHeight;
//
//        mLikeCountRect.right=mCommentsCountRect.left-mPaddingLarge;
//        mLikeCountRect.left=mLikeCountRect.right-mLikeCountWidth;
//        mLikeCountRect.top=(mViewHeight-mLikeCountHeight)/2;
//        mLikeCountRect.bottom=mLikeCountRect.top+mLikeCountHeight;
//
//        mUserPhotoRect.left=mPaddingMiddle;
//        mUserPhotoRect.right=mUserPhotoRect.left+mUserPhotoWidth;
//        mUserPhotoRect.top=(mViewHeight-mUserPhotoHeight)/2;
//        mUserPhotoRect.bottom=mUserPhotoRect.top+mUserPhotoHeight;
//
//        mUserNameRect.left=mUserPhotoRect.right+mPaddingLittle;
//        mUserNameRect.right=mUserNameRect.left+mUserNameWidth;
//        mUserNameRect.top=(mViewHeight-mUserNameHeight)/2;
//        mUserNameRect.bottom=mUserNameRect.top+mUserNameHeight;
//    }
//
//    private void initRect() {
//        mUserPhotoRect=new Rect();
//        mUserNameRect=new Rect();
//        mLikeCountRect=new Rect();
//        mCommentsCountRect=new Rect();
//        mToolRect=new Rect();
//    }
//
//    private void initMeasure(){
//        mPaddingLittle=getResources().getDimensionPixelSize(R.dimen.PaddingLittle);
//        mPaddingMiddle=getResources().getDimensionPixelSize(R.dimen.PaddingMiddle);
//        mPaddingLarge=getResources().getDimensionPixelSize(R.dimen.PaddingLarge);
//
//        mTool.measure(MeasureSpec.makeMeasureSpec(-1,MeasureSpec.UNSPECIFIED)
//                ,MeasureSpec.makeMeasureSpec(-1,MeasureSpec.UNSPECIFIED));
//        mToolWidth=mTool.getMeasuredWidth();
//        mToolHeight=mTool.getMeasuredHeight();
//
//        mCommentsCount.measure(MeasureSpec.makeMeasureSpec(-1,MeasureSpec.UNSPECIFIED)
//                ,MeasureSpec.makeMeasureSpec(-1,MeasureSpec.UNSPECIFIED));
//        mCommentsCountWidth=mCommentsCount.getMeasuredWidth();
//        mCommentsCountHeight=mCommentsCount.getMeasuredHeight();
//
//        mLikeCount.measure(MeasureSpec.makeMeasureSpec(-1,MeasureSpec.UNSPECIFIED)
//                ,MeasureSpec.makeMeasureSpec(-1,MeasureSpec.UNSPECIFIED));
//        mLikeCountWidth=mLikeCount.getMeasuredWidth();
//        mLikeCountHeight=mLikeCount.getMeasuredHeight();
//        mUserName.measure(MeasureSpec.makeMeasureSpec(-1,MeasureSpec.UNSPECIFIED)
//                ,MeasureSpec.makeMeasureSpec(-1,MeasureSpec.UNSPECIFIED));
//        mUserNameWidth=mUserName.getMeasuredWidth();
//        mUserNameHeight=mUserName.getMeasuredHeight();
//
//        mUserPhotoHeight=mUserNameHeight+mPaddingLittle;
//        mUserPhotoWidth=mUserNameHeight+mPaddingLittle;
//
//    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
////        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        initMeasure();
//        mViewWidth=MeasureSpec.getSize(widthMeasureSpec);
//        mViewHeight=MeasureSpec.getSize(heightMeasureSpec);
//        mTool.measure(MeasureSpec.makeMeasureSpec(mToolWidth,MeasureSpec.EXACTLY)
//                ,MeasureSpec.makeMeasureSpec(mToolHeight,MeasureSpec.EXACTLY));
//        mCommentsCount.measure(MeasureSpec.makeMeasureSpec(mCommentsCountWidth,MeasureSpec.EXACTLY)
//                ,MeasureSpec.makeMeasureSpec(mCommentsCountHeight,MeasureSpec.EXACTLY));
//        mLikeCount.measure(MeasureSpec.makeMeasureSpec(mLikeCountWidth,MeasureSpec.EXACTLY)
//                ,MeasureSpec.makeMeasureSpec(mLikeCountHeight,MeasureSpec.EXACTLY));
//        mUserPhoto.measure(MeasureSpec.makeMeasureSpec(mUserPhotoWidth,MeasureSpec.EXACTLY)
//                ,MeasureSpec.makeMeasureSpec(mUserPhotoHeight,MeasureSpec.EXACTLY));
//        mUserName.measure(MeasureSpec.makeMeasureSpec(mUserNameWidth,MeasureSpec.EXACTLY)
//                ,MeasureSpec.makeMeasureSpec(mUserNameHeight,MeasureSpec.EXACTLY));
//        setMeasuredDimension(mViewWidth,mViewHeight);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        setRect();
//        mTool.layout(mToolRect.left,mToolRect.top,mToolRect.right,mToolRect.bottom);
//        mCommentsCount.layout(mCommentsCountRect.left,mCommentsCountRect.top,mCommentsCountRect.right,mCommentsCountRect.bottom);
//        mLikeCount.layout(mLikeCountRect.left,mLikeCountRect.top,mLikeCountRect.right,mLikeCountRect.bottom);
//        mUserPhoto.layout(mUserPhotoRect.left,mUserPhotoRect.top,mUserPhotoRect.right,mUserPhotoRect.bottom);
//        mUserName.layout(mUserNameRect.left,mUserNameRect.top,mUserNameRect.right,mUserNameRect.bottom);
//    }
}
