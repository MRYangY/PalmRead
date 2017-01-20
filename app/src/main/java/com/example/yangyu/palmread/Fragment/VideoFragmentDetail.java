package com.example.yangyu.palmread.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangyu.palmread.Activity.VideoPlayActivity;
import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.Logic.HomeJsonToResult;
import com.example.yangyu.palmread.Logic.VideoLogic;
import com.example.yangyu.palmread.Models.GetVideoResult;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.JsoupParse;
import com.example.yangyu.palmread.Util.ToastUtils;
import com.example.yangyu.palmread.Util.UrlParseUtils;
import com.example.yangyu.palmread.View.MyItemDecoration;
import com.example.yangyu.palmread.View.VideoItemBottom;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangyu on 2017/1/18.
 */

public class VideoFragmentDetail extends BaseFragment implements Callback{

    private View mLayout;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mContentView;
    private VideoDetailAdapter mAdapter;
    private MyItemDecoration mItemDecoration;

    private GetVideoResult mGetVideoResult;
    private List<GetVideoResult> mVideoData;
    private int mVideoIndex;
    private int default_page=1;
//    private boolean isShow=true;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 00:
                    ToastUtils.TipToast(getActivity(),"短视频获取网络错误！！");
                break;
                case 01:
                    mAdapter.notifyDataSetChanged();
                    break;
                case 02:
                    Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
                    intent.putExtra(ProjectContent.EXTRA_VIDEO_PLAY_URL,(String)msg.obj);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_video_detail,container,false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        mVideoIndex = arguments.getInt(ProjectContent.EXTRA_VIDEO_INDEX);
        String videoDetailUrl = UrlParseUtils.getVideoDetailUrlPage(mVideoIndex,default_page);
        VideoLogic.getVideoNoPage(videoDetailUrl,this);
        mContentView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mContentView.addItemDecoration(mItemDecoration);
        mContentView.setAdapter(mAdapter);
    }

    @Override
    protected void initView() {
        mRefreshLayout = (SwipeRefreshLayout)mLayout.findViewById(R.id.video_refresh);
        mContentView = (RecyclerView)mLayout.findViewById(R.id.video_content);
    }

    @Override
    protected void initData() {
        mAdapter = new VideoDetailAdapter();
        mItemDecoration = new MyItemDecoration(getActivity());
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        mHandler.sendEmptyMessage(00);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        mVideoData=HomeJsonToResult.VideoResultParse(GetVideoResult.class
                ,response.body().string());
        mHandler.sendEmptyMessage(01);
    }

    private class VideoDetailAdapter extends RecyclerView.Adapter<VideoHolder>{
        private VideoHolder mHolder;

        public VideoHolder getVideoHolder(){
            return mHolder;
        }
        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.item_video_detail,parent,false);
            mHolder=new VideoHolder(view);
            return new VideoHolder(view);
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int position) {
            if(mVideoData!=null){
                GetVideoResult result = mVideoData.get(position);
                holder.mCoverPic.setImageURI(result.getCover_pic());
                holder.mDescription.setText(result.getCaption());
                holder.mPlayCount.setText(result.getPlays_count()+"");
                holder.mVib.mUserPhoto.setImageURI(result.getAvatar());
                holder.mVib.mUserName.setText(result.getScreen_name());
                holder.mVib.mLikeCount.setText(result.getLikes_count()+"");
                holder.mVib.mCommentsCount.setText(result.getComments_count()+"");
                holder.mVib.mLikeCount.setTag(result);
                holder.mVib.mCommentsCount.setTag(result);
                holder.mPlayPause.setTag(result);
                holder.mPlayPause.setOnClickListener(mPlayPauseListener);
            }

        }

        @Override
        public int getItemCount() {
            return mVideoData==null?1:mVideoData.size();
        }

    }

    private View.OnClickListener mPlayPauseListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GetVideoResult data=(GetVideoResult)v.getTag();
            JsoupParse.htmlParseString(data,mHandler);
//            Log.e("test", "onClick: "+url);
        }
    };

    public class VideoHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView mCoverPic;
        TextView mDescription;
        SimpleDraweeView mPlayPause;
        TextView mPlayCount;
        VideoItemBottom mVib;

        public VideoHolder(View itemView) {
            super(itemView);
            mCoverPic = (SimpleDraweeView)itemView.findViewById(R.id.video_cover_pic);
            mDescription = (TextView)itemView.findViewById(R.id.video_description);
            mPlayPause = (SimpleDraweeView)itemView.findViewById(R.id.play_pause);
            mPlayCount = (TextView)itemView.findViewById(R.id.play_count);
            mVib = (VideoItemBottom)itemView.findViewById(R.id.vib);
        }
    }
}
