package com.example.yangyu.palmread.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangyu.palmread.Activity.VideoPlayActivity;
import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.Models.GetVideoResult;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.JsoupParse;
import com.example.yangyu.palmread.Util.VideoCollectDbUtils;
import com.example.yangyu.palmread.View.MyItemDecoration;
import com.example.yangyu.palmread.View.VideoItemBottom;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yangyu on 17/3/7.
 */

public class VideoCollectedFragment extends BaseFragment {
    public static final int MSG_NET_NULL = 0;
    public static final int MSG_GET_URL = 2;
    private View mLayout;
    private RecyclerView mRecyclerView;
    private List<GetVideoResult> mVideoData;
    private VideoDetailAdapter mAdapter;
    private LinearLayoutManager manager;
    private boolean isSucceed = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_NET_NULL:
                    mAdapter.notifyDataSetChanged();
                    break;
                case MSG_GET_URL:
                    Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                    intent.putExtra(ProjectContent.EXTRA_VIDEO_PLAY_URL, (String) msg.obj);
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
        mLayout = LayoutInflater.from(getContext()).inflate(R.layout.fragment_collect, container, false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mVideoData = VideoCollectDbUtils.cursorToPageData(VideoCollectDbUtils
                .quaryDataVideoCollect(getContext()), mHandler);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new MyItemDecoration(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        isSucceed = mVideoData.size() != 0;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) mLayout.findViewById(R.id.content);
    }

    @Override
    protected void initData() {
        mAdapter = new VideoDetailAdapter();
        manager = new LinearLayoutManager(getContext());
    }

    class VideoHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mCoverPic;
        TextView mDescription;
        SimpleDraweeView mPlayPause;
        TextView mPlayCount;
        VideoItemBottom mVib;

        TextView mDataNull;

        VideoHolder(View itemView, int type) {
            super(itemView);
            switch (type) {
                case 112:
                    mDataNull = (TextView) itemView.findViewById(R.id.data_null);
                    break;
                case 113:
                    mCoverPic = (SimpleDraweeView) itemView.findViewById(R.id.video_cover_pic);
                    mDescription = (TextView) itemView.findViewById(R.id.video_description);
                    mPlayPause = (SimpleDraweeView) itemView.findViewById(R.id.play_pause);
                    mPlayCount = (TextView) itemView.findViewById(R.id.play_count);
                    mVib = (VideoItemBottom) itemView.findViewById(R.id.vib);
                    break;
            }

        }
    }

    private class VideoDetailAdapter extends RecyclerView.Adapter<VideoHolder> {
        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 112:
                    View view_null = LayoutInflater.from(getActivity())
                            .inflate(R.layout.item_null, parent, false);

                    return new VideoHolder(view_null, 112);

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
            switch (type) {
                case 112:
                    break;
                case 113:
                    if (mVideoData != null) {
                        GetVideoResult result = mVideoData.get(position);
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
            return mVideoData.size() == 0 ? 1 : mVideoData.size();
        }

        @Override
        public int getItemViewType(int position) {
            int count = getItemCount();
            if (position == count - 1) {
                if (!isSucceed) {
                    return 112;
                }
            }
            return 113;
        }

        private View.OnClickListener mPlayPauseListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetVideoResult data = (GetVideoResult) v.getTag();
                JsoupParse.htmlParseString(data, mHandler);
            }
        };
    }
}
