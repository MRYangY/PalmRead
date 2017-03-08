package com.example.yangyu.palmread.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yangyu.palmread.Fragment.NewsCollectedFragment;
import com.example.yangyu.palmread.Fragment.VideoCollectedFragment;
import com.example.yangyu.palmread.R;

/**
 * Created by yangyu on 17/3/7.
 */

public class PersonCollectActivity extends AppCompatActivity {

    private TextView mNewsCollect;
    private TextView mVideoCollect;
    private View toolBar;
    private TextView title;
    private ImageView back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_collect);
        mNewsCollect = (TextView) findViewById(R.id.news_collect);
        mVideoCollect = (TextView) findViewById(R.id.video_collect);
        toolBar=findViewById(R.id.my_collect_top_bar);
        mNewsCollect.setOnClickListener(mNewsCollectListner);
        mVideoCollect.setOnClickListener(mVideoCollectListener);
        title=(TextView) toolBar.findViewById(R.id.editor);
        back = (ImageView) toolBar.findViewById(R.id.back);
        title.setText("我的收藏");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SetData();
    }

    private void SetData() {
        mNewsCollect.setSelected(true);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction ft=manager.beginTransaction();
        ft.replace(R.id.collect_content,new NewsCollectedFragment());
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private View.OnClickListener mNewsCollectListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mNewsCollect.setSelected(true);
            mVideoCollect.setSelected(false);
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            ft.replace(R.id.collect_content,new NewsCollectedFragment());
            ft.commit();
        }
    };

    private View.OnClickListener mVideoCollectListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mNewsCollect.setSelected(false);
            mVideoCollect.setSelected(true);
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            ft.replace(R.id.collect_content,new VideoCollectedFragment());
            ft.commit();
        }
    };

}
