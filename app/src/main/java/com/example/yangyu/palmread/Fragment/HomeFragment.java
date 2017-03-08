package com.example.yangyu.palmread.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangyu.palmread.Activity.HomePageDetailActivity;
import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.Logic.HomeCollectionLogic;
import com.example.yangyu.palmread.Logic.HomeJsonToResult;
import com.example.yangyu.palmread.Logic.WebCache;
import com.example.yangyu.palmread.Models.GetHomePageresult;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.NewsHistoryDbUtils;
import com.example.yangyu.palmread.Util.ToastUtils;
import com.example.yangyu.palmread.Util.UrlParseUtils;
import com.example.yangyu.palmread.View.MyItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangyu on 2017/1/9.
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = HomeFragment.class.getCanonicalName();
    private View mLayout;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private MyItemDecoration mDecoration;
    private String mUrl;
//    private String mResult;
    private GetHomePageresult mHomePageresult;
    private GetHomePageresult.PageData[] mPageData;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch(what) {
                case 0:
                    mAdapter.notifyDataSetChanged();
                    ToastUtils.TipToast(getActivity(), "网络错误，请检查您的网络");
                    break;
                case 1:
                    mRefresh.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    mPageData=((GetHomePageresult) msg.obj).mResult.mData;
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
    private String mTab;
    private HomeCallBack mCallBack;
    private boolean hasNet=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mLayout = inflater.inflate(R.layout.fragmnet_home, container, false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        mTab = arguments.getString(ProjectContent.EXTRA_TAB_INDEX);
        mUrl = UrlParseUtils.onStringParseUrl(mTab);
        HomeCollectionLogic.getNetData(mUrl, mCallBack, mTab,mHandler);
        mRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light);
        mRefresh.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(mDecoration);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initView() {
        mRefresh = (SwipeRefreshLayout)mLayout.findViewById(R.id.swipe);
        mRecyclerView = (RecyclerView)mLayout.findViewById(R.id.recycler);
    }

    @Override
    protected void initData() {
        mCallBack = new HomeCallBack();
        mAdapter = new MyAdapter() {
            @Override
            public void reGetData() {
                HomeCollectionLogic.getNetData(mUrl, mCallBack, mTab,mHandler);
            }
        };
        mAdapter.setmOnRecyclerViewItemClick(new OnRecyclerViewItemClick() {
            @Override
            public void onItemClick(View v,GetHomePageresult.PageData data) {
//                Toast.makeText(getActivity(), "Item Click", Toast.LENGTH_LONG).show();
                NewsHistoryDbUtils.insertDataNewHistory(getContext(),data);
                Intent intent=new Intent(getActivity(), HomePageDetailActivity.class);
                intent.putExtra(ProjectContent.EXTRA_HOME_FRAGMENT_RESULT,data);
                startActivity(intent);
            }
        });
        mDecoration = new MyItemDecoration(getActivity());
    }

    private class HomeCallBack implements Callback{
        @Override
        public void onFailure(Call call, IOException e) {
            hasNet=false;
            mHandler.sendEmptyMessage(0);
            mRefresh.setRefreshing(false);
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            hasNet=true;
            mHomePageresult = HomeJsonToResult.HomePageParse(GetHomePageresult.class, response.body().string());
            WebCache.saveMemoryCache(mTab,mHomePageresult);
            mPageData = mHomePageresult != null ? mHomePageresult.mResult.mData : new GetHomePageresult.PageData[0];
            mHandler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(true);
        WebCache.resetMemory(mTab);
        HomeCollectionLogic.getNetData(mUrl, mCallBack,mTab,mHandler);
    }

    private abstract class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private OnRecyclerViewItemClick mOnRecyclerViewItemClick;

        void setmOnRecyclerViewItemClick(OnRecyclerViewItemClick mOnRecyclerViewItemClick) {
            this.mOnRecyclerViewItemClick = mOnRecyclerViewItemClick;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch(viewType){
                case 111:
                    View viewError=LayoutInflater.from(getActivity())
                            .inflate(R.layout.item_error,parent,false);
                    return new MyHolder(viewError,111);
                case 112:
                    View view = LayoutInflater.from(getActivity())
                            .inflate(R.layout.item_home, parent, false);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mOnRecyclerViewItemClick != null) {
                                mOnRecyclerViewItemClick.onItemClick(v,(GetHomePageresult.PageData)v.getTag());
                            }
                        }
                    });
                    return new MyHolder(view,112);
            }
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_home, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnRecyclerViewItemClick != null) {
                        mOnRecyclerViewItemClick.onItemClick(v,(GetHomePageresult.PageData)v.getTag());
                    }
                }
            });
            return new MyHolder(view,112);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            int type=getItemViewType(position);
            switch(type){
                case 111:
                    if(holder.mNetError!=null)
                    holder.mNetError.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reGetData();
                        }
                    });
                    break;
                case 112:
                    if(mPageData!=null){
                        GetHomePageresult.PageData data=mPageData[position];
                        int num=showPic(data);
                        switch(num){
                            case 0:
                                holder.mNewPhoto1.setVisibility(View.GONE);
                                holder.mNewPhoto2.setVisibility(View.GONE);
                                holder.mNewPhoto3.setVisibility(View.GONE);
                                break;
                            case 1:
                                holder.mNewPhoto1.setVisibility(View.VISIBLE);
                                holder.mNewPhoto2.setVisibility(View.GONE);
                                holder.mNewPhoto3.setVisibility(View.GONE);
                                ViewGroup.LayoutParams params= holder.mNewPhoto1.getLayoutParams();
                                DisplayMetrics metric = new DisplayMetrics();
                                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
                                int width = metric.widthPixels;
                                params.height=width*2/4;
                                holder.mNewPhoto1.setLayoutParams(params);
                                holder.mNewPhoto1.setImageURI(data.mPicOne);
                                break;
                            case 2:
                                holder.mNewPhoto1.setVisibility(View.VISIBLE);
                                holder.mNewPhoto2.setVisibility(View.VISIBLE);
                                holder.mNewPhoto3.setVisibility(View.GONE);
                                ViewGroup.LayoutParams params1= holder.mNewPhoto1.getLayoutParams();
                                ViewGroup.LayoutParams paramsPhotoCase2= holder.mNewPhoto2.getLayoutParams();
                                params1.height=paramsPhotoCase2.height;
                                holder.mNewPhoto1.setLayoutParams(params1);
                                holder.mNewPhoto1.setImageURI(data.mPicOne);
                                holder.mNewPhoto2.setImageURI(data.mPicTwo);
                                break;
                            case 3:
                                holder.mNewPhoto1.setVisibility(View.VISIBLE);
                                holder.mNewPhoto2.setVisibility(View.VISIBLE);
                                holder.mNewPhoto3.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams params2= holder.mNewPhoto1.getLayoutParams();
                                ViewGroup.LayoutParams paramsPhotoCase3= holder.mNewPhoto2.getLayoutParams();
                                params2.height=paramsPhotoCase3.height;
                                holder.mNewPhoto1.setLayoutParams(params2);
                                holder.mNewPhoto1.setImageURI(data.mPicOne);
                                holder.mNewPhoto2.setImageURI(data.mPicTwo);
                                holder.mNewPhoto3.setImageURI(data.mPicThree);
                                break;
                        }
                        holder.mNewTitle.setText(data.mTitle);
                        holder.mNewTime.setText(data.mData);
                        holder.mNewEditor.setText(data.mAutorName);
                        holder.itemView.setTag(data);
                    }
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mPageData == null ? 1 : mPageData.length;
        }

        @Override
        public int getItemViewType(int position) {
            int count=getItemCount();
            if(count-1==position){
                if(!hasNet)
                    return 111;
            }
            return 112;
        }

        public abstract void reGetData();
    }

    private int showPic(GetHomePageresult.PageData data){
        int count=0;
        if(!TextUtils.isEmpty(data.mPicOne)){
            count=1;
        }else {
            return count;
        }
        if(!TextUtils.isEmpty(data.mPicTwo)){
            count=2;
        }else {
            return count;
        }
        if(!TextUtils.isEmpty(data.mPicThree)){
            count=3;
        }else {
            return count;
        }
        return count;
    }
    public interface OnRecyclerViewItemClick {
        void onItemClick(View v,GetHomePageresult.PageData modelData);
    }

    private class MyHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mNewPhoto1;
        private SimpleDraweeView mNewPhoto2;
        private SimpleDraweeView mNewPhoto3;
        private TextView mNewTitle;
        private TextView mNewTime;
        private TextView mNewEditor;

        TextView mNetError;

        MyHolder(View itemView,int type) {
            super(itemView);
            switch(type){
                case 111:
                    mNetError=(TextView)itemView.findViewById(R.id.net_error);
                    break;
                case 112:
                    mNewPhoto1 = (SimpleDraweeView)itemView.findViewById(R.id.new_photo1);
                    mNewPhoto2 = (SimpleDraweeView)itemView.findViewById(R.id.new_photo2);
                    mNewPhoto3 = (SimpleDraweeView)itemView.findViewById(R.id.new_photo3);
                    mNewTitle = (TextView)itemView.findViewById(R.id.new_title);
                    mNewTime = (TextView)itemView.findViewById(R.id.new_time);
                    mNewEditor = (TextView)itemView.findViewById(R.id.new_editor);
                    break;
            }
        }

    }


}
