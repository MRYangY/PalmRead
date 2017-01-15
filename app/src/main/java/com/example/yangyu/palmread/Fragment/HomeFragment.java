package com.example.yangyu.palmread.Fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Logic.HomeCollectionLogic;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.UrlParseUtils;

/**
 * Created by yangyu on 2017/1/9.
 */

public class HomeFragment extends BaseFragment {
    public static final String TAG = HomeFragment.class.getCanonicalName();
    private View mLayout;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private MyItemDecoration mDecoration;
    private String mUrl;
    private String mResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mLayout = inflater.inflate(R.layout.fragmnet_home,container,false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        String mTab=arguments.getString("TabIndex");
        Toast.makeText(getActivity(),mTab,Toast.LENGTH_SHORT).show();
        mUrl = UrlParseUtils.onStringParseUrl(mTab);
        getData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(mDecoration);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mResult = HomeCollectionLogic.getNetData(mUrl);
            }
        }).start();
    }
    @Override
    protected void initView() {
        mRefresh = (SwipeRefreshLayout)mLayout.findViewById(R.id.swipe);
        mRecyclerView = (RecyclerView)mLayout.findViewById(R.id.recycler);
    }

    @Override
    protected void initData() {
        mAdapter = new MyAdapter();
        mAdapter.setmOnRecyclerViewItemClick(new OnRecyclerViewItemClick() {
            @Override
            public void onItemClick(View v) {
                Toast.makeText(getActivity(),"Item Click",Toast.LENGTH_LONG).show();
            }
        });
        mDecoration = new MyItemDecoration(getActivity());
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder>{
        private OnRecyclerViewItemClick mOnRecyclerViewItemClick;

        void setmOnRecyclerViewItemClick(OnRecyclerViewItemClick mOnRecyclerViewItemClick) {
            this.mOnRecyclerViewItemClick = mOnRecyclerViewItemClick;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_home,parent,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnRecyclerViewItemClick!=null){
                        mOnRecyclerViewItemClick.onItemClick(v);
                    }
                }
            });
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }



    }
    public interface OnRecyclerViewItemClick{
        void onItemClick(View v);
    }

    private class MyHolder extends RecyclerView.ViewHolder {

        private  ImageView mNewPhoto;
        private  TextView mNewTitle;
        private  TextView mNewTime;
        private  TextView mNewEditor;

        public MyHolder(View itemView) {
            super(itemView);
            mNewPhoto = (ImageView) itemView.findViewById(R.id.new_photo);
            mNewTitle = (TextView)itemView.findViewById(R.id.new_title);
            mNewTime = (TextView)itemView.findViewById(R.id.new_time);
            mNewEditor = (TextView)itemView.findViewById(R.id.new_editor);
        }

    }

    private class MyItemDecoration extends RecyclerView.ItemDecoration{
        private  int[] ATTRS=new int[]{
                android.R.attr.listDivider
        };
        private Drawable mDrawable;

        MyItemDecoration(Context context) {
            final TypedArray array=context.obtainStyledAttributes(ATTRS);
            mDrawable=array.getDrawable(0);
            array.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDrawable.getIntrinsicHeight();
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        }
    }
}
