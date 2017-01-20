package com.example.yangyu.palmread.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.R;

/**
 * Created by yangyu on 2017/1/20.
 */

public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private String mUrl;
    private String mName;
    private WebView mWebView;
    private View mTopBar;
    private ProgressBar mProgressBar;
    private ImageView mBack;
    private TextView mEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        mUrl = getIntent().getStringExtra(ProjectContent.EXTRA_VIDEO_URL);
        mName = getIntent().getStringExtra(ProjectContent.EXTRA_VIDEO_USER_NAME);
        mWebView = (WebView)findViewById(R.id.video_web);
        mProgressBar = (ProgressBar)findViewById(R.id.video_progress);
        mTopBar = findViewById(R.id.video_topbar);
        mBack = (ImageView)mTopBar.findViewById(R.id.back);
        mEditor = (TextView)mTopBar.findViewById(R.id.editor);
        mWebView.setWebViewClient(mClient);
        mWebView.setWebChromeClient(mChromeClient);
//        mWebView.setFocusable(true);
//        mWebView.requestFocus();
//        mWebView.setFocusableInTouchMode(true);
//        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
        mBack.setOnClickListener(this);
    }
    private WebViewClient mClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
    private WebChromeClient mChromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress==100){
                mProgressBar.setVisibility(View.GONE);
            }else {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mEditor.setText(mName);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 自动生成的方法存根
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if(mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
            else {
                finish();
            }


        }
        return super.onKeyDown(keyCode, event);
    }
}
