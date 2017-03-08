package com.example.yangyu.palmread.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yangyu.palmread.Fragment.NewsHistoryFragment;
import com.example.yangyu.palmread.Fragment.VideoHistoryFragment;
import com.example.yangyu.palmread.R;

/**
 * Created by yangyu on 17/3/7.
 */

public class PersonHistoryActivity extends AppCompatActivity {

    private TextView mNewsHistory;
    private TextView mVideoHistory;
    private View toolBar;
    private TextView title;
    private ImageView back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_history);
        mNewsHistory = (TextView) findViewById(R.id.news_history);
        mVideoHistory = (TextView) findViewById(R.id.video_history);
        toolBar=findViewById(R.id.my_history_top_bar);
        mNewsHistory.setOnClickListener(mNewsHistoryListner);
        mVideoHistory.setOnClickListener(mVideoHistoryListener);
        title=(TextView) toolBar.findViewById(R.id.editor);
        back = (ImageView) toolBar.findViewById(R.id.back);
        title.setText("浏览历史");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SetData();
    }

    private void SetData() {
        mNewsHistory.setSelected(true);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction ft=manager.beginTransaction();
        ft.replace(R.id.history_content,new NewsHistoryFragment());
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private View.OnClickListener mNewsHistoryListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mNewsHistory.setSelected(true);
            mVideoHistory.setSelected(false);
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            ft.replace(R.id.history_content,new NewsHistoryFragment());
            ft.commit();
        }
    };

    private View.OnClickListener mVideoHistoryListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mNewsHistory.setSelected(false);
            mVideoHistory.setSelected(true);
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            ft.replace(R.id.history_content,new VideoHistoryFragment());
            ft.commit();
        }
    };

}
