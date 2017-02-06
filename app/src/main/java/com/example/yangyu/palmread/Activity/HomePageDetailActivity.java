package com.example.yangyu.palmread.Activity;

import android.content.Intent;
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
import com.example.yangyu.palmread.Models.GetHomePageresult;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.View.HomePageDetaiBottom;

/**
 * Created by yangyu on 2017/1/16.
 */

public class HomePageDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private GetHomePageresult.PageData mData;
    private WebView mWebView;
    private ImageView mBack;
    private TextView mEditor;
    private View mTopBar;
    private String detail_url;
    private String editorName;
    private ProgressBar mProgressBar;
    private HomePageDetaiBottom mDetailBottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_detail);
        Intent intent=getIntent();
        mData = intent.getParcelableExtra(ProjectContent.EXTRA_HOME_FRAGMENT_RESULT);
        setData(mData);
        mWebView = (WebView)findViewById(R.id.news_web);
        mDetailBottom = (HomePageDetaiBottom) findViewById(R.id.detail_bottom);
        mProgressBar = (ProgressBar)findViewById(R.id.progress);
        mTopBar = findViewById(R.id.top_bar);
        mBack = (ImageView)mTopBar.findViewById(R.id.back);
        mEditor = (TextView)mTopBar.findViewById(R.id.editor);
        mWebView.loadUrl(detail_url);
        mWebView.setWebViewClient(mClient);
        mWebView.setWebChromeClient(mChromeClient);
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
    private void setData(GetHomePageresult.PageData result){
        if(result!=null){
            detail_url = result.mContent;
            editorName = result.mAutorName;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mEditor.setText(editorName);
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
