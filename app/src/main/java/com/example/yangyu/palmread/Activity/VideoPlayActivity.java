package com.example.yangyu.palmread.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.R;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by yangyu on 2017/1/20.
 */

public class VideoPlayActivity extends AppCompatActivity {

    private VideoView mVideo;
    private String playUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = VideoPlayActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        //必须写这个，初始化加载库文件
        Vitamio.initialize(this);
        //设置视频解码监听
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        setContentView(R.layout.activity_play_video);
        playUrl = getIntent().getStringExtra(ProjectContent.EXTRA_VIDEO_PLAY_URL);
        mVideo = (VideoView)findViewById(R.id.video_view);
        mVideo.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH,0);
        mVideo.setVideoURI(Uri.parse("http://mvvideo2.meitudata.com/58bfaedcc5958941.mp4"));
        MediaController mediaController=new MediaController(this);
        mVideo.setMediaController(mediaController);
        mVideo.start();
    }

}
