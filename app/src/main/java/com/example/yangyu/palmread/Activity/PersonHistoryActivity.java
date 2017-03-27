package com.example.yangyu.palmread.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.yangyu.palmread.Fragment.NewsHistoryFragment;
import com.example.yangyu.palmread.Fragment.VideoHistoryFragment;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.NewsHistoryDbUtils;
import com.example.yangyu.palmread.Util.ToastUtils;
import com.example.yangyu.palmread.Util.VideoHistoryDbUtils;

/**
 * Created by yangyu on 17/3/7.
 */

public class PersonHistoryActivity extends AppCompatActivity {

    private TextView mNewsHistory;
    private TextView mVideoHistory;
    private View toolBar;
    private TextView title;
    private ImageView back;
    private ImageView menu;
    private boolean isNewFragment;

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
        menu = (ImageView) toolBar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);
        menu.setOnClickListener(mMenuListener);
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
        isNewFragment=true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private View.OnClickListener mMenuListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopupMenu popupMenu=new PopupMenu(PersonHistoryActivity.this,menu);
            popupMenu.getMenuInflater().inflate(R.menu.history_right_top_menu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.clear:
                            if (isNewFragment){
                                NewsHistoryDbUtils.deleteAllDataNewCollect(PersonHistoryActivity.this);
                            }else{
                                VideoHistoryDbUtils.deleteAllDataVideoHistory(PersonHistoryActivity.this);
                            }
                            ToastUtils.TipToast(PersonHistoryActivity.this,"清除成功");
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    };

    private View.OnClickListener mNewsHistoryListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mNewsHistory.setSelected(true);
            mVideoHistory.setSelected(false);
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            ft.replace(R.id.history_content,new NewsHistoryFragment());
            ft.commit();
            isNewFragment=true;
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
            isNewFragment=false;
        }
    };

}
