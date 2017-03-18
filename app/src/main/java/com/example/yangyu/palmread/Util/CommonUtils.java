package com.example.yangyu.palmread.Util;

import android.content.Context;

import com.example.yangyu.palmread.Models.GetHomePageresult;
import com.example.yangyu.palmread.Models.GetVideoResult;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

/**
 * Created by yangyu on 17/3/17.
 */

public class CommonUtils {
    public static void showShare(final GetHomePageresult.PageData pageresult, Context context) {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("来自掌上新闻APP:"+pageresult.mTitle);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(pageresult.mContent);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(pageresult.mTitle);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(pageresult.mPicOne);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(null);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(pageresult.mContent);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("");
//         site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("掌上新闻");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(null);

// 启动分享GUI
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                if ("QQ".equals(platform.getName())) {
//							paramsToShare.setText(null);
//							paramsToShare.setTitle(null);
//							paramsToShare.setTitleUrl(null);
//							paramsToShare.setHidden(1);
                }
                if ("QZone".equals(platform.getName())) {

                }
                if ("Email".equals(platform.getName())) {
                    paramsToShare.setText("来自掌上新闻APP:"+pageresult.mTitle+pageresult.mContent);
                }
                if ("ShortMessage".equals(platform.getName())){
                    paramsToShare.setText("来自掌上新闻APP:"+pageresult.mTitle+pageresult.mContent);
                }
                if ("Facebook".equals(platform.getName())) {
//							paramsToShare.setImageUrl(null);
//							paramsToShare.setUrl(null);
//							paramsToShare.setShareType(Platform.SHARE_VIDEO);
                }
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setUrl(null);
                    paramsToShare.setImagePath(null);
                    paramsToShare.setImageUrl(pageresult.mPicOne);
                }
                if ("Wechat".equals(platform.getName())) {
                    paramsToShare.setShareType(Platform.SHARE_EMOJI);
                }
                if ("WechatMoments".equals(platform.getName())) {
//							paramsToShare.setShareType(Platform.SHARE_IMAGE);
//							Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//							paramsToShare.setImageData(imageData);
                }

            }
        });
        oks.show(context);
    }
    public static void showShare(final GetVideoResult result, Context context) {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("来自掌上新闻APP:"+result.getCaption());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(result.getUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(result.getCaption());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(result.getCover_pic());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(null);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(result.getUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("");
//         site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("掌上新闻");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(null);

// 启动分享GUI
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                if ("QQ".equals(platform.getName())) {
//							paramsToShare.setText(null);
//							paramsToShare.setTitle(null);
//							paramsToShare.setTitleUrl(null);
//							paramsToShare.setHidden(1);
                }
                if ("QZone".equals(platform.getName())) {

                }
                if ("Email".equals(platform.getName())) {
                    paramsToShare.setText("来自掌上新闻APP:"+result.getCaption()+result.getUrl());
                }
                if ("ShortMessage".equals(platform.getName())){
                    paramsToShare.setText("来自掌上新闻APP:"+result.getCaption()+result.getUrl());
                }
                if ("Facebook".equals(platform.getName())) {
//							paramsToShare.setImageUrl(null);
//							paramsToShare.setUrl(null);
//							paramsToShare.setShareType(Platform.SHARE_VIDEO);
                }
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setUrl(null);
                    paramsToShare.setImagePath(null);
                    paramsToShare.setImageUrl(result.getCover_pic());
                }
                if ("Wechat".equals(platform.getName())) {
                    paramsToShare.setShareType(Platform.SHARE_EMOJI);
                }
                if ("WechatMoments".equals(platform.getName())) {
//							paramsToShare.setShareType(Platform.SHARE_IMAGE);
//							Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//							paramsToShare.setImageData(imageData);
                }

            }
        });
        oks.show(context);
    }
}
