package com.example.yangyu.palmread.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.yangyu.palmread.Models.GetHomePageresult;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.NewsCollectDbUtils;
import com.example.yangyu.palmread.View.MyItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yangyu on 17/3/7.
 */

public class NewsCollectedFragment extends BaseFragment {
    private View mLayout;
    private RecyclerView mNewsCollectContent;

    private List<GetHomePageresult.PageData> mPageData;
    private MyAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout= LayoutInflater.from(getContext()).inflate(R.layout.fragment_collect,container,false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPageData= NewsCollectDbUtils.cursorToPageData(NewsCollectDbUtils.quaryDataNewCollect(getContext()));
        mNewsCollectContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mNewsCollectContent.addItemDecoration(new MyItemDecoration(getContext()));
        mNewsCollectContent.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        mNewsCollectContent = (RecyclerView) mLayout.findViewById(R.id.content);
    }

    @Override
    protected void initData() {
        mAdapter = new MyAdapter();
        mAdapter.setmOnRecyclerViewItemClick(new OnRecyclerViewItemClick() {
            @Override
            public void onItemClick(View v, GetHomePageresult.PageData modelData) {
                Intent intent=new Intent(getActivity(), HomePageDetailActivity.class);
                intent.putExtra(ProjectContent.EXTRA_HOME_FRAGMENT_RESULT,modelData);
                startActivity(intent);
            }
        });
    }

    private class MyHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mNewPhoto1;
        private SimpleDraweeView mNewPhoto2;
        private SimpleDraweeView mNewPhoto3;
        private TextView mNewTitle;
        private TextView mNewTime;
        private TextView mNewEditor;

        TextView mDataNull;

        MyHolder(View itemView,int type) {
            super(itemView);
            switch(type){
                case 111:
                    mDataNull=(TextView)itemView.findViewById(R.id.data_null);
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

    public interface OnRecyclerViewItemClick {
        void onItemClick(View v,GetHomePageresult.PageData modelData);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private OnRecyclerViewItemClick mOnRecyclerViewItemClick;

        void setmOnRecyclerViewItemClick(OnRecyclerViewItemClick mOnRecyclerViewItemClick) {
            this.mOnRecyclerViewItemClick = mOnRecyclerViewItemClick;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch(viewType){
                case 111:
                    View viewError=LayoutInflater.from(getActivity())
                            .inflate(R.layout.item_null,parent,false);
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
//                    if(holder.mDataNull!=null)
//                        holder.mDataNull.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
////                                reGetData();
//                            }
//                        });
                    break;
                case 112:
                    if(mPageData!=null){
                        GetHomePageresult.PageData data=mPageData.get(position);
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
            return mPageData.size() == 0 ? 0 : mPageData.size();
        }

        @Override
        public int getItemViewType(int position) {
            int count=getItemCount();
            if(count==position){
//                if(!hasNet)
                    return 111;
            }
            return 112;
        }

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

}
