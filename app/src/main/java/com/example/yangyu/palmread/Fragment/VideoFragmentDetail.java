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
import com.example.yangyu.palmread.Util.VideoHistoryDbUtils;
import com.example.yangyu.palmread.View.MyItemDecoration;
import com.example.yangyu.palmread.View.VideoItemBottom;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangyu on 2017/1/18.
 */

public class VideoFragmentDetail extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final int MSG_NET_ERROR=00;
    public static final int MSG_REFRESH=01;
    public static final int MSG_GET_URL=02;
    private View mLayout;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mContentView;
    private VideoDetailAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ReadVideoCallBack mCallBack;

    private MyItemDecoration mItemDecoration;
    //    private GetVideoResult mGetVideoResult;
    private List<GetVideoResult> mVideoData;
    private List<GetVideoResult> mVideoDataTotal;
    private int mVideoIndex;

    private int default_page = 1;
    private String videoDetailUrl;
    private boolean hasNet = true;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case MSG_NET_ERROR:
                    mRefreshLayout.setRefreshing(false);
                    ToastUtils.TipToast(getActivity(), getString(R.string.video_net_error));
                    mAdapter.notifyDataSetChanged();
                    break;
                case MSG_REFRESH:
                    mRefreshLayout.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                    break;
                case MSG_GET_URL:
                    Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                    intent.putExtra(ProjectContent.EXTRA_VIDEO_PLAY_URL, (String)msg.obj);
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
        mLayout = inflater.inflate(R.layout.fragment_video_detail, container, false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        mVideoIndex = arguments.getInt(ProjectContent.EXTRA_VIDEO_INDEX);
        videoDetailUrl = UrlParseUtils.getVideoDetailUrlPage(mVideoIndex, default_page);
        VideoLogic.getVideoNoPage(videoDetailUrl, mCallBack);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light);
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(this);
        mContentView.setLayoutManager(mLinearLayoutManager);
        mContentView.addItemDecoration(mItemDecoration);
        mContentView.setAdapter(mAdapter);
        mContentView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LoadMoreData(currentPage);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mVideoDataTotal!=null)
        mVideoDataTotal.clear();
    }

    private void LoadMoreData(int currentPage) {
        String moreUrl = UrlParseUtils.getVideoDetailUrlPage(mVideoIndex, currentPage);
        VideoLogic.getVideoNoPage(moreUrl, mCallBack);
    }

    abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener {

        //声明一个LinearLayoutManager
        private LinearLayoutManager mLinearLayoutManager;

        //当前页，从0开始    private int currentPage = 0;
        //已经加载出来的Item的数量
        private int totalItemCount;

        //主要用来存储上一个totalItemCount
//        private int previousTotal = 0;

        //在屏幕上可见的item数量
//        private int visibleItemCount;

        //在屏幕可见的Item中的第一个
//        private int firstVisibleItem;
        private int lastVisibleItem;
        //是否正在上拉数据
//        private boolean loading = true;

        EndLessOnScrollListener(LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
//            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
            lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

//            Log.e("Test","firstVisibleItem: " +firstVisibleItem);
//            Log.e("Test", "lastVisibleItem:" + lastVisibleItem);
//            Log.e("Test","totalPageCount:" +totalItemCount);
//            Log.e("Test", "visibleItemCount:" + visibleItemCount);
//            Log.e("Test", "dx:" + dx);
//            Log.e("Test", "dy:" + dy);


        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(totalItemCount - 1 == lastVisibleItem && newState == RecyclerView.SCROLL_STATE_IDLE) {
                default_page++;
                onLoadMore(default_page);
            }
        }

        /**
         * 提供一个抽闲方法，在Activity中监听到这个EndLessOnScrollListener
         * 并且实现这个方法
         */
        public abstract void onLoadMore(int currentPage);
    }

    @Override
    protected void initView() {
        mRefreshLayout = (SwipeRefreshLayout)mLayout.findViewById(R.id.video_refresh);
        mContentView = (RecyclerView)mLayout.findViewById(R.id.video_content);
    }

    @Override
    protected void initData() {
        mCallBack = new ReadVideoCallBack();
//        mVideoDataTotal = new ArrayList<>();
        mAdapter = new VideoDetailAdapter() {
            @Override
            public void reGetData() {
                VideoLogic.getVideoNoPage(videoDetailUrl, mCallBack);
            }
        };
        mItemDecoration = new MyItemDecoration(getActivity());
    }

    private class ReadVideoCallBack implements Callback {

        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            hasNet = false;
            mHandler.sendEmptyMessage(MSG_NET_ERROR);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            hasNet = true;
            mVideoData = HomeJsonToResult.VideoResultParse(GetVideoResult.class
                    , response.body().string());
            if(mVideoDataTotal==null){
                mVideoDataTotal=new ArrayList<>();
            }
            if (mVideoData!=null) {
                mVideoDataTotal.addAll(mVideoData);
            }
            mHandler.sendEmptyMessage(MSG_REFRESH);
        }

    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        VideoLogic.getVideoNoPage(videoDetailUrl, mCallBack);
    }

    private abstract class VideoDetailAdapter extends RecyclerView.Adapter<VideoHolder> {
        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch(viewType) {
                case 111:
                    View view_more = LayoutInflater.from(getActivity())
                            .inflate(R.layout.item_loading, parent, false);

                    return new VideoHolder(view_more, 111);

                case 112:
                    View view_error = LayoutInflater.from(getActivity())
                            .inflate(R.layout.item_error, parent, false);

                    return new VideoHolder(view_error, 112);

                case 113:
                    View view_normal = LayoutInflater.from(getActivity())
                            .inflate(R.layout.item_video_detail, parent, false);

                    return new VideoHolder(view_normal, 113);
            }
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_video_detail, parent, false);

            return new VideoHolder(view, 113);
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int position) {
            int type = getItemViewType(position);
            switch(type) {
                case 111:
                    holder.mFootView.setText("正在加载中...");
                    break;
                case 112:
                    if(holder.mNetError!=null)
                    holder.mNetError.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reGetData();
                        }
                    });
                    break;
                case 113:
                    if(mVideoDataTotal != null) {
                        GetVideoResult result = mVideoDataTotal.get(position);
                        holder.mCoverPic.setImageURI(result.getCover_pic());
                        holder.mDescription.setText(result.getCaption());
                        holder.mPlayCount.setText(result.getPlays_count() + "");
                        holder.mVib.mUserPhoto.setImageURI(result.getAvatar());
                        holder.mVib.mUserName.setText(result.getScreen_name());
                        holder.mVib.mLikeCount.setText(result.getLikes_count() + "");
                        holder.mVib.mCommentsCount.setText(result.getComments_count() + "");
                        holder.mVib.mLikeCount.setTag(result);
                        holder.mVib.mCommentsCount.setTag(result);
                        holder.mVib.mTool.setTag(result);
                        holder.mPlayPause.setTag(result);
                        holder.mPlayPause.setOnClickListener(mPlayPauseListener);
                    }
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return mVideoDataTotal == null ? 1 : mVideoDataTotal.size();
        }

        @Override
        public int getItemViewType(int position) {
            int count = getItemCount();
            if(position == count - 1) {
                if(hasNet) {
                    return 111;
                } else {
                    return 112;
                }
            }
            return 113;
        }

        public abstract void reGetData();
    }

    private View.OnClickListener mPlayPauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GetVideoResult data = (GetVideoResult)v.getTag();
            VideoHistoryDbUtils.insertDataVideoHistory(getContext(),data);
            JsoupParse.htmlParseString(data, mHandler);
        }
    };

    class VideoHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mCoverPic;
        TextView mDescription;
        SimpleDraweeView mPlayPause;
        TextView mPlayCount;
        VideoItemBottom mVib;

        TextView mFootView;
        TextView mNetError;

        VideoHolder(View itemView, int type) {
            super(itemView);
            switch(type) {
                case 111:
                    mFootView = (TextView)itemView.findViewById(R.id.foot_view);
                    break;
                case 112:
                    mNetError = (TextView)itemView.findViewById(R.id.net_error);
                    break;
                case 113:
                    mCoverPic = (SimpleDraweeView)itemView.findViewById(R.id.video_cover_pic);
                    mDescription = (TextView)itemView.findViewById(R.id.video_description);
                    mPlayPause = (SimpleDraweeView)itemView.findViewById(R.id.play_pause);
                    mPlayCount = (TextView)itemView.findViewById(R.id.play_count);
                    mVib = (VideoItemBottom)itemView.findViewById(R.id.vib);
                    break;
            }

        }
    }
}
