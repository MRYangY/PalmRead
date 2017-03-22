package com.example.yangyu.palmread.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.yangyu.palmread.Base.BaseFragment;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.SharePreferenceUtils;
import com.example.yangyu.palmread.Util.ToastUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yangyu on 17/3/18.
 */

public class AccountLoginFragment extends BaseFragment {

    private View mLayout;
    private EditText mAccount;
    private EditText mPassword;
    private CircleImageView mLogin;

    private String account;
    private String pwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login,null);
        return mLayout;
    }

    private View.OnClickListener mLoginListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            account=mAccount.getText().toString().trim();
            pwd=mPassword.getText().toString().trim();
            if (TextUtils.isEmpty(account)||TextUtils.isEmpty(pwd)){
                ToastUtils.TipToast(getContext(),"不能输入空信息");
            }else if (account.equals(SharePreferenceUtils.getStringData(getContext()
                    , ProjectContent.USER_ACCOUNT))&&pwd.equals(SharePreferenceUtils.getStringData(getContext()
                    ,ProjectContent.USER_PASSWORD))){
                ToastUtils.TipToast(getContext(),"登录成功");
                SharePreferenceUtils.setBoolean(getContext(),true,ProjectContent.LOGIN_OK);
                Intent intent=new Intent();
                intent.setAction(ProjectContent.INTENT_LOGIN_OK);
                getContext().sendBroadcast(intent);
            }else {
                ToastUtils.TipToast(getContext(),"账号密码错误");
            }
        }
    };
    @Override
    protected void initView() {
        mAccount = (EditText) mLayout.findViewById(R.id.login_person_account);
        mPassword = (EditText) mLayout.findViewById(R.id.login_person_password);
        mLogin = (CircleImageView) mLayout.findViewById(R.id.login);
    }

    @Override
    protected void initData() {
        mLogin.setOnClickListener(mLoginListener);
    }
}
