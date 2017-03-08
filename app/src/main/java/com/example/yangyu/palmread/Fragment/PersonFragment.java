package com.example.yangyu.palmread.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangyu.palmread.Activity.PersonCollectActivity;
import com.example.yangyu.palmread.Activity.PersonHistoryActivity;
import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.ToastUtils;

/**
 * Created by yangyu on 2017/1/9.
 */

public class PersonFragment extends BaseFragment {
    public static final String TAG =PersonFragment.class.getCanonicalName();
    private View mLayout;
    private TextView mCollect;
    private TextView mHistory;
    private TextView mSize;
    private TextView mDeclare;
    private TextView mUpdate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mLayout=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_person,container,false);
        return mLayout;
    }

    @Override
    protected void initView() {
        mCollect=(TextView) mLayout.findViewById(R.id.person_collect);
        mHistory = (TextView) mLayout.findViewById(R.id.read_history);
        mSize=(TextView) mLayout.findViewById(R.id.person_textsize);
        mDeclare = (TextView) mLayout.findViewById(R.id.software_declare);
        mUpdate = (TextView) mLayout.findViewById(R.id.software_updata);
    }

    @Override
    protected void initData() {
        mCollect.setOnClickListener(mCollectListener);
        mHistory.setOnClickListener(mHistoryListener);
        mSize.setOnClickListener(mSizeListener);
        mDeclare.setOnClickListener(mDeclareListener);
        mUpdate.setOnClickListener(mUpdateListener);
    }
    private View.OnClickListener mCollectListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getContext().startActivity(new Intent(getActivity(), PersonCollectActivity.class));
        }
    };
    private View.OnClickListener mHistoryListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getContext().startActivity(new Intent(getActivity(), PersonHistoryActivity.class));
        }
    };
    private View.OnClickListener mSizeListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtils.TipToast(getContext(),"字体大小");
        }
    };
    private View.OnClickListener mDeclareListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtils.TipToast(getContext(),"软件声明");
        }
    };
    private View.OnClickListener mUpdateListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtils.TipToast(getContext(),"当前已是最新版本！");
        }
    };
}
