package com.example.yangyu.palmread.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangyu.palmread.Activity.PersonCollectActivity;
import com.example.yangyu.palmread.Activity.PersonHistoryActivity;
import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.SharePreferenceUtils;
import com.example.yangyu.palmread.Util.ToastUtils;

/**
 * Created by yangyu on 2017/1/9.
 */

public class PersonFragment extends BaseFragment {
    public static final String TAG =PersonFragment.class.getCanonicalName();
    private View mLayout;
    private TextView mCollect;
    private TextView mHistory;
    private TextView mLogout;
    private TextView mDeclare;
    private TextView mUpdate;
//    private FrameLayout content;

    private String account;
    private String password;
    private boolean isLogin;

    private BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ProjectContent.REGISTER_OK:
                    setFragment(new AccountLoginFragment());
                    break;
                case ProjectContent.INTENT_LOGIN_OK:
                    setFragment(new UserLoginedFragment());
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mLayout=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_person,container,false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IntentFilter filter=new IntentFilter();
        filter.addAction(ProjectContent.INTENT_LOGIN_OK);
        filter.addAction(ProjectContent.REGISTER_OK);
        getContext().registerReceiver(mReceiver,filter);
        account=SharePreferenceUtils.getStringData(getActivity(),ProjectContent.USER_ACCOUNT);
        password=SharePreferenceUtils.getStringData(getActivity(),ProjectContent.USER_PASSWORD);
        if (!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(password)){

        }
        isLogin= SharePreferenceUtils.getBoolean(getActivity(),ProjectContent.LOGIN_OK);
        if (isLogin){
            setFragment(new UserLoginedFragment());
        }else if (!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(password)){
            setFragment(new AccountLoginFragment());
        }else {
            setFragment(new AccountRegisterFragment());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver!=null){
            getActivity().unregisterReceiver(mReceiver);
        }
    }

    private void setFragment(Fragment fragment){
        FragmentManager fm=getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.person_login,fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void initView() {
        mCollect=(TextView) mLayout.findViewById(R.id.person_collect);
        mHistory = (TextView) mLayout.findViewById(R.id.read_history);
        mLogout=(TextView) mLayout.findViewById(R.id.person_logout);
        mDeclare = (TextView) mLayout.findViewById(R.id.software_declare);
        mUpdate = (TextView) mLayout.findViewById(R.id.software_updata);
//        content = (FrameLayout) mLayout.findViewById(R.id.person_login);
    }

    @Override
    protected void initData() {
        mCollect.setOnClickListener(mCollectListener);
        mHistory.setOnClickListener(mHistoryListener);
        mLogout.setOnClickListener(mLogoutListener);
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
    private View.OnClickListener mLogoutListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharePreferenceUtils.setBoolean(getActivity(),false,ProjectContent.LOGIN_OK);
//            Intent intent=new Intent();
//            intent.setAction(ProjectContent.INTENT_LOGOUT_OK);
//            getContext().sendBroadcast(intent);
            setFragment(new AccountLoginFragment());
            ToastUtils.TipToast(getContext(),"已退出登录");
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
