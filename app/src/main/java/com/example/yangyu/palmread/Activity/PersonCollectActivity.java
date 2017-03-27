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

import com.example.yangyu.palmread.Fragment.NewsCollectedFragment;
import com.example.yangyu.palmread.Fragment.VideoCollectedFragment;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.NewsCollectDbUtils;
import com.example.yangyu.palmread.Util.ToastUtils;
import com.example.yangyu.palmread.Util.VideoCollectDbUtils;

/**
 * Created by yangyu on 17/3/7.
 */

public class PersonCollectActivity extends AppCompatActivity {

    private TextView mNewsCollect;
    private TextView mVideoCollect;
    private View toolBar;
    private TextView title;
    private ImageView back;
    private ImageView menu;

    private boolean isNewsFragment;

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
        menu = (ImageView) toolBar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);
        menu.setOnClickListener(mMenuListener);
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
        isNewsFragment=true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private View.OnClickListener mMenuListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopupMenu popupMenu=new PopupMenu(PersonCollectActivity.this,menu);
            popupMenu.getMenuInflater().inflate(R.menu.history_right_top_menu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.clear:
                            if (isNewsFragment){
                                NewsCollectDbUtils.deleteAllDataNewCollect(PersonCollectActivity.this);
                            }else {
                                VideoCollectDbUtils.deleteAllDataVideoCollect(PersonCollectActivity.this);
                            }
                            ToastUtils.TipToast(PersonCollectActivity.this,"清除成功");
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    };

    private View.OnClickListener mNewsCollectListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mNewsCollect.setSelected(true);
            mVideoCollect.setSelected(false);
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            ft.replace(R.id.collect_content,new NewsCollectedFragment());
            ft.commit();
            isNewsFragment=true;
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
            isNewsFragment=false;
        }
    };

}
